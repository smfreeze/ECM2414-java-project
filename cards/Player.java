package cards;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Random;

public class Player implements Runnable {
    private final Integer playerNumber;
    private static final AtomicReference<Integer> winner = new AtomicReference<>(null);
    private final ArrayList<Card> cardsList = new ArrayList<>();
    private final CardDeck leftDeck;
    private final CardDeck rightDeck;

    @Override
    public void run() {
        // Creates output text files for player and deck
        String playerFileName = "player" + playerNumber + "_output.txt";
        String deckFileName = "deck" + playerNumber + "_output.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(playerFileName));
                BufferedWriter writer2 = new BufferedWriter(new FileWriter(deckFileName))) {
            writer.write("player " + playerNumber + " initial hand " + handToString() + "\n");

            // Stops when a winner has been found
            while (winner.get() == null) {
                int removePos = cardToRemove();
                if (removePos != -1) {
                    // Only draws and remove when decks are not empty
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
                         // Player has won so writes into ouput file as winner
                        if (winner.compareAndSet(null, playerNumber)) {
                            writer.write("player " + playerNumber + " wins\n");
                            writer.write("player " + playerNumber + " exits\n");
                            writer.write("player " + playerNumber + " final hand: " + handToString() + "\n");
                            writer2.write("deck" + leftDeck.getNumber() + " contents: " + leftDeck.deckToString());
                            System.out.println("player " + playerNumber + " wins");
                        } else {
                            // Winner has been found so writes into output file as loser
                            writer.write("player " + winner + " has informed player " + playerNumber + " that player " + winner
                                    + " has won\n");
                            writer.write("player " + playerNumber + " exits\n");
                            writer.write("player " + playerNumber + " final hand: " + handToString() + "\n");
                            writer2.write("deck" + leftDeck.getNumber() + " contents: " + leftDeck.deckToString());
                        }
                    }
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Constructs Player object with the player number, hand, decks to the left and right
    public Player(int playerNumber, ArrayList<Card> cardsList, CardDeck leftDeck, CardDeck rightDeck) {
        this.playerNumber = playerNumber;
        this.cardsList.addAll(cardsList);
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
    }

    // Converts the player's hand into string format to be written into their output file
    private synchronized String handToString() {
        StringBuilder builder = new StringBuilder();
        synchronized (this) {
            for (Card card : cardsList) {
                builder.append(card.getNumber()).append(" ");
            }
        }
        return builder.toString().trim();
    }

    // Chooses which cards to remove from hand, if none exists the player wins
    private synchronized int cardToRemove() {
        synchronized (this) {
            Random rand = new Random();
            ArrayList<Integer> positions = new ArrayList<Integer>();
            for (int pos = 0; pos < cardsList.size(); pos++) {
                if (cardsList.get(pos).getNumber() != playerNumber) {
                    positions.add(pos);
                }
            }
            if (!positions.isEmpty()) {
                // Chooses a random non same denomination card
                return positions.get(rand.nextInt(positions.size()));
            } else {
                return -1;
            }

        }
    }
}