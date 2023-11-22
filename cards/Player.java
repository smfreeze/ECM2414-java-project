package cards;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Player implements Runnable {
    private final Integer playerNumber;
    // Shared memory winner:
    private static final AtomicReference<Integer> winner = new AtomicReference<>(null);
    private ArrayList<Card> cardsList = new ArrayList<Card>();
    private CardDeck leftDeck;
    private CardDeck rightDeck;

    @Override
    public void run() {
        String fileName = "player" + playerNumber + "_output.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Log initial hand to file
            writer.write("player " + playerNumber + " initial hand " + handToString() + "\n");
            while (winner.get() == null) {
                int removePos = cardToRemove();
                if (removePos != -1) {
                    synchronized (Player.class) {
                        if (winner.get() != null)
                            break;
                        if ((leftDeck.getSize() == 5 || leftDeck.getSize() == 4)
                                && (rightDeck.getSize() == 3 || rightDeck.getSize() == 4)) {

                            // Draw and log card to file
                            Card drawnCard = leftDeck.removeCard();
                            cardsList.add(drawnCard);
                            writer.write(
                                    "player " + playerNumber + " draws a " + drawnCard.getNumber() + " from deck "
                                            + leftDeck.getNumber() + "\n");

                            // Discard and log card to file
                            writer.write(
                                    "player " + playerNumber + " discards a " + cardsList.get(removePos).getNumber()
                                            + " to deck " + rightDeck.getNumber() + "\n");
                            rightDeck.addCard(cardsList.remove(removePos));

                            // Log current hand to file
                            writer.write("player " + playerNumber + " current hand is " + handToString() + "\n");
                        }
                    }
                } else {
                    synchronized (Player.class) {
                        if (winner.get() == null) {
                            winner.set(playerNumber);
                            writer.write("player " + playerNumber + " wins\n");
                            writer.write("player " + playerNumber + " exits\n");
                            writer.write("player " + playerNumber + " final hand:" + handToString() + "\n");
                        }
                    }
                    return;
                }
            }
            writer.write("player " + winner + " has informed player " + playerNumber + " that player "
                    + winner + " has won\n");
            writer.write("player " + playerNumber + " exits\n");
            writer.write("player " + playerNumber + " final hand: " + handToString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String handToString() {
        StringBuilder builder = new StringBuilder();
        for (Card card : cardsList) {
            builder.append(card.getNumber()).append(" ");
        }
        return builder.toString().trim();
    }

    public Player(int playerNumber, ArrayList<Card> cardsList, CardDeck leftDeck, CardDeck rightDeck) {
        this.playerNumber = playerNumber;
        this.cardsList = cardsList;
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
    }

    private synchronized int cardToRemove() {
        for (int pos = 0; pos < cardsList.size(); pos++) {
            if (cardsList.get(pos).getNumber() != playerNumber) {
                return pos;
            }
        }
        return -1;
    }

    public synchronized boolean isWinner() {
        return playerNumber.equals(winner);
    }

}