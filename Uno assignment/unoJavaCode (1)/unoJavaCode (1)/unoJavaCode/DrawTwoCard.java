import java.util.List;

public class DrawTwoCard {
    public static Player drawTwoCard(Player player, List<Player> players, List<UnoCard> unoCards) {

        Player nextP = PlayerInputHandler.getNextPlayer(player, players);
        System.out.println("Drawing two cards for " + nextP.getName());
        for (int i = 0; i < 2; i++) {
            UnoCard drawnCard = CardHandler.drawCardFromDeck(unoCards);
            nextP.addCardToHand(drawnCard);

        }
        System.out.println("Skipping the " + nextP.getName() + "'s turn.");

        Player p = PlayerInputHandler.getNextPlayer(nextP, players);
        return p;

    }
}
