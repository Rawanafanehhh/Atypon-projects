import java.util.ArrayList;
import java.util.List;

public class ReverseCard {
    public static Player reverseCard(List<Player> players, Player player) {

        System.out.println("Reversing the order of play.");
        if (players.size() == 2) {

            System.out.println("play again!");
            return player;

        }

        List<Player> reversedPlayers = new ArrayList<>();
        for (int i = players.size() - 1; i >= 0; i--) {
            Player p = players.get(i);
            reversedPlayers.add(p);
        }

        System.out.println("the order before reversed: ");
        int w = 1;
        for (Player x : PlayerInputHandler.getPlayerOrder(players))
            System.out.println((w++) + " :" + x.getName());
        System.out.println();
        System.out.println("Order reversed!");
        System.out.println("the order will be :");
        int n = 1;
        for (Player y : reversedPlayers) {
            System.out.println((n++) + " :" + y.getName());
        }
        Player p = PlayerInputHandler.getNextPlayer(player, players);
        return p;

    }
}
