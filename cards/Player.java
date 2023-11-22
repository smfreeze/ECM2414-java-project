package cards;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Player implements Runnable {
    private final Integer playerNumber;
    private static final AtomicReference<Integer> winner = new AtomicReference<>(null);
    private final ArrayList<Card> cardsList = new ArrayList<>();
    private final CardDeck leftDeck;
    private final CardDeck rightDeck;

    @Override
    public void run() {
        String playerFileName = "player" + playerNumber + "_output.txt";
        String deckFileName = "deck" + playerNumber + "_output.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(playerFileName));
                BufferedWriter writer2 = new BufferedWriter(new FileWriter(deckFileName))) {
            writer.write("player " + playerNumber + " initial hand " + handToString() + "\n");

            while (winner.get() == null) {
                int removePos = cardToRemove();
                if (removePos != -1) {
                    if (leftDeck.getSize() > 0 && rightDeck.getSize() > 0) {
                        Card drawnCard = leftDeck.removeCard();
                        synchronized (this) {
                            cardsList.add(drawnCard);
                        }

                        writer.write("player " + playerNumber + " draws a " + drawnCard.getNumber() + " from deck "
                                + leftDeck.getNumber() + "\n");

                        Card discardedCard = null;
                        synchronized (this) {
                            discardedCard = cardsList.remove(removePos);
                        }
                        rightDeck.addCard(discardedCard);
                        writer.write("player " + playerNumber + " discards a " + discardedCard.getNumber() + " to deck "
                                + rightDeck.getNumber() + "\n");
                        writer.write("player " + playerNumber + " current hand is " + handToString() + "\n");
                    }
                } else {
                    synchronized (Player.class) {
                        if (winner.compareAndSet(null, playerNumber)) {
                            writer.write("player " + playerNumber + " wins\n");
                            writer.write("player " + playerNumber + " exits\n");
                            writer.write("player " + playerNumber + " final hand: " + handToString() + "\n");
                            writer2.write("deck" + leftDeck.getNumber() + " contents: " + leftDeck.handToString());
                            System.out.println("player " + playerNumber + " wins");
                        }
                    }
                    return;
                }
            }
            writer.write("player " + winner + " has informed player " + playerNumber + " that player " + winner
                    + " has won\n");
            writer.write("player " + playerNumber + " exits\n");
            writer.write("player " + playerNumber + " final hand: " + handToString() + "\n");
            writer2.write("deck" + leftDeck.getNumber() + " contents: " + leftDeck.handToString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized String handToString() {
        StringBuilder builder = new StringBuilder();
        synchronized (this) {
            for (Card card : cardsList) {
                builder.append(card.getNumber()).append(" ");
            }
        }
        return builder.toString().trim();
    }

    public Player(int playerNumber, ArrayList<Card> cardsList, CardDeck leftDeck, CardDeck rightDeck) {
        this.playerNumber = playerNumber;
        this.cardsList.addAll(cardsList);
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
    }

    private synchronized int cardToRemove() {
        synchronized (this) {
            for (int pos = 0; pos < cardsList.size(); pos++) {
                if (cardsList.get(pos).getNumber() != playerNumber) {
                    return pos;
                }
            }
        }
        return -1;
    }

    public synchronized boolean isWinner() {
        synchronized (Player.class) {
            return playerNumber.equals(winner.get());
        }
    }
}