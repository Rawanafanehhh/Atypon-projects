import java.util.ArrayList;
import java.util.List;
public class UnoDeck {
    private List<UnoCard> cards;
    public UnoDeck() {
        cards = createDeck();
    }
    public List<UnoCard> getCards() {
        return cards;
    }
    private List<UnoCard> createDeck() {
        List<UnoCard> deck = new ArrayList<>();

        for (CardColor color : CardColor.values()) {
            if (color != CardColor.NONE) {
                deck.add(new UnoCard(CardType.ZERO, color, 0));
            }
        }
        for (int number = 1; number <= 9; number++) {
            for (CardColor color : CardColor.values()) {
                if (color != CardColor.NONE) {
                    deck.add(new UnoCard(CardType.values()[number], color, number));
                    deck.add(new UnoCard(CardType.values()[number], color, number));
                }
            }
        }
        for (CardType type : CardType.values()) {
            if (type == CardType.REVERSE || type == CardType.SKIP || type == CardType.DRAW_TWO) {
                for (int i = 0; i < 1; i++) {
                    for (CardColor color : CardColor.values()) {
                        if (color != CardColor.NONE) {
                            deck.add(new UnoCard(type, color));
                            deck.add(new UnoCard(type, color));
                        }
                    }
                }
            }
        }
        for (CardType type : CardType.values()) {
            if (type == CardType.WILD || type == CardType.WILD_DRAW_FOUR) {
                for (int i = 0; i < 4; i++) {
                    deck.add(new UnoCard(type));
                }
            }
        }
        return deck;
    }
}
