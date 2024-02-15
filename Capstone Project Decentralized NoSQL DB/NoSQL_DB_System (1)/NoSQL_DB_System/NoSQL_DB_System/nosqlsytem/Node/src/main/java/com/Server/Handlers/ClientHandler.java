package com.Server.Handlers;

import com.Action.ReadDocPropertyAction;
import com.Control.JWTController;
import com.Queries.Queries.DocUpdateQuery;
import com.Queries.Queries.QueriesFactory;
import com.Queries.Queries.QueriesTypes;
import com.Queries.QueriesManegers.QueryExecutor;
import com.Queries.QueriesManegers.QueryFetcher;
import com.Queries.QueriesManegers.Validators.syntax.QueryValidator;
import com.Queries.QueriesManegers.Validators.syntax.ValidatorsFactory;
import com.Server.BroadCaster;
import com.Server.Dispatcher;
import com.Server.Server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements RequestHandler {

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private JWTController jwtManager;
    private Server server;

    public ClientHandler(Server server, Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.jwtManager = new JWTController();
        this.server = server;
    }


    public void handle() throws IOException {

        while (true) {
            try {

                String query = in.readUTF();
                QueriesTypes type = new QueryFetcher().fetch(query);
                QueryValidator validator = new ValidatorsFactory().getInstance(type, query);


                if (!validator.isValidQuery()) {
                    sendMessageToClient("Invalid query!");
                    continue;
                }


                if (type == QueriesTypes.READ_DOCUMENT || server.hasWriteAffinity()) {
                    QueryExecutor executor = new QueryExecutor();
                    String message = executor.execute(new QueriesFactory().getInstance(type, query));
                    sendMessageToClient(message);
                    if (server.hasWriteAffinity() && type != QueriesTypes.READ_DOCUMENT) {
                        BroadCaster broadCaster = new BroadCaster(server.getOtherNodes());
                        broadCaster.broadCast(broadCastRequestTokenGenerator(query, server.getSECRET_KEY()));
                    }
                    continue;
                }
                else {
                    Dispatcher dispatcher = new Dispatcher(server.getWriterNode().getAddress(), server.getWriterNode().getPortNumber());
                    Map<String, Object> claims = new HashMap<>();
                    claims.put("SENDER", "READER_NODE");
                    claims.put("QUERY", query);
                    claims.put("QUERY_TYPE", "WRITE_DOCUMENT");
                    String token = jwtManager.createJWT(claims, server.getSECRET_KEY(), 60000);
                    String result = dispatcher.forward(token);
                    sendMessageToClient(result);
                    continue;
                }



            } catch (SocketException e) {
                socket.close();
                continue;
            } catch (IOException e) {
                sendMessageToClient(e.getMessage());
                continue;

            } catch (Exception e) {
                sendMessageToClient(e.getMessage());
                continue;
            }
        }
    }

    private String broadCastRequestTokenGenerator(String query, String secretKey) {
        Map<String, Object> map = new HashMap<>();
        map.put("QUERY", query);
        map.put("SENDER", "WRITER_NODE");
        JWTController controller = new JWTController();
        return controller.createJWT(map, secretKey, 60000);
    }

    private void sendMessageToClient(String message) {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (Exception e) {
        }
    }
}