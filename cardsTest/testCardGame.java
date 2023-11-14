import cards.CardGame;
import cards.Card;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class testCardGame {
    @Test
    public void testCheckPlayers() {
        assertEquals(0,CardGame.checkPlayers("-1")); // negative players
        assertEquals(0,CardGame.checkPlayers("abc")); // string
        assertEquals(2,CardGame.checkPlayers("2")); 
        assertEquals(5,CardGame.checkPlayers("5"));
    }

    @Test
    public void testCheckPack() { //DOESNT WORK RIGHT NOW
        assertEquals(new ArrayList<Card>(),CardGame.checkPack("testPacks/countPack.txt", 2)); // only contains 15 cards
        assertEquals(new ArrayList<Card>(),CardGame.checkPack("testPacks/negativePack.txt", 2)); // negative value
        assertEquals(new ArrayList<Card>(),CardGame.checkPack("testPacks/stringPack.txt", 2)); // string value
        assertEquals(new ArrayList<Card>(),CardGame.checkPack("testPacks/nonExistent.txt", 2)); // does not exist
        assertEquals(new ArrayList<Card>(),CardGame.checkPack("testPacks/correctPack.txt", 4)); // too few cards
        assertEquals(new ArrayList<Card>(),CardGame.checkPack("testPacks/correctPack.txt", 1)); // too many cards

        assertEquals(new ArrayList<Card>(),CardGame.checkPack("testPacks/correctPack.txt", 2));
    }
}

