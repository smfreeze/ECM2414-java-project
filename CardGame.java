import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CardGame {
    public static void main(String[] args) {
        System.out.println("Please enter the number of players:");
        Scanner terminalReader = new Scanner(System.in);
        String players = terminalReader.next();
        boolean invalidPlayers = true;
        int playerCount = 0;
        while (invalidPlayers) {
            try { // checks if input is a invalid int
                playerCount = Integer.parseInt(players);
                if (Integer.parseInt(players) > 0) {
                    invalidPlayers = false;
                    break;
                }
            } catch (Exception e) {
            }
            System.out.println("");
            System.out.println("Invalid entry");
            System.out.println("Please enter the number of players:");
            players = terminalReader.next();
        }

        System.out.println("Please enter location of pack to load:");
        String pack = terminalReader.next();
        while (!checkPack(pack, playerCount)) {
            System.out.println("");
            System.out.println("Invalid pack file");
            System.out.println("Please enter location of pack to load:");
            pack = terminalReader.next();
        }
        terminalReader.close();
        // Creates 2 arrays for the player and for the card deck threads
        Player[] playerArray = new Player[playerCount];
        CardDeck[] deckArray = new CardDeck[playerCount];

        // Initialises and starts the threads for as many players as there are (there
        // will be an equal amount of deck threads, also started here)
        for (int x = 0; x < playerCount; x++) {
            playerArray[x] = new Player(x + 1);
            playerArray[x].start();
            deckArray[x] = new CardDeck();
            deckArray[x].start();
        }
        int a = 0;
        for (int x = 0; x < playerCount * 4; x++) {
            playerArray[a].addCard(new Card(cardValuesArray.get(x)));
            if (a + 1 >= playerCount) {
                a = 0;
            } else {
                a++;
            }
        }
    }

    public static boolean checkPack(String file, int players) {
        int count = 0;
        try {
            File f = new File(file);
            Scanner read = new Scanner(f);
            while (read.hasNextLine()) {
                String data = read.nextLine();
                if (Integer.parseInt(data) < 0) {
                    read.close();
                    return false;
                }
                count++;
            }
            if (count != 8 * players) {
                read.close();
                return false;
            }
            read.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
