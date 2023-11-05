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
