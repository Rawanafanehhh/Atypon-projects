import java.util.*;
public class PlayerInputHandler {
    public static UnoCard getPlayerMove(Player player) {
        System.out.println("Choose a move");
        Scanner scanner = new Scanner(System.in);
        int moveIndex;
        UnoCard card;
        do {
            moveIndex = scanner.nextInt();
            if (moveIndex < 1 || moveIndex > player.getHand().size()) {
                System.out.println("Invalid move. Try again.");
            }
        } while (moveIndex < 1 || moveIndex > player.getHand().size());

        card = player.getHand().get(moveIndex - 1);
        return card;
    }
    public static UnoCard getPlayerMove(Player player, List<UnoCard> validMoves) {
        System.out.println("Choose a move (1-" + validMoves.size() + "):");
        Scanner scanner = new Scanner(System.in);
        int moveIndex;
        UnoCard card;
        do {
            moveIndex = scanner.nextInt();
            if (moveIndex < 1 || moveIndex > validMoves.size()) {
                System.out.println("Invalid move. Try again.");
            }
        } while (moveIndex < 1 || moveIndex > validMoves.size());
        card = validMoves.get(moveIndex - 1);
        return card;
    }
    public static Player getNextPlayer(Player currentPlayer, List<Player> player) {
        int currentPlayerIndex = player.indexOf(currentPlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % player.size();
        return player.get(nextPlayerIndex);
    }
    public static List<Player> getPlayerOrder(List<Player> players) {
        List<Player> playerOrder = new ArrayList<>();
        System.out.println("Player order:");
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.println((i + 1) + ". " + player.getName());
            playerOrder.add(player);
        }
        return playerOrder;
    }
}