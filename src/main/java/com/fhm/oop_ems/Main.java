package com.fhm.oop_ems;

import p1.Admin;
import p1.Database;
import p1.Day;
import p3.User;

import java.io.*;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    private static String SERVER_URL = "https://XXXX/send";
    private static String GET_URL = "https://XXXX/get";

    private static String username = "Fris233";
    private static String friend = "AhmedHesham102";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        Day.Init();
        Database.Connect();

        try {
            ResultSet rs = Database.GetAny("SELECT url FROM hosturl LIMIT 1");
            if(rs.next()) {
                String url = rs.getString("url");
                SERVER_URL = String.format("%s/send", url);
                GET_URL = String.format("%s/get?id=%s", url, friend);
            }
            else {
                System.out.println("No active Server!");
                System.exit(1);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        // Thread to read messages from server
        new Thread(() -> {
            while (true) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(GET_URL).openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if(line.startsWith(friend))
                            System.out.println(line);
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("Error fetching messages.");
                }
            }
        }).start();

        // Main thread: send messages
        while (true) {
            String message = String.format("%s:%s", username, scanner.nextLine());

            HttpURLConnection connection = (HttpURLConnection) new URL(SERVER_URL).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(message.getBytes());
            }

            connection.getInputStream().close(); // trigger request
        }
    }
}