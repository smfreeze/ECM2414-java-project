import java.util.ArrayList;

public class Player {
    private int playerNumber;
    private ArrayList<Card> cardsList = new ArrayList<Card>();

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Card removeCard(int pos) {
        return cardsList.remove(pos);
    }

    public void addCard(Card card) {
        cardsList.add(card);
    }
}
