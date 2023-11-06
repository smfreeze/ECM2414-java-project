import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class CardGame {
    public static void main(String[] args) {
        //asks user to input number of players
        System.out.println("Please enter the number of players:");
        Scanner terminalReader = new Scanner(System.in); //opens reader
        int playerCount = checkPlayers(terminalReader.next());
        while (playerCount == 0) { //stops when value is not 0
            System.out.println("");
            System.out.println("Invalid entry");
            System.out.println("Please enter the number of players:");
            playerCount = checkPlayers(terminalReader.next()); //keeps asking if invalid
        }

        //asks user to input pack file location
        System.out.println("Please enter location of pack to load:");
        ArrayList<Card> pack = checkPack(terminalReader.next(), playerCount);
        while (pack.isEmpty()) { //stops when arraylist is not empty
            System.out.println("");
            System.out.println("Invalid pack file");
            System.out.println("Please enter location of pack to load:");
            pack = checkPack(terminalReader.next(), playerCount); //keeps asking if invalid
        }
        terminalReader.close(); //closes reader
    }

    public static int checkPlayers(String players) {
        int playerCount = 0;
        try { //checks if input is a invalid int
            playerCount = Integer.parseInt(players);
            if (Integer.parseInt(players) > 0) { //exits if positive
                return playerCount;
            }
        } catch (Exception e) {
        }
        return 0; //invalid number of players
    }

    public static ArrayList<Card> checkPack(String file, int players) {
        int count = 0;
        ArrayList<Card> cards = new ArrayList<Card>();
        try {
            File f = new File(file); //opens text file
            Scanner read = new Scanner(f);
            while (read.hasNextLine()) { //continues if there is a next line
                String data = read.nextLine();
                int num = Integer.parseInt(data); //converts line to integer
                if (num < 0) { //checks if in is negative
                    read.close();
                    return new ArrayList<Card>(); //empty arraylist
                }
                cards.add(new Card(num)); //adds card object
                count++; //increments count
            }
            if (count != 8 * players) { //checks if count is the incorrect size
                read.close();
                return new ArrayList<Card>();
            }
            read.close();
            return cards;
        } catch (Exception e) { //file not found and string to integer error
            return new ArrayList<Card>();
        }
    }
}
