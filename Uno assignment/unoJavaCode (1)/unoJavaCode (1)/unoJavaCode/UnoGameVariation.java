import java.util.*;
public class UnoGameVariation extends Game {
    private UnoCard lastCard;
    private CardColor color = CardColor.valueOf("NONE");
    UnoDeck unoDeck = new UnoDeck();
    List<UnoCard> unoCards = unoDeck.getCards();
    @Override
    protected List<Player> createPlayers() {
        List<Player> players = new ArrayList<>();
        System.out.println("Please enter the number of players:");
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        try {
            number = scanner.nextInt();
            while (number < 2 || number > 10) {
                System.out.println("The game is typically played by 2-10 players.");
                number = scanner.nextInt();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        }
        scanner.nextLine();
        for (int i = 0; i < number; i++) {
            System.out.println("Please enter the name of player " + (i + 1) + ":");
            String name = scanner.nextLine();
            players.add(new Player(name));
        }
        System.out.println("Player order:");
        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". " + players.get(i).getName());
        }
        return players;
    }
    @Override
    protected void initializeGame(List<Player> players) {
        for (Player player : players) {
            List<UnoCard> hand = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                int randomIndex = (int) (Math.random() * unoCards.size());
                UnoCard card = unoCards.remove(randomIndex);
                hand.add(card);
            }
            player.setHand(hand);
        }
        System.out.println("---------------------------");
        System.out.println("Starting Uno game!");
        System.out.println("---------------------------");
    }
    @Override
    protected void processTurn(Player player) {
        System.out.println("------------------------------");
        System.out.println("It's " + player.getName() + "'s turn.");
        if (lastCard != null) {
            System.out.println("last card: " + lastCard.getType() + " - " + lastCard.getColor());
        }
        System.out.println();
        System.out.println("Your cards are: ");
        for (int i = 0; i < player.getHand().size(); i++)
            System.out.println((i + 1) + ": " + player.getHand().get(i).getType() + " - " + player.getHand().get(i).getColor());
        UnoCard card = null;
        if (color == CardColor.valueOf("NONE")) {
            card = PlayerInputHandler.getPlayerMove(player);
        } else {
            List<UnoCard> hand = player.getHand();
            List<UnoCard> validMoves = CardHandler.getValidMoves(hand, lastCard, color);

            if (validMoves.isEmpty()) {
                card = CardHandler.handleNoValidMoves(player, color, lastCard, unoCards);
            } else {
                CardHandler.displayAvailableMoves(validMoves);
                card = PlayerInputHandler.getPlayerMove(player, validMoves);
            }
        }
        if (CardHandler.checkIfNumbers(card)) {
            processNumberCard(card, player);
        } else if (CardHandler.checkIfAction(card)) {
            processActionCard(card, player);
        } else if (CardHandler.checkIfWild(card)) {
            processWildCard(card, player);
        } else {
            System.out.println("Invalid move. Try again.");
            processTurn(player);
        }
    }
    @Override
    protected void processNumberCard(UnoCard card, Player player) {
        lastCard = card;
        color = card.getColor();
        player.removeCardfromHand(card);
        if (isGameEnded()) {
            endGame();
        } else processTurn(PlayerInputHandler.getNextPlayer(player, getPlayers()));
    }
    @Override
    protected void processActionCard(UnoCard card, Player player) {
        lastCard = card;
        color = card.getColor();
        player.removeCardfromHand(card);

        Player p = CardFactory.actionCards(player, getPlayers(), card.getType(), unoCards);
        if (isGameEnded()) {
            endGame();
        } else processTurn(p);
    }
    @Override
    protected void processWildCard(UnoCard card, Player player) {
        lastCard = card;
        Scanner scanner = new Scanner(System.in);
        CardType cardType = card.getType();
        Player nextPlayer = PlayerInputHandler.getNextPlayer(player, getPlayers());
        if (cardType == CardType.WILD_DRAW_FOUR) {
            System.out.println(nextPlayer.getName() + " must draw four cards!");
            for (int i = 0; i < 4; i++) {
                UnoCard drawnCard = CardHandler.drawCardFromDeck(unoCards);
                nextPlayer.addCardToHand(drawnCard);
            }
            System.out.println(nextPlayer.getName() + "'s turn is skipped!");
        }
        System.out.println("Wild Card! Choose a color (Red, Blue, Green, Yellow):");
        String chosenColor = scanner.nextLine().toLowerCase();
        while (!chosenColor.matches("red|blue|green|yellow")) {
            System.out.println("Invalid color. Choose a color (Red, Blue, Green, Yellow):");
            chosenColor = scanner.nextLine().toLowerCase();
        }
        color = CardColor.valueOf(chosenColor.toUpperCase());
        player.removeCardfromHand(card);
        if (isGameEnded()) {
            endGame();
        } else {
            if (cardType == CardType.WILD_DRAW_FOUR) {
                processTurn(PlayerInputHandler.getNextPlayer(nextPlayer, getPlayers()));
            } else {
                processTurn(PlayerInputHandler.getNextPlayer(player, getPlayers()));
            }
        }
    }
    @Override
    protected void endGame() {
        for (int i = 0; i < getPlayers().size(); i++) {
            if (getPlayers().get(i).getHand().size() == 0) {
                getPlayers().get(i).setScore(getPlayers().get(i).getScore() + 100);
            }
        }
        for (int i = 0; i < getPlayers().size(); i++) {
            System.out.println(getPlayers().get(i).getName() + "'s score is :" + getPlayers().get(i).getScore());
            if (getPlayers().get(i).getScore() == 500) {
                System.out.println(getPlayers().get(i).getName().toUpperCase() + " is THE WINNER !! ");
                System.exit(0);
            }

        }
        System.out.println("The round finished! ");
        System.out.println("------------------------");
        for (Player player : getPlayers()) {
            player.getHand().clear();
        }
        System.out.println("Starting a new round!");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        lastCard = null;
        color = CardColor.valueOf("NONE");
        UnoDeck unoDeck = new UnoDeck();
        unoCards = unoDeck.getCards();
        initializeGame(getPlayers());
        processTurn(getPlayers().get(0));
    }
    @Override
    protected boolean isGameEnded() {
        for (Player p : getPlayers()) {
            if (p.getHand().size() == 0) return true;
        }
        return false;
    }
}