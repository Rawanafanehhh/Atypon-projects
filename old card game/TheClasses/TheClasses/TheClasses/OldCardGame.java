import java.util.Scanner;

public class OldCardGame {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("WELCOME TO THIS CARD GAME!");
        System.out.println("how many palyers will play:");
        Scanner sc = new Scanner(System.in);
        int numPlayers = sc.nextInt();
        while (numPlayers<2){
            System.out.println("the game must have at least 2 players!");
            System.out.println("please enter the number again:");
            numPlayers = sc.nextInt();
        }
        Game game = new Game(numPlayers);
        game.startGame();
    }
}
