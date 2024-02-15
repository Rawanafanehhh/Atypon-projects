import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardHandler {
    public static UnoCard drawCardFromDeck(List<UnoCard> unoCards) {

        int randomIndex = (int) (Math.random() * unoCards.size());
        System.out.println("Draw another card...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        UnoCard card = unoCards.remove(randomIndex);
        System.out.println("the cards number is: "+ unoCards.size());
        return card;
    }

    public static List<UnoCard> getValidMoves(List<UnoCard> hand,UnoCard lastCard, CardColor color) {
        List<UnoCard> validMoves = new ArrayList<>();
        for (UnoCard c : hand) {
            if (isValidMove(c,color,lastCard)) {
                validMoves.add(c);
            }
        }
        return validMoves;
    }

    public static boolean isValidMove(UnoCard card, CardColor color, UnoCard lastCard) {
        if (checkIfNumbers(card) || checkIfAction(card)) {
            if (color == CardColor.NONE) {
                if (card.getColor() == CardColor.BLUE || card.getColor() == CardColor.GREEN ||
                        card.getColor() == CardColor.RED || card.getColor() == CardColor.YELLOW ||
                        card.getNumber() == lastCard.getNumber()) {
                    return true;
                }
            } else {
                if (color.equals(card.getColor()) || card.getType() == lastCard.getType()) {
                    return true;
                }
            }
        } else if (checkIfWild(card)) {
            return true;
        }
        return false;
    }

    public static boolean checkIfNumbers(UnoCard card) {
        return card.getNumber() != -1;
    }

    public static boolean checkIfAction(UnoCard card) {
        CardType cardType = card.getType();
        return cardType == CardType.SKIP || cardType == CardType.REVERSE || cardType == CardType.DRAW_TWO;
    }

    public static boolean checkIfWild(UnoCard card) {
        CardType cardType = card.getType();
        return cardType == CardType.WILD_DRAW_FOUR || cardType == CardType.WILD;
    }
    public static void displayAvailableMoves(List<UnoCard> validMoves) {
        System.out.println();
        System.out.println("Available moves: ");
        for (int i = 0; i < validMoves.size(); i++) {
            System.out.println((i + 1) + ". " + validMoves.get(i).getType() + " " + validMoves.get(i).getColor());
        }
    }

    public static UnoCard handleNoValidMoves(Player player , CardColor color ,UnoCard lastCard,List<UnoCard> unoCards) {
        System.out.println();
        System.out.println("No valid moves. Drawing a card...");
        UnoCard drawnCard = drawCardFromDeck(unoCards);
        player.addCardToHand(drawnCard);
        System.out.println("The card is: " + drawnCard.getType() + " - " + drawnCard.getColor());

        while (!isValidMove(drawnCard , color ,lastCard)) {
            drawnCard = drawCardFromDeck(unoCards);
            player.addCardToHand(drawnCard);
            System.out.println("The card is: " + drawnCard.getType() + " - " + drawnCard.getColor());
        }
        return drawnCard;

    }

}