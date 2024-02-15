package co;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static  String SECRET_KEY  ;
    private final ExecutorService threadPool;
    private Map<String , Node> clusterNodes;


    private List<Node> nodes = new ArrayList<>();
    private LoadBalancer loadBalancer = new LoadBalancer(); ;
    private ServerSocket serverSocket;


    private int PORT_NUMBER;


    private String nodeWithWriteAffinity;

    public Server() throws IOException {
        this.threadPool = Executors.newCachedThreadPool();

        Map<String , String> env = System.getenv();

        clusterNodes = new HashMap<>();

        PORT_NUMBER = Integer.parseInt(env.get("PORT_NUMBER"));
        SECRET_KEY = env.get("SECRET_KEY");
        prepareNodesInfo();
    }




    public void startServer() throws IOException {
        try {
            bootstrapNodes();
        }
        catch (Exception e){
            startServer();
        }
        
        while (true) {

            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("The server has started listening on port " + PORT_NUMBER);
            System.out.println("Waiting for clients...");
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new ClientHandler(socket,this));
            }
        }
    }

    public void prepareNodesInfo() {

        Map<String , String> env = System.getenv();

        int numberOfNodes = Integer.parseInt(env.get("NUMBER_OF_NODES"));
        for (int i = 1 ; i<= numberOfNodes;i++){
            String nodeInfo = env.get("NODE_"+i);
            String nodeAddress= nodeInfo.split("/")[0];
            int nodePort = Integer.parseInt(nodeInfo.split("/")[1]);
            clusterNodes.put(String.valueOf(i) , new Node(nodeAddress , i , nodePort));
        }
        this.nodeWithWriteAffinity =env.get("WRITE_AFFINITY_NODE_NUMBER");

        clusterNodes.get(nodeWithWriteAffinity).setWriteAffinity(true);;
    }

    private void bootstrapNodes() throws IOException {
        for (String s : clusterNodes.keySet()) {
            Node node = clusterNodes.get(s);
            String token;

            if (node.hasWriteAffinity())
                token = writeNodeToken(node);
            else token = readNodeToken(node);

            Socket socket = new Socket(node.getAddress() , node.getPortNumber());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(token);
            out.flush();
            out.close();
            socket.close();

        }
        return;
    }

    private String writeNodeToken(Node node){
        Map<String,Node> otherNodesInfo = new HashMap<>();
        for (int j = 1 ; j<= clusterNodes.size() ; j++){
            if (j== node.getNumber())
                continue;
            Node otherNode = clusterNodes.get(String.valueOf(j));
            otherNodesInfo.put(String.valueOf(otherNode.getNumber()) , otherNode);
        }

        Map<String , Object> claims = new HashMap<>();
        Gson gson = new Gson();

        String jsonMap = gson.toJson(otherNodesInfo);

        claims.put("SENDER" , "BOOTSTRAP_NODE");
        claims.put("NODE_NUMBER" , String.valueOf(node.getNumber()));
        claims.put("WRITE_AFFINITY" , "TRUE");
        claims.put("OTHER_NODES_INFO" , jsonMap);


        String token = new JController().createJWT(claims , SECRET_KEY , 120000);
        return token;
    }

    private String readNodeToken(Node node) {
        Gson gson = new Gson();
        Map<String , Object> claims = new HashMap<>();
        Node writerNode = clusterNodes.get(nodeWithWriteAffinity);
        claims.put("WRITER_NODE" ,gson.toJson(writerNode));
        claims.put("SENDER" , "BOOTSTRAP_NODE");
        claims.put("WRITE_AFFINITY" , "FALSE");
        claims.put("NODE_NUMBER" , node.getNumber());

        String token = new JController().createJWT(claims , SECRET_KEY , 120000);
        return token;
    }

    public Node assignNodeToClient() {
        System.out.println("assigning node to client ");
         nodes = new ArrayList<>(clusterNodes.values());
        loadBalancer.setNodes(nodes);
        return loadBalancer.getNextNode();
    }

    public int getPORT_NUMBER() {
        return PORT_NUMBER;
    }

    public String getNodeWithWriteAffinity() {
        return nodeWithWriteAffinity;
    }

    public static void main(String[] args) throws IOException {
        Server bootstrapNode = new Server();
        bootstrapNode.startServer();
    }
    public String getSECRET_KEY() {
        return SECRET_KEY;
    }


}
