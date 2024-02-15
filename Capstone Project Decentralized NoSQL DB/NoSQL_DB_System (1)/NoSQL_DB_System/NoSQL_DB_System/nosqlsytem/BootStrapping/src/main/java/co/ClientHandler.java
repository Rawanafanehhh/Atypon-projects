package co;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Map<String, String> usernameArray = new ConcurrentHashMap<>();
    private Map<String, String> clientsInfo = new ConcurrentHashMap<>();
    private Map<String, String> clientsJ = new ConcurrentHashMap<>();
    private final Server server;
    private final Object fileLock = new Object();

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            synchronized (socket) {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                String userName = in.readUTF();
                String password = in.readUTF();
                loadClientData();
                if (isUsernameInArray(userName)) {
                    if (!isValid(userName, password)) {
                        out.writeUTF("Invalid password");
                        out.flush();
                        out.writeUTF("try again!");
                        out.flush();
                        closeConnection();
                        return;
                    } else {
                        System.out.println("Client connected!");
                        alreadyClient(userName);
                    }
                } else {
                    System.out.println("Client connected for the first time!");
                    forTheFirstTime(userName, password);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isUsernameInArray(String userName) {
        return usernameArray.containsKey(userName);
    }

    private void addUserToArray(String userName, String password) {
        synchronized (usernameArray) {
            usernameArray.put(userName, password);
        }
        System.out.println("putting the username and password on the system");
    }

    private String generateToken(Node node) {
        int nodeNumber = node.getNumber();
        Map<String, Object> claims = new HashMap<>();
        claims.put("SENDER", "CLIENT");
        claims.put("NODE_NUMBER", nodeNumber);
        JController controller = new JController();
        return controller.createJWT(claims, server.getSECRET_KEY(), 864000000);
    }

    public void forTheFirstTime(String username, String password) {
        try {
            Node node = server.assignNodeToClient();
            String nodeInfo;
            if (node.getAddress().equals("host.docker.internal")) {
                nodeInfo = "127.0.0.1/" + node.getPortNumber();
            } else {
                nodeInfo = node.getAddress() + "/" + node.getPortNumber();
            }
            String token = generateToken(node);
            saveClientData(username, nodeInfo, token, password);
            out.writeUTF(nodeInfo);
            out.flush();
            out.writeUTF(token);
            out.flush();
            closeConnection();
            addUserToArray(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void alreadyClient(String username) {
        try {
            String nodeInfo = clientsInfo.get(username);
            String token = clientsJ.get(username);
            out.writeUTF(nodeInfo);
            out.flush();
            out.writeUTF(token);
            out.flush();
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isValid(String userName, String password) {
        synchronized (usernameArray) {
            String storedPassword = usernameArray.get(userName);
            return password.equals(storedPassword);
        }
    }

    private void closeConnection() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveClientData(String username, String nodeInfo, String token, String password) {
        System.out.println("saving client data ..");
        Properties prop = new Properties();

        synchronized (fileLock) {
            try (InputStream input = new FileInputStream("/path/in/container/client_data.properties")) {
                prop.load(input);
            } catch (FileNotFoundException e) {
                System.out.println("the file doesn't exist ");
            } catch (IOException e) {
                e.printStackTrace();
            }

            String usernameKey = "username";
            String passwordKey = "password";
            String infoKey = username + "_info";
            String tokenKey = username + "_token";

            String existingUsername = prop.getProperty(usernameKey);
            String existingPassword = prop.getProperty(passwordKey);

            if (existingUsername != null) {
                username = existingUsername + "," + username;
            }
            if (existingPassword != null) {
                password = existingPassword + "," + password;
            }

            prop.setProperty(usernameKey, username);
            prop.setProperty(passwordKey, password);
            prop.setProperty(infoKey, nodeInfo);
            prop.setProperty(tokenKey, token);

            try (OutputStream output = new FileOutputStream("/path/in/container/client_data.properties")) {
                prop.store(output, "save");
            } catch (Exception e) {
                System.err.println("can't save client data.");
            }
        }
    }

    private void loadClientData() {
        System.out.println("loading the information");
        Properties prop = new Properties();

        synchronized (fileLock) {
            try (InputStream input = new FileInputStream("/path/in/container/client_data.properties")) {
                prop.load(input);
            } catch (IOException e) {
                System.err.println("Error loading client data from file.");
                e.printStackTrace();
            }

            String usernamesProperty = prop.getProperty("username");
            String passwordsProperty = prop.getProperty("password");

            if (usernamesProperty != null && passwordsProperty != null) {
                String[] usernamesArray = usernamesProperty.split(",");
                String[] passwordsArray = passwordsProperty.split(",");

                if (usernamesArray.length == passwordsArray.length) {
                    for (int i = 0; i < usernamesArray.length; i++) {
                        String username = usernamesArray[i];
                        String password = passwordsArray[i];
                        usernameArray.put(username, password);
                    }

                    for (String username : usernamesArray) {
                        String infoKey = username + "_info";
                        String tokenKey = username + "_token";
                        String info = prop.getProperty(infoKey);
                        String token = prop.getProperty(tokenKey);
                        clientsInfo.put(username, info);
                        clientsJ.put(username, token);
                    }
                }
            }
        }
    }
}
