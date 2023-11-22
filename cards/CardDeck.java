package cards;

import java.util.LinkedList;
import java.util.Queue;

public class CardDeck {
    private Queue<Card> deck = new LinkedList<>();
    private Integer number;

    public CardDeck(Integer number) {
        this.number = number;
    }

    public synchronized void addCard(Card card) {
        deck.add(card);
    }

    public synchronized Integer getNumber() {
        return number;
    }

    public synchronized Card removeCard() {
        return deck.remove();
    }

    public synchronized Integer getSize() {
        return deck.size();
    }
}