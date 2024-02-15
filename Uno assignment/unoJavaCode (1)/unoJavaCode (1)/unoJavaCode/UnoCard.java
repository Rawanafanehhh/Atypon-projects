public class UnoCard {
    private CardType type;
    private CardColor color;
    private int number = -1;
    public UnoCard(CardType type, CardColor color, int number) {
        this.type = type;
        this.color = color;
        this.number = number;
    }
    public UnoCard(CardType type, CardColor color) {
        this.type = type;
        this.color = color;

    }
    public UnoCard(CardType type) {
        this.type = type;

    }
    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }
    public int getNumber() {
        return number;
    }
}
