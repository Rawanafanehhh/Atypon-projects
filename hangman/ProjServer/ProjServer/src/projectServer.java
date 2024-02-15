
import java.io.*;
import java.net.*;
import java.util.*;

public class projectServer extends Thread {

    Socket client1;
    Socket client2;

    public projectServer(Socket client1, Socket client2) {
        this.client1 = client1;
        this.client2 = client2;
    }

    public void run() {
        ArrayList list;
        ArrayList port = new ArrayList(2);

        try {
            FileInputStream fin = new FileInputStream("hangman.out");
            ObjectInputStream oin = new ObjectInputStream(fin);

            Object obj = oin.readObject();
            list = (ArrayList) obj;
            // we open the file and we store it in the list arraylist 
            Random r = new Random();

            int index = r.nextInt(list.size());
            // index is to pick a random index from the list in the file 
            String TheWord = String.valueOf(list.get(index));
            // the word string is the word that we pick it randomly 
            String[] arr = TheWord.split("-", 2);
            // we split it to two index 
            String letters = arr[1].replaceAll(" ", "");
            // we delete the spaces from the word 
            String[] sletters = new String[letters.length()];
            for (int i = 0; i < letters.length(); i++) {
                sletters[i] = Character.toString(letters.charAt(i));

            }
            // we put the letters without the spaces in the string array (sletters)

            String h = arr[1];

            for (int j = 0; j < sletters.length; j++) {
                h = h.replace(sletters[j], "-");
            }
            // then we replace the letters with (-) 

            BufferedReader o1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
            OutputStream out = client1.getOutputStream();
            PrintWriter print = new PrintWriter(out, true);
            // this is the output and the input for the first client   

            String name1 = o1.readLine();
            // we will store the name of the first client in name1

            port.add(client1.getPort());
            // we add the port number to the port arraylist for make sure that if we have one client we will send to him( wait the second client )

            if (port.size() == 1) {
                print.println("Waiting for another player to connect");
                out.flush();
                //here we will send wait the second client to the first client 
            }
            port.add(client2.getPort());
            if (port.size() == 2) {
                // when the port size will be ==2 then we have two client and when we will have two client the game will start 

                BufferedReader o2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
                OutputStream out2 = client2.getOutputStream();
                PrintWriter print2 = new PrintWriter(out2, true);
                // this is the output and the input for the second client 

                String name2 = o2.readLine();
                // we will store the name of the second client in name2

                print.println("“Game has started" + "\n" + "Genre: " + arr[0] + "\n" + " " + h + "\n" + "Incorrect letters:");
                print2.println("“Game has started" + "\n" + "Genre: " + arr[0] + "\n" + " " + h + "\n" + "Incorrect letters:");
                out2.flush();
                out.flush();
                // we send this to each client to let them know that the game has started

                Random random = new Random();
                String RStart = random.nextBoolean() ? "1" : "2";
                ArrayList iL = new ArrayList();
                // to make the start be randomly so we do this( if the RStart==1 the first client will start, and if RStart ==2 so the second client will start )

                for (;;) {

                    if (RStart.equals("1")) {
                        // here if RStart == 1 so it is firt client turn 

                        ArrayList indexP = new ArrayList();

                        print2.println(name1 + " Turn! ");
                        print.println("it is your turn! " + "\n" + "Enter a letter OR make a guess: ");
                        out.flush();
                        out2.flush();
                        String let = o1.readLine();
                        // String let , this where we take the letter or the guess 
                        char[] h2 = h.toCharArray();
                        if (let.length() == 1) {
                            // if let length == 1 then it is a letter not a guess so we have to replace it or we will put it in the incorrect letters
                            for (int i = 0; i < arr[1].length(); i++) {
                                if (let.equalsIgnoreCase(Character.toString(arr[1].charAt(i)))) {
                                    //here we will check all the index of the letters that it is equel to the letter of the client 
                                    indexP.add(i);
                                    // and we put the index that the letter equel to the client letter in indexP
                                    h2[i] = let.charAt(0);
                                    //and we put the letter with the same index in h2 char array that its contan a (-) where the characters are
                                }

                            }
                            h = String.valueOf(h2);
                            // and then we return it to the h String with the letters replace

                            print.println("Genre: " + arr[0]);
                            print.println(h);
                            print.print("Incorrect letters:");

                            print2.println("Genre: " + arr[0]);
                            print2.println(h);
                            print2.print("Incorrect letters: ");

                            out.flush();
                            out2.flush();
                            // then we will send to the two clients 
                            if (indexP.isEmpty()) {
                                // if the indexP is empty then the letter is incorrect so we will put it in the Incorrect letters

                                iL.add(let);
                                for (int i = 0; i < iL.size(); i++) {
                                    print.print(iL.get(i).toString() + " , ");
                                    print2.print(iL.get(i).toString() + " , ");
                                    // here we will print all the incorrect letters which are stored in iL arraylist
                                }

                            }
                            print.println("");
                            print2.println("");
                            if (h.equalsIgnoreCase(arr[1])) {
                                // here if the client send the last letter and the word is completed so send to the first client that you win the game
                                print.println("The word is: " + arr[1]);
                                print2.println("The word is: " + arr[1]);
                                print.println("YOU WIN! ");
                                print2.println(name1 + " WIN!");
                                out.flush();
                                out2.flush();
                                o1.close();
                                out.close();
                                print.close();
                                o2.close();
                                out2.close();
                                print2.close();
                                client1.close();
                                client2.close();

                                return;
                            }

                            print.println("Do you want to make a guess? ");
                            // if the word still not completed and the client enter a letter so he can take a chance to make a guess 
                            out.flush();
                            out2.flush();

                            String yn1 = o1.readLine();
                            String s = "yes";
                            // in yn1 here where  if the client enter yes or no for making a guess

                            if (yn1.equalsIgnoreCase(s)) {
                                // if yn1 == yes so we will ask him what is your guess
                                print.println("What is your guess? ");
                                out.flush();
                                String ge1 = o1.readLine();
                                out.flush();
                                out2.flush();
                                if (ge1.equalsIgnoreCase(arr[1])) {
                                    // If the guess is correct, we will send to the both clients  that the first player has won
                                    print.println("The word is: " + arr[1]);
                                    print2.println("The word is: " + arr[1]);
                                    print.println("YOU WIN! ");
                                    print2.println(name1 + " WIN!");
                                    out.flush();
                                    out2.flush();

                                    o1.close();
                                    out.close();
                                    print.close();
                                    o2.close();
                                    out2.close();
                                    print2.close();
                                    client1.close();
                                    client2.close();
                                    return;
                                } else {
                                    // if the guess is wrong so we will go to the second client and it will be his turn 
                                    RStart = "2";

                                }
                            } else {
                                // if the answer is no so we will go to the second client and it will be his turn 
                                RStart = "2";
                            }
                        }

                        if (let.length() > 1) {
                            // if the let length >1 so it is a guess not a letter 
                            if (let.equalsIgnoreCase(arr[1])) {
                                // If the guess is correct, we will send to the both clients  that the first player has won

                                print.println("The word is: " + arr[1]);
                                print2.println("The word is: " + arr[1]);
                                print.println("YOU WIN! ");
                                print2.println(name1 + " WIN!");
                                out.flush();
                                out2.flush();
                                o1.close();
                                out.close();
                                print.close();
                                o2.close();
                                out2.close();
                                print2.close();
                                client1.close();
                                client2.close();
                                return;

                            } else {
                                // if the guess is wrong so we will go to the second client and it will be his turn 

                                RStart = "2";
                            }

                        }

                    }

                    if (RStart.equals("2")) {
                        // here if RStart == 2 so it is second client turn 

                        ArrayList indexP = new ArrayList();
                        print.println(name2 + " Turn! ");
                        // here we send to the first client that its  second client turn 

                        print2.println("it is your turn! ");

                        print2.println("Enter a letter OR make a guess:");
                        out.flush();
                        out2.flush();
                        String let = (String) o2.readLine();
                        // String let , this where we take the letter or the guess 

                        char[] h2 = h.toCharArray();

                        if (let.length() == 1) {
                            // if let length == 1 then it is a letter not a guess so we have to replace it or we will put it in the incorrect letters

                            for (int i = 0; i < arr[1].length(); i++) {
                                if (let.equalsIgnoreCase(Character.toString(arr[1].charAt(i)))) {
                                    //here we will check all the index of the letters that it is equel to the letter of the client 

                                    indexP.add(i);
                                    // and we put the index that the letter equel to the client letter in indexP
                                    h2[i] = let.charAt(0);
                                    //and we put the letter with the same index in h2 char array that its contan a (-) where the characters are
                                }

                            }
                            h = String.valueOf(h2);
                            // and then we return it to the h String with the letters replace

                            print2.println("Genre: " + arr[0]);
                            print2.println(h);
                            print2.print("Incorrect letters:");
                            print.println("Genre: " + arr[0]);
                            print.println(h);
                            print.print("Incorrect letters:");
                            out2.flush();
                            out.flush();
                            // then we will send to the two clients 

                            if (indexP.isEmpty()) {
                                // if the indexP is empty then the letter is incorrect so we will put it in the Incorrect letters
                                iL.add(let);
                                for (int i = 0; i < iL.size(); i++) {
                                    print.print(iL.get(i).toString() + " , ");
                                    print2.print(iL.get(i).toString() + " , ");
                                    // here we will print all the incorrect letters which are stored in iL arraylist

                                }

                            }
                            print.println("");
                            print2.println("");
                            if (h.equalsIgnoreCase(arr[1])) {
                                // here if the client send the last letter and the word is completed so send to the second client that you win the game

                                print.println("The word is: " + arr[1]);
                                print2.println("The word is: " + arr[1]);
                                print.println("YOU WIN! ");
                                print2.println(name1 + " WIN!");
                                out.flush();
                                out2.flush();
                                o1.close();
                                out.close();
                                print.close();
                                o2.close();
                                out2.close();
                                print2.close();
                                client1.close();
                                client2.close();
                                return;
                            }

                            print2.println("Do you want to make a guess? ");
                            // if the word still not completed and the client enter a letter so he can take a chance to make a guess 

                            String yn2 = o2.readLine();
                            String s = "yes";
                            out2.flush();
                            out.flush();
                            // in yn2 here where  if the client enter yes or no for making a guess

                            if (yn2.equalsIgnoreCase(s)) {
                                // if yn1 == yes so we will ask him what is your guess

                                print2.println("What is your guess? ");
                                String ge = o2.readLine();
                                out2.flush();
                                out.flush();
                                if (ge.equalsIgnoreCase(arr[1])) {
                                    // If the guess is correct, we will send to the both clients  that the second player has won

                                    print2.println("The word is: " + arr[1]);
                                    print.println("The word is: " + arr[1]);
                                    print2.println("YOU WIN! ");
                                    print.println(name1 + " WIN!");
                                    out2.flush();
                                    out.flush();
                                    o1.close();
                                    out.close();
                                    print.close();
                                    o2.close();
                                    out2.close();
                                    print2.close();
                                    client1.close();
                                    client2.close();
                                   return;
                                } else {
                                    RStart = "1";
                                    // if the guess is wrong so we will go to firt client and it will be his turn 

                                }

                            } else {
                                RStart = "1";
                                //  if the answer is no so we will go to firt client and it will be his turn 
                            }
                        }

                        if (let.length() > 1) {
                            // if the let length >1 so it is a guess not a letter 

                            if (let.equalsIgnoreCase(arr[1])) {
                                // If the guess is correct, we will send to the both clients  that the first player has won

                                print2.println("The word is: " + arr[1]);
                                print.println("The word is: " + arr[1]);
                                print2.println("YOU WIN! ");
                                print.println(name1 + " WIN!");
                                out2.flush();
                                out.flush();
                                o1.close();
                                out.close();
                                print.close();
                                o2.close();
                                out2.close();
                                print2.close();
                                client1.close();
                                client2.close();
                                return;
                            } else {
                                RStart = "1";
                                // if the guess is wrong so we will go to firt client and it will be his turn  

                            }
                        }

                    }
                }
            }
        } catch (ClassCastException | ClassNotFoundException cce) {
            System.err.println(cce);

        } catch (IOException ioe) {
            System.err.println(ioe);

        }

    }

    public static void main(String[] args) {
        try {

            ServerSocket server = new ServerSocket(2000);

            for (;;) {
                Socket client1 = server.accept();

                Socket client2 = server.accept();
                projectServer c = new projectServer(client1, client2);
                c.start();
                
                // we create a serverSocket and we do an infinite loop for accept clients as many as we want
                //and then we accept the clients and we will send every two client in one thread and we start the thread
            }
        } catch (IOException ioe) {
            System.err.println(ioe);

        }
    }
}
