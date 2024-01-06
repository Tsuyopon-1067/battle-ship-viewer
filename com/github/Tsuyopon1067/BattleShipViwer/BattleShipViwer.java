package com.github.Tsuyopon1067.BattleShipViwer;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class BattleShipViwer implements Runnable {
    private boolean hasChanged = true;
    String jsonString = "";

    @Override
    public void run() {
        System.err.println("Server starts.");
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(50000), 0);
            server.createContext("/getjson", new GetJsonHandler());
            server.setExecutor(null);
            server.start();
            System.err.println("Server is running.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerBoard(BattleShipBoard board) {
        jsonString = board.toJSonString();
        hasChanged = true;
    }

    class GetJsonHandler implements HttpHandler {
        final String CLIENT_URL = "http://localhost:3000";

        @Override
        public void handle(HttpExchange t) throws IOException {
            while (!hasChanged) {
                try {
                    Thread.sleep(13);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String response = jsonString;
            t.getResponseHeaders().set("Content-Type", "application/json");
            t.getResponseHeaders().set("Access-Control-Allow-Origin", CLIENT_URL);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}