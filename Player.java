package cards;

import java.util.ArrayList;

public class Player implements Runnable {
    private final Integer playerNumber;
    private Boolean done;
    private ArrayList<Card> cardsList = new ArrayList<Card>();
    private CardDeck leftDeck;
    private CardDeck rightDeck;

    @Override
    public void run() {
        while (!done) {
            int removePos = cardToRemove();
            if (removePos != -1) {
                if ((leftDeck.getSize() == 5 || leftDeck.getSize() == 4)
                        && (rightDeck.getSize() == 3 || rightDeck.getSize() == 4)) {
                    cardsList.add(leftDeck.removeCard());
                    rightDeck.addCard(cardsList.remove(removePos));
                    System.out.println("player" + playerNumber.toString() + "left:" + leftDeck.printDeck());
                    System.out.println("player" + playerNumber.toString() + "right:" + rightDeck.printDeck());
                    System.out.println("player" + playerNumber.toString() + "deck" + printDeck2());
                }
            } else {
                System.out.println(playerNumber);
                System.out.println("player" + playerNumber.toString() + "left:" + leftDeck.printDeck());
                System.out.println("player" + playerNumber.toString() + "right:" + rightDeck.printDeck());
                System.out.println("player" + playerNumber.toString() + "deck" + printDeck2());
                done = true;

            }
        }
        System.out.println("player" + playerNumber.toString() + "deck" + printDeck2());
    }

    private synchronized String printDeck2() {
        String text = "";
        for (Card s : cardsList) {
            text += s.getNumber().toString();
        }
        return text;
    }

    public Player(int playerNumber, ArrayList<Card> cardsList, CardDeck leftDeck, CardDeck rightDeck, Boolean done) {
        this.playerNumber = playerNumber;
        this.cardsList = cardsList;
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
        this.done = done;
    }

    private synchronized int cardToRemove() {
        for (int pos = 0; pos < cardsList.size(); pos++) {
            if (cardsList.get(pos).getNumber() != playerNumber) {
                return pos;
            }
        }
        return -1;
    }

}