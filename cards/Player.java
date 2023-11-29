package cards;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Player implements Runnable {
    private final Integer playerNumber;
    private static final AtomicInteger winner = new AtomicInteger(-1);
    private final ArrayList<Card> cardsList = new ArrayList<>();
    private final CardDeck leftDeck;
    private final CardDeck rightDeck;

    @Override
    public void run() {
        String playerFileName = "player" + playerNumber + "_output.txt";
        String deckFileName = "deck" + playerNumber + "_output.txt";
        // Creates output text files
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(playerFileName));
                BufferedWriter writer2 = new BufferedWriter(new FileWriter(deckFileName))) {
            // Writes the player's initial hand
            writer.write("player " + playerNumber + " initial hand " + handToString() + "\n");
            while (winner.get() == -1) {
                int removePos = cardToRemove();
                if (removePos != -1) {
                    // Draws a card from the left deck
                    Card drawnCard = leftDeck.removeCard();
                    if (drawnCard != null) {
                        cardsList.add(drawnCard);
                        // Discards a non denomination card from hand to the right deck
                        Card discardedCard = cardsList.remove(removePos);
                        rightDeck.addCard(discardedCard);
                        // Writes the player's actions during the game
                        writer.write("player " + playerNumber + " draws a " + drawnCard.getNumber() + " from deck "
                                + leftDeck.getNumber() + "\n");

                        writer.write("player " + playerNumber + " discards a " + discardedCard.getNumber() + " to deck "
                                + rightDeck.getNumber() + "\n");
                        writer.write("player " + playerNumber + " current hand is " + handToString() + "\n");
                    }
                } else {
                    // Winning condition
                    if (winner.compareAndSet(-1, playerNumber)) {
                        // Writes the winning message
                        writer.write("player " + playerNumber + " wins\n");
                        writer.write("player " + playerNumber + " final hand: " + handToString());
                        // Writes to left deck the deck's cards
                        writer2.write("deck" + leftDeck.getNumber() + " contents: " + leftDeck.deckToString());
                        System.out.println("player " + playerNumber + " wins");
                    }
                    break;
                }
            }
            if (winner.get() != playerNumber) {
                // Losing condition
                // Writes the losing message
                writer.write("player " + winner + " has informed player " + playerNumber + " that player " + winner
                        + " has won\n");
                writer.write("player " + playerNumber + " exits\n");
                writer.write("player " + playerNumber + " final hand: " + handToString());
                writer2.write("deck" + leftDeck.getNumber() + " contents: " + leftDeck.deckToString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player(int playerNumber, ArrayList<Card> cardsList, CardDeck leftDeck, CardDeck rightDeck) {
        this.playerNumber = playerNumber;
        this.cardsList.addAll(cardsList);
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
    }

    // Converts the player's hand into string format
    private String handToString() {
        StringBuilder builder = new StringBuilder();
        for (Card card : cardsList) {
            builder.append(card.getNumber()).append(" ");
        }
        return builder.toString().trim();
    }

    // Chooses a random card in the hand to discard which does not have the same denomination as the player
    private int cardToRemove() {
        Random rand = new Random();
        ArrayList<Integer> positions = new ArrayList<Integer>();
        for (int pos = 0; pos < cardsList.size(); pos++) {
            if (cardsList.get(pos).getNumber() != playerNumber) {
                positions.add(pos);
            }
        }
        if (!positions.isEmpty()) {
            return positions.get(rand.nextInt(positions.size()));
        } else {
            return -1;
        }
    }
}
