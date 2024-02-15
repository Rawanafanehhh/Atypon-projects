import java.io.*;
import java.net.*;


public class Server {
    public static void main(String[] args) {
        int port = 8000;

        try {

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            String role = in.readUTF();
            String username = in.readUTF();
            String password = in.readUTF();

            boolean isUser = Database.isUser(username.toLowerCase(), password, role.toLowerCase());

            out.writeBoolean(isUser);

            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
