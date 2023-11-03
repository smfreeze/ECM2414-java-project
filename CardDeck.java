import java.util.Stack;

public class CardDeck {
    private Stack<Card> deck = new Stack<Card>();

    public void addToDeck(Card card) {
        deck.push(card);
    }

    public Card removeCard() {
        return deck.pop();
    }
}
