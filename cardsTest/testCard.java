import cards.Card;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class testCard {
    @Test
    public void testGetNumber() {
        Card card1 = new Card(1);
        Card card2 = new Card(57);
        Card card3 = new Card(100000);
        // card number is 1
        assertEquals(Integer.valueOf(1), card1.getNumber());
        // card number is 57
        assertEquals(Integer.valueOf(57), card2.getNumber());
        // card number is 100000
        assertEquals(Integer.valueOf(100000), card3.getNumber());
    }
}
