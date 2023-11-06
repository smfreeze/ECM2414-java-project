import java.util.ArrayList;

public class Player extends Thread {
    private final int playerNumber;
    private Boolean done = false;
    private ArrayList<Card> cardsList = new ArrayList<Card>();

    public void run() {
        while (!done) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
}
