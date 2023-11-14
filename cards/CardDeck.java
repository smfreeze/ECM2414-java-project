import java.util.LinkedList;
import java.util.Queue;

public class CardDeck {
    private Queue<Card> deck = new LinkedList<>();

    public synchronized void addCard(Card card) {
        deck.add(card);
    }

    public synchronized Card removeCard() {
        return deck.remove();
    }
}
