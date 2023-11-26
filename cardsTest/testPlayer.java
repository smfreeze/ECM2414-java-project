import cards.CardDeck;
import cards.Player;
import cards.Card;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class testPlayer { 
    CardDeck deck1 = new CardDeck(1);
    CardDeck deck2 = new CardDeck(2);
    ArrayList<Card> playerHand1 = new ArrayList<Card>(Arrays.asList(new Card(1), new Card(1), new Card(2), new Card(3)));
    ArrayList<Card> playerHand2 = new ArrayList<Card>(Arrays.asList(new Card(4), new Card(4), new Card(2), new Card(3)));
    ArrayList<Card> playerHand3 = new ArrayList<Card>(Arrays.asList(new Card(2), new Card(2), new Card(2), new Card(2)));
    ArrayList<Card> playerHand4 = new ArrayList<Card>(Arrays.asList(new Card(4), new Card(4), new Card(4), new Card(4)));
    Player player1 = new Player(1, playerHand1, deck1, deck2);
    Player player2 = new Player(2, playerHand2, deck1, deck2);
    Player player3 = new Player(3, playerHand3, deck1, deck2);
    Player player4 = new Player(4, playerHand4, deck1, deck2);

    @Test
    public void testHandToString() throws Exception {
        Method privateMethod = Player.class.getDeclaredMethod("handToString");
        privateMethod.setAccessible(true);

        // outputs the players hands
        assertEquals("1 1 2 3", privateMethod.invoke(player1));
        assertEquals("4 4 2 3", privateMethod.invoke(player2));
        assertEquals("2 2 2 2", privateMethod.invoke(player3));
        assertEquals("4 4 4 4", privateMethod.invoke(player4));
    }

    @Test
    public void testCardToRemove() throws Exception {
        Method privateMethod = Player.class.getDeclaredMethod("cardToRemove");
        privateMethod.setAccessible(true);

        // contains non same denomination card after self numbered card
        assertTrue(Arrays.asList(new Integer[]{2, 3}).contains(privateMethod.invoke(player1)));
        // contains non same denomination card at the front
        assertTrue(Arrays.asList(new Integer[]{0, 1, 3}).contains(privateMethod.invoke(player2)));
        // full non same denomination cards in hand
        assertTrue(Arrays.asList(new Integer[]{0, 1, 2, 3}).contains(privateMethod.invoke(player3)));
        // full same denomination cards in hand (winner) 
        assertEquals(-1, privateMethod.invoke(player4));
    }
}
