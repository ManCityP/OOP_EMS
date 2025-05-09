package com.fhm.oop_ems;

import com.sun.net.httpserver.*;
import p1.Database;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.sql.ResultSet;
import java.util.*;

public abstract class Server {
    private static final List<String> messages = Collections.synchronizedList(new ArrayList<>());

    public static String GetTunnelURL() {
        try {
            URL url = new URL("http://localhost:4040/api/tunnels");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Look for the public URL in the JSON response
            String json = response.toString();
            int start = json.indexOf("public_url\":\"") + 13;
            int end = json.indexOf("\"", start);
            String publicUrl = json.substring(start, end);

            if(!publicUrl.startsWith("http"))
                return null;

            System.out.println("Ngrok Public URL: " + publicUrl);
            return publicUrl;

        } catch (Exception e) {
            //System.out.println("Could not fetch ngrok URL: " + e.getMessage());
            return null;
        }
    }

    private static final int PORT = 7878;
    private static Process ngrokProcess;

    public static void main(String[] args) throws IOException {
        Database.Connect();

        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/k", "start", "tunnel.bat");
            builder.redirectErrorStream(true);
            ngrokProcess = builder.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if(ngrokProcess.isAlive())
                    ngrokProcess.destroy();
            }));
            Thread.sleep(100);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/send", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String message = new BufferedReader(new InputStreamReader(is)).readLine();
                messages.add(message);
                String response = "Message received";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        server.createContext("/get", exchange -> {

            String query = exchange.getRequestURI().getQuery();
            String userId = null;

            if (query != null && query.startsWith("id=")) {
                userId = query.substring(3);
            }

            StringBuilder response = new StringBuilder();
            synchronized (messages) {
                Iterator<String> it = messages.iterator();
                while (it.hasNext()) {
                    String msg = it.next();
                    if(msg.startsWith(userId)) {
                        response.append(msg).append("\n");
                        it.remove();
                    }
                }
            }
            byte[] resp = response.toString().getBytes();
            exchange.sendResponseHeaders(200, resp.length);
            exchange.getResponseBody().write(resp);
            exchange.getResponseBody().close();
        });

        server.setExecutor(null); // default
        server.start();

        String tunnelURL = null;
        try {
            for (int i = 0; i < 100; i++) {
                tunnelURL = GetTunnelURL();
                if (tunnelURL != null)
                    break;
                Thread.sleep(200);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            ResultSet resultSet = Database.GetAny("SELECT url FROM hosturl LIMIT 1");
            if(resultSet.next()) {
                System.out.println("There is already an active server!");
                System.exit(0);
            }
            Database.Execute(String.format("INSERT INTO hosturl (url) VALUES ('%s')", tunnelURL));
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Database.Execute("TRUNCATE hosturl");
                Database.CloseConnection();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }));

        System.out.println("HTTP Chat Server running on port " + PORT);
    }
}