import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

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
    }

    public static int checkPlayers(String players) {
        int playerCount = 0;
        // Check if input players can be converted to an integer
        try {
            playerCount = Integer.parseInt(players);
            if (Integer.parseInt(players) > 0) {
                return playerCount;
                // Returns if this is the case (verifying the player input)
            }
        } catch (Exception e) {
        }
        // Else, return 0 for invalid user input
        return 0;
    }

    public static ArrayList<Card> checkPack(String file, int players) {
        int count = 0;
        ArrayList<Card> cards = new ArrayList<Card>();
        try {
            // Tries to open file from user pack location input
            File f = new File(file);
            Scanner read = new Scanner(f);
            // If succeeds, loop through each line in file, confirm it 
            // is an integer, instantiates card and adds to array
            // of cards. Also counts number of lines for future
            // verification.
            while (read.hasNextLine()) {
                String data = read.nextLine();
                int num = Integer.parseInt(data); 
                if (num < 0) {
                    read.close();
                    return new ArrayList<Card>();
                }
                cards.add(new Card(num));
                count++;
            }
            // Checks number of lines is invalid, returns empty array
            // list, else returns the array of cards.
            if (count != 8 * players) {
                read.close();
                return new ArrayList<Card>();
            }
            read.close();
            return cards;
        } catch (Exception e) {
            return new ArrayList<Card>();
        }
    }
}
