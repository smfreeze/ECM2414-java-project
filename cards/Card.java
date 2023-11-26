package cards;

public class Card {
    private Integer number;

    // Constructs Card object with the cards' number
    public Card(Integer number) {
        this.number = number;
    }

    // Returns the card number
    public synchronized Integer getNumber() {
        return number;
    }
}
