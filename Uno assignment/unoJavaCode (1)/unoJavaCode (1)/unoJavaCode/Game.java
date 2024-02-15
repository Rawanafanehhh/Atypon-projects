import java.util.*;
public abstract class Game {
    private final List<Player> players;
    public Game() {
        players = createPlayers();
    }
    protected abstract List<Player> createPlayers();
    protected abstract void initializeGame(List<Player> players);
    protected abstract boolean isGameEnded();
    protected abstract void processTurn(Player player);
    protected abstract void processActionCard(UnoCard card, Player player);
    protected abstract void processNumberCard(UnoCard card, Player player);
    protected abstract void processWildCard(UnoCard card, Player player);
    protected abstract void endGame() ;
    public void play() {
        initializeGame(getPlayers());
        processTurn(players.get(0));
    }
    public List<Player> getPlayers() {
        return players;
    }

}