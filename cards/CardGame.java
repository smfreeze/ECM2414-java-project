package cards;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CardGame {
    public static void main(String[] args) {
        // Input number of players
        System.out.println("Please enter the number of players:");
        Scanner terminalReader = new Scanner(System.in);
        int playerCount = checkPlayers(terminalReader.next());
        // Loops until the number of players is validated by checkPlayers function
        while (playerCount == 0) {
            System.out.println("");
            System.out.println("Invalid entry");
            System.out.println("Please enter the number of players:");
            playerCount = checkPlayers(terminalReader.next());
        }

        // Input the pack file location
        System.out.println("Please enter location of pack to load:");
        ArrayList<Card> pack = checkPack(terminalReader.next(), playerCount);
        // Loops until the pack file has been validated by checkPack function
        while (pack.isEmpty()) {
            System.out.println("");
            System.out.println("Invalid pack file");
            System.out.println("Please enter location of pack to load:");
            pack = checkPack(terminalReader.next(), playerCount);
        }
        terminalReader.close();

        // Creates arrays for the player and for the card deck threads
        Thread[] playerThreadsArray = new Thread[playerCount];
        CardDeck[] deckArray = new CardDeck[playerCount];
        Player[] playersArray = new Player[playerCount];

        ArrayList<ArrayList<Card>> tempPlayersArray = new ArrayList<ArrayList<Card>>();
        ArrayList<ArrayList<Integer>> playersCardNumbersArray = new ArrayList<ArrayList<Integer>>();

        // Initialises the player hands
        for (int x = 0; x < playerCount; x++) {
            tempPlayersArray.add(new ArrayList<Card>());
            playersCardNumbersArray.add(new ArrayList<Integer>());
        }

        // Initialises the card decks
        for (int x = 0; x < playerCount; x++) {
            deckArray[x] = new CardDeck(x + 1);
        }

        // Deals players their cards in round robin fashion
        for (int x = 0; x < playerCount * 4; x++) {
            tempPlayersArray.get(x % playerCount).add(pack.get(x));
            playersCardNumbersArray.get(x % playerCount).add(pack.get(x).getNumber());
        }

        // Deals the cards in a round robin fashion to the decks
        for (int x = playerCount * 4; x < playerCount * 8; x++) {
            deckArray[x % playerCount].addCard(pack.get(x));
        }

        // Checks if any inital hands fulfill the win condition
        int startWin = -1;
        for (int pos = 0; pos < playersCardNumbersArray.size(); pos++) {
            if (new HashSet<Integer>(playersCardNumbersArray.get(pos)).size() == 1) {
                // Stores the winning player number
                startWin = pos+1;
                break;
            }
        }

        if (startWin != -1) { // There is a player who has started with a winning hand
            for (int playerNumber = 1; playerNumber < tempPlayersArray.size()+1; playerNumber++) {
                // Creates output text files for player and deck
                String playerFileName = "player" + playerNumber + "_output.txt";
                String deckFileName = "deck" + playerNumber + "_output.txt";
                StringBuilder builder = new StringBuilder();
                for (Integer cardNum : playersCardNumbersArray.get(playerNumber-1)) {
                    builder.append(cardNum+" ");
                }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(playerFileName))) {
                    writer.write("player " + playerNumber + " initial hand " + builder + "\n");
                    if (playerNumber == startWin) { // Writing into winning player output file 
                        writer.write("player " + playerNumber + " wins\n");
                        writer.write("player " + playerNumber + " exits\n");
                        writer.write("player " + playerNumber + " final hand:" + builder + "\n");
                        System.out.println("player " + playerNumber + " wins");
                    } else { // Writing into losing player output file 
                        writer.write("player " + startWin + " has informed player " + playerNumber + " that player " + startWin + " has won\n");
                        writer.write("player " + playerNumber + " exits\n");
                        writer.write("player " + playerNumber + " final hand: " + builder + "\n");
                    }
                    try (BufferedWriter writer2 = new BufferedWriter(new FileWriter(deckFileName))) { 
                        // Writing into deck output file 
                        writer2.write("deck" + playerNumber + " contents: " + deckArray[playerNumber-1].deckToString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else { // No player starting with a winning hand
            // Creates player threads with their respective hand, left deck and right deck
            for (int x = 0; x < playerCount; x++) {
                if (x == playerCount - 1) {
                    playersArray[x] = new Player(x + 1, tempPlayersArray.get(x), deckArray[x], deckArray[0]);
                    playerThreadsArray[x] = new Thread(playersArray[x]);
                } else {
                    playersArray[x] = new Player(x + 1, tempPlayersArray.get(x), deckArray[x], deckArray[x + 1]);
                    playerThreadsArray[x] = new Thread(playersArray[x]);
                }
            }

            // Starts all the threads
            for (Thread playerThread : playerThreadsArray) {
                playerThread.start();
            }
        }
    }
    
    // Checks if the input for number of players is valid
    private static int checkPlayers(String players) {
        int playerCount = 0;
        // Check if input players can be converted to an integer
        try {
            playerCount = Integer.parseInt(players);
            if (Integer.parseInt(players) > 1) {
                return playerCount;
                // Returns if this is the case (verifying the player input)
            }
        } catch (Exception e) {
        }
        // Else, return 0 for invalid user input
        return 0;
    }

    // Checks if the input for the pack file location is valid
    private static ArrayList<Card> checkPack(String file, int players) {
        int count = 0;
        ArrayList<Card> cards = new ArrayList<Card>();
        Map<Integer, Integer> countCards = new HashMap<Integer, Integer>();
        try {
            // Tries to open file from user pack location input
            File f = new File(file);
            Scanner read = new Scanner(f);
            // Loops through each line in file, confirming it
            // is an integer and not negative
            while (read.hasNextLine()) {
                String data = read.nextLine();
                int num = Integer.parseInt(data);
                if (num < 0) {
                    read.close();
                    return new ArrayList<Card>();
                }
                // Counts number of each card
                if (countCards.get(num) == null) {
                    countCards.put(num, 1);
                } else {
                    countCards.put(num, countCards.get(num)+1);
                }

                // Instantiates card and adds to array of cards
                cards.add(new Card(num));
                // Counts number of lines
                count++;
            }
            // Checks if there is a win condition
            boolean winCond = false;
            for (int x = 1; x <= players; x++) {
                if (countCards.get(x) >= 4) {
                    winCond = true;
                    break;
                }
            }
            if (!winCond) {
                read.close();
                return new ArrayList<Card>();
            }

            // Checks if the number of lines is invalid, returns empty arraylist
            if (count != 8 * players) {
                read.close();
                return new ArrayList<Card>();
            }
            read.close();
            // Returns arraylist of card objects if valid file
            return cards;
        } catch (Exception e) {
            return new ArrayList<Card>();
        }
    }
}