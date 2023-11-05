import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CardGame {
    public static void main(String[] args) {
        // System.out.println("Please enter the number of players:");
        // Scanner terminalReader = new Scanner(System.in);
        // String players = terminalReader.next();
        // System.out.println("Please enter location of pack to load:");
        // String pack = terminalReader.next();
        // terminalReader.close();

        final Player test = new Player(1);
        final Player test2 = new Player(2);
        test.start();
        test2.start();
        test.print();
        test.interrupt();
        System.out.println("");
        test.print();

    }
}
