import java.util.ArrayList;
import java.util.List;

public class Player implements Runnable {
    private int playerId;
    private List<Card> hand;
    private Game game;
    private boolean finished;
    private static final int MOVE_DELAY = 1500;

    public Player(int playerId, Game game) {
        this.playerId = playerId;
        this.hand = new ArrayList<>();
        this.game = game;
        this.finished = false;
    }

    @Override
    public void run() {

        while (true) {

            synchronized (game.getTurnLock()) {
                if(game.getPlayers().size()==1){
                    break;
                }
                while (this != game.getCurrentPlayer()) {
                    try {
                        game.getTurnLock().wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                // It's the player's turn
                if (hand.isEmpty()) {
                    setFinished(true);
                    game.removePlayer(this);
                }else {
                    if(game.getPlayers().size()==1){
                        break;
                    }
                    performTurn();
                }
                // Check if player has finished their cards


                // Notify the next player
                game.nextTurn();

                game.getTurnLock().notifyAll();
            }
        }
    game.getThreadPool().shutdown();
        System.exit(0);
    }


    private synchronized void performTurn() {

        System.out.println("________________________________________________________");
        System.out.println("it's player "+this.getPlayerId()+"'s turn");
        System.out.println("Player has :"+this.getHand());




        Player nextPlayer = game.getNextPlayer(this);
        Card drawnCard = nextPlayer.drawRandomCard();
        if (drawnCard != null) {
            System.out.println("the Player "+this.playerId+" pick randomly card from Player "+nextPlayer.playerId);
            addToHand(drawnCard);
            System.out.println("the drawn card is: "+ drawnCard);
        }
        System.out.println("Checking if there any new pairs....");
        this.throwMatchingPairs();

        try {
            Thread.sleep(MOVE_DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void addToHand(Card card) {
        hand.add(card);
    }

    public Card drawRandomCard() {
        if (!hand.isEmpty()) {
            int randomIndex = (int) (Math.random() * hand.size());
            return hand.remove(randomIndex);
        }
        return null;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void throwMatchingPairs() {
        List<Card> pairsToDiscard = new ArrayList<>();

        for (int i = 0; i < hand.size(); i++) {
            for (int j = i + 1; j < hand.size(); j++) {
                if ((hand.get(i).getRank().equals(hand.get(j).getRank() )) && ( matchingSuits(hand.get(i).getSuit(), hand.get(j).getSuit()))) {

                    pairsToDiscard.add(hand.get(i));
                    pairsToDiscard.add(hand.get(j));
                }
            }
        }

        if(pairsToDiscard.size()!=0){
            hand.removeAll(pairsToDiscard);
            System.out.println("the pairs cards removes");

        }
        else{
            System.out.println("there is no pairs");
        }

    }

    private boolean matchingSuits(CardSuit suit1, CardSuit suit2) {
        return (suit1 == CardSuit.SPADES && suit2 == CardSuit.CLUBS) ||
                (suit1 == CardSuit.CLUBS && suit2 == CardSuit.SPADES) ||
                (suit1 == CardSuit.DIAMONDS && suit2 == CardSuit.HEARTS) ||
                (suit1 == CardSuit.HEARTS && suit2 == CardSuit.DIAMONDS);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

}