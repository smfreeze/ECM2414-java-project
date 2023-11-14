import java.util.ArrayList;

public class Player implements Runnable {
    private final int playerNumber;
    private Boolean done = false;
    private ArrayList<Card> cardsList = new ArrayList<Card>();

    @Override
    public void run() {
        while (!done) {

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
