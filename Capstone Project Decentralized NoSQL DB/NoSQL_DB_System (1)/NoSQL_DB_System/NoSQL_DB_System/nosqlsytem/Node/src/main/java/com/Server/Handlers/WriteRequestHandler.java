package com.Server.Handlers;

import com.Action.ReadDocPropertyAction;
import com.Control.JWTController;
import com.Queries.QueriesManegers.QueryExecutor;
import com.Server.BroadCaster;
import com.Server.Server;
import io.jsonwebtoken.Claims;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class WriteRequestHandler implements RequestHandler{
    private Server server;
    private Socket socket;
    private Claims claims;
    private DataInputStream in;
    private DataOutputStream out;
    public WriteRequestHandler(Server server  , Socket socket , Claims claims) throws IOException {
        this.claims = claims;
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out=new DataOutputStream(socket.getOutputStream());
    }

    public void handle() throws IOException {
        try {

            String query = (String)claims.get("QUERY");
            QueryExecutor executor = new QueryExecutor();
            String message  = executor.execute(query);
            sendMessageToClient(message);
            BroadCaster broadCaster = new BroadCaster(server.getOtherNodes());
            String token = generateBroadCastToken(query , server.getSECRET_KEY());
            broadCaster.broadCast(token);
            return;
        }
        catch (IOException ioException){
            return;
        } catch (Exception e) {
            sendMessageToClient(e.getMessage());
            socket.close();
            return;
        }
    }

    public void sendMessageToClient(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
        out.close();
    }

    private String generateBroadCastToken(String query , String secretKey){
        Map<String, Object> map = new HashMap<>();
        map.put("QUERY" , query);
        map.put("SENDER" , "WRITER_NODE");
        JWTController controller = new JWTController();
        return controller.createJWT(map , secretKey , 60000);
    }

}