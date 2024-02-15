import java.util.List;

public class CardFactory {

    public static Player actionCards(Player player, List<Player> players, CardType type, List<UnoCard> unoCards) {
        switch (type) {
            case SKIP:
                return SkipCard.skipCard(player, players);
            case REVERSE:
                return ReverseCard.reverseCard(players, player);
            case DRAW_TWO:
                return DrawTwoCard.drawTwoCard(player, players, unoCards);
            default:
                throw new IllegalArgumentException("Invalid card type");
        }
    }

}
