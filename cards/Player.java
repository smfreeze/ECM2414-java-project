import java.util.ArrayList;

public class Player implements Runnable {
    private final int playerNumber;
    private Boolean done = false;
    private ArrayList<Card> cardsList = new ArrayList<Card>();
    private CardDeck leftDeck;
    private CardDeck rightDeck;

    @Override
    public void run() {
        for (Card value : cardsList) {
            System.out.println(value.getNumber());
        }
        leftDeck.printDeck();
        rightDeck.printDeck();
    }

    public Player(int playerNumber, ArrayList<Card> cardsList, CardDeck leftDeck, CardDeck rightDeck) {
        this.playerNumber = playerNumber;
        this.cardsList = cardsList;
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
    }

    /*
     * private synchronized Card removeCard(int pos) {
     * return cardsList.remove(pos);
     * }
     * 
     * private synchronized void addCard(Card card) {
     * cardsList.add(card);
     * }
     * 
     * private synchronized void stopThread() {
     * done = true;
     * }
     */
}
