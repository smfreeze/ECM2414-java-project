package cards;

import java.util.LinkedList;
import java.util.Queue;

public class CardDeck {
    private Queue<Card> deck = new LinkedList<>();
    private Integer number;
    public static final Object globalLock = new Object();

    public CardDeck(Integer number) {
        this.number = number;
    }

    public synchronized void addCard(Card card) {
        synchronized (globalLock) {
            deck.add(card);
        }
    }

    public synchronized Integer getNumber() {
        return number;
    }

    public synchronized Card removeCard() {
        synchronized (globalLock) {
            if (deck.size() != 0) {
                return deck.remove();
            }
            return null;
        }
    }

    public synchronized Integer getSize() {
        return deck.size();
    }

    public String handToString() {
        StringBuilder builder = new StringBuilder();
        for (Card card : deck) {
            builder.append(card.getNumber()).append(" ");
        }
        return builder.toString().trim();
    }
}