import cards.CardGame;
import cards.Card;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class testCardGame {
    @Test
    public void testCheckPlayers() {
        // negative number of players
        assertEquals(0,CardGame.checkPlayers("-1"));
        // string can not be converted to an integer
        assertEquals(0,CardGame.checkPlayers("abc"));
        // can not have 0 players
        assertEquals(0,CardGame.checkPlayers("0")); 
        // can not have 1 player
        assertEquals(0,CardGame.checkPlayers("1")); 
        // valid number of players
        assertEquals(5,CardGame.checkPlayers("5"));
    }

    @Test
    public void testCheckPack() {
        int count = CardGame.checkPack("cardsTest/testPacks/countPack.txt", 2).size();
        int negative = CardGame.checkPack("cardsTest/testPacks/negativePack.txt", 2).size();
        int string = CardGame.checkPack("cardsTest/testPacks/stringPack.txt", 2).size();
        int nonExist = CardGame.checkPack("cardsTest/testPacks/nonExistent.txt", 2).size();
        int few = CardGame.checkPack("cardsTest/testPacks/correctPack.txt", 4).size();
        int many = CardGame.checkPack("cardsTest/testPacks/correctPack.txt", 1).size();
        int correct = CardGame.checkPack("cardsTest/testPacks/correctPack.txt", 2).size();
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
        // correct number of players (16) and cards (2)
        assertEquals(16,correct);
    }

    @Test
    public void testDealHand() {

    }

}

