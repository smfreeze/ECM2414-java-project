package cardsTest;

import org.junit.runner.JUnitCore;

public class cardsTestSuite {
    public static void main(String[] args) {
        JUnitCore.main("cardsTest.testCard");
        JUnitCore.main("cardsTest.testCardDeck");
        JUnitCore.main("cardsTest.testCardGame");
        JUnitCore.main("cardsTest.testPlayer");
    }
}
