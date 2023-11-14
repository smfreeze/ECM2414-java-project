package cards;
import java.util.ArrayList;

public class Player implements Runnable {
    private final int playerNumber;
    private Boolean done = false;
    private ArrayList<Card> cardsList = new ArrayList<Card>();
    private CardDeck leftDeck;
    private CardDeck rightDeck;

    @Override
    public void run() {
        while (!done) {
            int removePos = cardToRemove();
            if (removePos != -1) {
                cardsList.add(leftDeck.removeCard());
                rightDeck.addCard(cardsList.remove(removePos));
            } else {
                System.out.println(playerNumber);
                continue;
                // win condition
            }
        }
    }

    public Player(int playerNumber, ArrayList<Card> cardsList, CardDeck leftDeck, CardDeck rightDeck) {
        this.playerNumber = playerNumber;
        this.cardsList = cardsList;
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
    }

    private int cardToRemove() {
        for (int pos = 0; pos < cardsList.size(); pos++) {
            if (cardsList.get(pos).getNumber() != playerNumber) {
                return pos;
            }
        }
        return -1;
    }

}