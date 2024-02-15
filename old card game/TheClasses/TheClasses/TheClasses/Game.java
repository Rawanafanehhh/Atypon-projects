import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Game {

    private GameRule gameRules;
    private static final int MOVE_DELAY = 1500;

    public List<Player> getPlayers() {
        return players;
    }

    private List<Player> players;
    private Deck deck;


    private ExecutorService threadPool;
    private int currentPlayerIndex;
    private final Object turnLock = new Object();

    public Game(int numPlayers) {
        players = new ArrayList<>();
        deck = new Deck(); // Create a new deck
        threadPool = Executors.newFixedThreadPool(numPlayers);
        gameRules = new GameRule(this,players,deck);

        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player(i, this);
            players.add(player);
        }
    }

    public void startGame() {
        gameRules.dealCards();
        startPlayerThreads();
        startFirstTurn();
        waitForGameToFinish();
        threadPool.shutdown();

    }
    private void waitForGameToFinish() {
        while (!isGameOver()) {
            // Wait for game to finish
        }
    }
    private void startFirstTurn() {
        synchronized (turnLock) {
            currentPlayerIndex = 0;
            turnLock.notifyAll();
        }
        try {
            Thread.sleep(MOVE_DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public synchronized void removePlayer(Player player) {
        players.remove(player);
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
    }

    public synchronized Player getNextPlayer(Player currentPlayer) {
        int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(nextPlayerIndex);
    }

    public synchronized void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public synchronized  Object getTurnLock() {
        return turnLock;
    }

    public Deck getDeck() {
        return deck;
    }

    public synchronized boolean isGameOver() {
        return gameRules.isGameOver();

    }

    private void startPlayerThreads() {
        for (Player player : players) {
            threadPool.execute(player);
        }
    }


    public synchronized ExecutorService getThreadPool() {
        return threadPool;
    }

}