
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class projectClient extends Thread {

    Socket socket;
    BufferedReader readerr;

    public projectClient(Socket sClient1) throws IOException {
        this.socket = sClient1;
        this.readerr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        try {
            String line;
            while ((line = readerr.readLine()) != null) {
                System.out.println(line);
                // the run method will read the inputStream for each client while it is not = null

            }
        } catch (IOException ioe) {
            System.err.println(ioe);

        } finally {
            try {
                readerr.close();
                socket.close();

            } catch (Exception e) {
                System.err.println(e);

            }
        }
    }

    public static void main(String[] args) {

        try {
            InetAddress address = InetAddress.getByName("localhost");
            Socket sClient1 = new Socket(address, 2000);
            BufferedReader readerr = new BufferedReader(new InputStreamReader(sClient1.getInputStream()));
            OutputStream out = sClient1.getOutputStream();
            PrintWriter print = new PrintWriter(out, true);

            String enter;
            Scanner sc1 = new Scanner(System.in);
            String yes;
            String g;
            projectClient thread = new projectClient(sClient1);
            thread.start();
            // Here we send each client separated from the other on the thread then we start it  
            // Because the scanner is blocking like the reader, so we separated them

            System.out.println("please enter your name: ");
            print.println(sc1.nextLine());

            // here we read the name from the user and send it to the server 

            for (;;) {
                enter = sc1.nextLine();
                // the here we read the letter or the guess from the user 
                if (enter.length() == 1) {
                    // here if the enter length == 1 then it is a letter 
                    print.println(enter);
                    // we send the letter to the server 
                    yes = sc1.nextLine();
                    // here the user will send if he want to take a chance and make a guess or not
                    print.println(yes);
                    String s = "yes";
                    if (yes.equalsIgnoreCase(s)) {
                        // if the answer was yes so here will enter the user his guess and then send it to the server 
                        g = sc1.nextLine();
                        print.println(g);
                        out.flush();
                    }

                }
                if (enter.length() > 1) {
                    // if the enter >1 then it is a guess so just send it to the server

                    print.println(enter);
                    out.flush();

                }

            }

        } catch (IOException ioe) {
            System.err.println(ioe);

        }

    }
}
