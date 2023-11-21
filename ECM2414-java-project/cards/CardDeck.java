package cards;

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

    public synchronized Integer getSize() {
        return deck.size();
    }

    public synchronized String printDeck() {
        String text = "";
        for (Card s : deck) {
            text += s.getNumber().toString();
        }
        return text;
    }
}