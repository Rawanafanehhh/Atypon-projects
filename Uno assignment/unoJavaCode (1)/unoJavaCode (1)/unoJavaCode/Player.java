import java.util.*;
public class Player {
    private final String name;
    private List<UnoCard> hand;
    private int score;
    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }
    public List<UnoCard> getHand() {
        return hand;
    }
    public void setHand(List<UnoCard> hand) {
        this.hand = hand;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public String getName() {
        return name;
    }
    public void addCardToHand(UnoCard drawnCard) {
        hand.add(drawnCard);
    }
    public void removeCardfromHand(UnoCard removeCard) {
        hand.remove(removeCard);
    }
}
