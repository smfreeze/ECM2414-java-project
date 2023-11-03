import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CardGame {
    public static void main(String[] args) {
        System.out.println("Please enter the number of players:");
        Scanner terminalReader = new Scanner(System.in);
        String players = terminalReader.next();
        System.out.println("Please enter location of pack to load:");
        String pack = terminalReader.next();
        terminalReader.close();
    }
}
