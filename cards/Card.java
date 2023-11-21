package cards;

public class Card {
    private Integer number;

    public Card(Integer number) {
        this.number = number;
    }

    public synchronized Integer getNumber() {
        return number;
    }
}
