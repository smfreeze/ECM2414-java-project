package cards;

import java.util.LinkedList;
import java.util.Queue;

public class CardDeck {
    private Queue<Card> deck = new LinkedList<>();
    private Integer number;

    // Constructs CardDeck object with the deck's number
    public CardDeck(Integer number) {
        this.number = number;
    }

    // Adds card to the end of the queue
    public synchronized void addCard(Card card) {
        deck.add(card);
    }

    // Returns the deck's number
    public Integer getNumber() {
        return number;
    }

    // Returns the card at the front of the queue and removes it only if the queue
    // is not empty
    public synchronized Card removeCard() {
        if (deck.size() != 0) {
            return deck.remove();
        }
        return null;
    }

    // Returns the length of the deck
    public Integer getSize() {
        return deck.size();
    }

    // Converts the deck into string format to be written into their output file
    public synchronized String deckToString() {
        StringBuilder builder = new StringBuilder();
        for (Card card : deck) {
            builder.append(card.getNumber()).append(" ");
        }
        return builder.toString().trim();
    }
}
