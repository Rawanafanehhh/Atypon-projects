import java.util.ArrayList;
import java.util.List;

public class GameRule {
    private Game game;
    private List<Player> players;
    private Deck deck;
    public GameRule(Game game ,List<Player> players,Deck deck) {
        this.game = game;
        this.players=players;
        this.deck=deck;
    }

    public void dealCards() {
        int totalCards = game.getDeck().getSize();
        int cardsPerPlayer = totalCards / players.size();
        int extraCards = totalCards % players.size();

        for (Player player :players) {
            int numCardsToDeal = cardsPerPlayer;


            if (extraCards > 0) {
                numCardsToDeal++;
                extraCards--;
            }

            for (int i = 0; i < numCardsToDeal; i++) {
                Card card = deck.drawCard();
                player.addToHand(card);
            }
        }

        System.out.println("Each player has at least " + cardsPerPlayer + " cards");

        for (Player player : players) {
            player.throwMatchingPairs();
        }

        System.out.println("All players threw all the matching pairs");
    }


    public synchronized boolean isGameOver() {
        List<Player> playersToRemove = new ArrayList<>();

        for (Player player : players) {
            if (!player.isFinished() && player.getHand().isEmpty()) {
                player.setFinished(true);
                playersToRemove.add(player);
            }
        }

        players.removeAll(playersToRemove);

        if (players.size() == 1) {
            System.out.println("_______________________________________________________________________");
            System.out.println("_______________________________________________________________________");
            System.out.println("PLAYER " +players.get(0).getPlayerId() +" THE FINAL ONE WHO HAS THE JOKER! Player "+ players.get(0).getPlayerId() + " IS THE LOSER ");
            System.out.println("_______________________________________________________________________");
            System.out.println("_______________________________________________________________________");

            return true;
        }

        return false;
    }
}
