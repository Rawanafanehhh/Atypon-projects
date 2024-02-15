package main.java;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static String token;
    private static String response;
    private static boolean skipLogin = false;
    private static int port=0;
    private static String address=null;
    private static Scanner scanner = new Scanner(System.in);

    public static boolean isSkipLogin() {
        return skipLogin;
    }

    public static void setSkipLogin(boolean sskipLogin) {
        skipLogin = sskipLogin;
    }

    public static void enterNameAndPassword() throws IOException {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter name:");
            String name = scanner.nextLine();
            System.out.println("Enter password");
            String password = scanner.nextLine();
            Socket socket = new Socket("localhost", 4001);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(name);
            out.flush();
            out.writeUTF(password);
            out.flush();

            response = in.readUTF();

            if (response.equals("Invalid password")) {
                System.out.println("_____________________________________________________________________");
                System.out.println("\u001b[1;31mInvalid password or the username is already token. Please try again.\u001b[0m");
                System.out.println("_____________________________________________________________________");

                socket.close();


            } else {
                // Valid username and password, proceed
                System.out.println("Node address:");
                System.out.println(response);
                System.out.println("Token");
                token   =in.readUTF();
                System.out.println(token);
                System.out.println("-----------------------------------------------------------------------------");
                break; // Exit the loop
        }
    }}
    public static void enterToken(){


            try {

                Socket socket = new Socket(address, port);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("Enter a query");
                // ANSI escape code for gray color
                String grayColor = "\u001B[90m";
                // ANSI escape code to reset text color
                String resetColor = "\u001B[0m";

                System.out.println(grayColor + "\" for create database: create database <database name> <Attributes> \"" + resetColor);
                System.out.println(grayColor + "\" for delete database: delete database <database name>  \"" + resetColor);
                System.out.println(grayColor + "\" for create document: create document in <database name> <Attributes=values> \"" + resetColor);
                System.out.println(grayColor + "\" for delete document: delete document from <database name> <id value> \"" + resetColor);
                System.out.println(grayColor + "\" for read document: read document from <database name> <id value> \"" + resetColor);
                System.out.println(grayColor+"\" for read specific property value: read property <property> on <database name> from <document id>\"" + resetColor);
                System.out.println(grayColor+"\" for read using index : read from <database name> where <indexingName>=<value>\"" + resetColor);
                System.out.println(grayColor + "\" for update specific property : update document from <database name> <id value> <Attributes=the new values> \"" + resetColor);
                System.out.println(grayColor + "\" for create index : create index in <database name> <indexingName> \"" + resetColor);
                String query = scanner.nextLine();
                out.writeUTF(token);
                out.flush();
                out.writeUTF(query);
                out.flush();
                System.out.println(in.readUTF());
                System.out.println("-----------------------------------------------------------------------------");
                setSkipLogin(true);

            }catch (Exception e){
            }


    }

    public static void main(String[] args) throws IOException {

        while (true) {
            if (!isSkipLogin()) {
                login();
                while (true){
                System.out.println("Please enter the address of the VM you want to assign:");
                String clientAddress= scanner.nextLine();
                System.out.println("Please enter the port number of the VM you want to assign:");
                int clientPort = Integer.parseInt(scanner.nextLine());
                String[] parts = response.split("/");
                address =parts[0];
                port =Integer.parseInt(parts[1]);
                if(address.equals(clientAddress)&& port==clientPort){
                    break;
                }
                else {
                    System.out.println("Rejected, this is not the VM you assigned to");
                }}

            }


            String b = "\u001b[1;20mchoose one of the following:\u001b[0m";
            System.out.println(b);
            System.out.println("1-sending a query");
            System.out.println("2-Exit");
            String choice = new Scanner(System.in).nextLine();
            System.out.println("-----------------------------------------------------------------------------");

            if (choice.equals("1")) {
                enterToken();
            } else if (choice.equals("2")) {
                setSkipLogin(false);
                continue;
            } else {
                System.out.println("Wrong input");
                setSkipLogin(true);
            }
        }
    }

    private static void login() throws IOException {
        System.out.println("-----------------------------------------------------------------------------");
        String boldAndBig = "\u001b[1;35mLogin page\u001b[0m";
        System.out.println(boldAndBig);
        System.out.println("---------------------------------");

        enterNameAndPassword();

    }


}