import java.util.*;

public class Deck {
    private List<Card> cards;
    private Random random;

    public Deck() {
        this.cards = new ArrayList<>();
        this.random = new Random();
        initializeDeck();
        shuffleDeck();
    }

    private void initializeDeck() {
        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                if (rank != CardRank.JOKER) {
                    Card card = new Card(suit, rank);
                    this.cards.add(card);
                }
            }
        }
        Card joker = new Card(null, CardRank.JOKER);
        this.cards.add(joker);
    }

    private void shuffleDeck() {
        Collections.shuffle(this.cards);
    }

    public Card drawCard() {
        if (this.cards.isEmpty()) {
            return null;
        }
        int randomIndex = this.random.nextInt(this.cards.size());
        return this.cards.remove(randomIndex);
    }

    public int getSize() {
        return this.cards.size();
    }
}