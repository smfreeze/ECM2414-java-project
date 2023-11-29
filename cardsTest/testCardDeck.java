package cardsTest;

import cards.Card;
import cards.CardDeck;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class testCardDeck {
    CardDeck deck1 = new CardDeck(1);
    CardDeck deck2 = new CardDeck(2);
    CardDeck deck3 = new CardDeck(3);
    private void fillTestDecks() {
        deck1.addCard(new Card(1));
        deck2.addCard(new Card(2));
        deck2.addCard(new Card(2));
        deck3.addCard(new Card(3));
        deck3.addCard(new Card(3));
        deck3.addCard(new Card(3));
    }

    @Test
    public void testAddCardAndGetSize() {
        fillTestDecks();
        // deck has 1 card and counts
        assertEquals(Integer.valueOf(1), deck1.getSize());
        // deck has 2 card and counts
        assertEquals(Integer.valueOf(2), deck2.getSize());
        // deck has 3 card and counts
        assertEquals(Integer.valueOf(3), deck3.getSize());
    }

    @Test
    public void testGetNumber() {
        // deck number is 1
        assertEquals(Integer.valueOf(1), deck1.getNumber());
        // deck number is 2
        assertEquals(Integer.valueOf(2), deck2.getNumber());
        // deck number is 3
        assertEquals(Integer.valueOf(3), deck3.getNumber());
    }

    @Test
    public void testRemoveCard() {
        fillTestDecks();
        deck1.removeCard();
        deck2.removeCard();
        deck3.removeCard();
        deck3.removeCard();
        // removes one card leaving none in deck
        assertEquals(Integer.valueOf(0), deck1.getSize());
        // removes one card leaving one in deck
        assertEquals(Integer.valueOf(1), deck2.getSize());
        // removes two cards 
        assertEquals(Integer.valueOf(1), deck3.getSize());
        // nothing in deck to remove
        deck1.removeCard();
        assertEquals(Integer.valueOf(0), deck1.getSize());
    }
}
