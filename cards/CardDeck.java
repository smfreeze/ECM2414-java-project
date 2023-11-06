import java.util.LinkedList;
import java.util.Queue;

public class CardDeck extends Thread {
    private Queue<Card> deck = new LinkedList<>();
    private Boolean done = false;

    public void run() {
        while (!done) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void addToDeck(Card card) {
        deck.add(card);
    }

    public synchronized Card removeCard() {
        return deck.remove();
    }

    public synchronized void stopThread() {
        done = true;
    }
}
