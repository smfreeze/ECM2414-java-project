import cards.CardDeck;
import cards.Player;
import cards.Card;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class testPlayer { /* WORK IN PROGRESS */
    CardDeck deck1 = new CardDeck(1);
    CardDeck deck2 = new CardDeck(2);
    Player player1 = new Player(1, new ArrayList<Card>(), deck1,deck2);
}
