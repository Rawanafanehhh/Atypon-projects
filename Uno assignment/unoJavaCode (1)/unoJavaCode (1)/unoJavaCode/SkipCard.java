import java.util.List;

public class SkipCard {
    public static Player skipCard(Player player, List<Player> players) {

        Player nextPlayer = PlayerInputHandler.getNextPlayer(player, players);
        System.out.println("Skipping the " + nextPlayer.getName() + "'s turn.");
        Player next = PlayerInputHandler.getNextPlayer(nextPlayer, players);

        return next;
    }
}


