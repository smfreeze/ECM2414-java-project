package cardsTest;

import cards.CardGame;
import cards.Card;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class testCardGame {
    @Test
    public void testCheckPlayers() throws Exception {
        CardGame myClass = new CardGame();
        Method privateMethod = CardGame.class.getDeclaredMethod("checkPlayers", String.class);
        privateMethod.setAccessible(true);

        // negative number of players
        assertEquals(0,privateMethod.invoke(myClass, "-1"));
        // string can not be converted to an integer
        assertEquals(0,privateMethod.invoke(myClass, "abc"));
        // can not have 0 players
        assertEquals(0,privateMethod.invoke(myClass, "0")); 
        // can not have 1 player
        assertEquals(0,privateMethod.invoke(myClass, "1")); 
        // valid number of players
        assertEquals(5,privateMethod.invoke(myClass, "5"));
    }

    @Test
    public void testCheckPack() throws Exception {
        CardGame myClass = new CardGame();
        Method privateMethod = CardGame.class.getDeclaredMethod("checkPack", String.class, int.class);
        privateMethod.setAccessible(true);

        int count = ((ArrayList<Card>) privateMethod.invoke(myClass, "cardsTest/testPacks/countPack.txt", 2)).size();
        int negative = ((ArrayList<Card>) privateMethod.invoke(myClass, "cardsTest/testPacks/negativePack.txt", 2)).size();
        int string = ((ArrayList<Card>) privateMethod.invoke(myClass, "cardsTest/testPacks/stringPack.txt", 2)).size();
        int nonExist = ((ArrayList<Card>) privateMethod.invoke(myClass, "cardsTest/testPacks/nonExistent.txt", 2)).size();
        int few = ((ArrayList<Card>) privateMethod.invoke(myClass, "cardsTest/testPacks/correctPack.txt", 4)).size();
        int many = ((ArrayList<Card>) privateMethod.invoke(myClass, "cardsTest/testPacks/correctPack.txt", 1)).size();
        int noWin = ((ArrayList<Card>) privateMethod.invoke(myClass, "cardsTest/testPacks/noWinPack.txt", 2)).size();
        int correct = ((ArrayList<Card>) privateMethod.invoke(myClass, "cardsTest/testPacks/correctPack.txt", 2)).size();
        // only contains 15 cards
        assertEquals(0,count);
        // contains negative value
        assertEquals(0,negative);
        // contains string value
        assertEquals(0,string);
        // file does not exist
        assertEquals(0,nonExist);
        // too few cards (16) for number of players (4)
        assertEquals(0,few); 
        // too many cards (16) for number of players (1)
        assertEquals(0,many);
        // does not contain any win possibilities
        assertEquals(0,noWin);
        // correct number of players (16) and players (2)
        assertEquals(16,correct);
    }
}

