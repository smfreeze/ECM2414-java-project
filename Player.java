import java.util.ArrayList;

public class Player extends Thread {
    private final int playerNumber;
    private Boolean done = false;
    private ArrayList<Card> cardsList = new ArrayList<Card>();
    private int count = 0;

    public void run() {
        while (!Thread.interrupted()) {
            count++; // arbitrary calculation
        }
    }

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public synchronized Card removeCard(int pos) {
        return cardsList.remove(pos);
    }

    public synchronized void addCard(Card card) {
        cardsList.add(card);
    }

    public synchronized void stopThread() {
        done = true;
    }

    public synchronized void print() {
        System.out.println(this.getName() + "Printed");
    }
}
