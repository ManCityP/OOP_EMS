package com.fhm.oop_ems;

import p1.Database;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 7878;
    private static final List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Database.Connect();

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("com.fhm.oop_ems.Server started. Waiting for clients...");

        try {
            InetAddress localhost = InetAddress.getLocalHost();
            Database.Execute(String.format("INSERT INTO hostip (ip) VALUES ('%s')", localhost.getHostAddress()));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Database.Execute("DELETE * FROM hostip");
                Database.CloseConnection();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }));

        while (true) {
            Socket clientSocket = serverSocket.accept();
            clients.add(clientSocket);
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // Handle each client in its own thread
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String message;
            while ((message = in.readLine()) != null) {
                broadcastMessage(message, clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clients.remove(clientSocket);
            try { clientSocket.close(); } catch (IOException ignored) {}
        }
    }

    private static void broadcastMessage(String message, Socket sender) {
        for (Socket client : clients) {
            if (client != sender) {
                try {
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    out.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}