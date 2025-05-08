package com.fhm.oop_ems;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import p1.Database;
import p3.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;

public class ChatController {

    @FXML
    private ImageView bgImg;
    @FXML
    private Label friendLabel;
    @FXML
    private TextArea messageArea;
    @FXML
    private VBox messagesContainer;
    @FXML
    private ScrollPane scrollPane;

    private User me;
    private User friend;

    private static String SERVER_URL = "https://XXXX/send";
    private static String GET_URL = "https://XXXX/get";

    private static boolean receiving = true;
    private static boolean prevme = true;

    public void Init(User currentUser, User messageUser) {
        this.me = currentUser;
        this.friend = messageUser;
        this.friendLabel.setText(this.friend.GetUsername());

        try {
            ResultSet rs = Database.GetAny("SELECT url FROM hosturl LIMIT 1");
            if(rs.next()) {
                String url = rs.getString("url");
                SERVER_URL = String.format("%s/send", url);
                GET_URL = String.format("%s/get?id=%s", url, friend.GetUsername().replace(" ", ""));
            }
            else {
                System.out.println("No active Server!");
                System.exit(1);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        Thread receiver = new Thread(() -> {
            while (receiving) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(GET_URL).openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if(line.startsWith(friend.GetUsername().replace(" ", "")))
                            MessageReceived(line.replace(friend.GetUsername().replace(" ", "") + ":", ""), false);
                    }
                    Thread.sleep(200);
                } catch (Exception e) {
                    System.out.println("Error fetching messages.");
                }
            }
        });
        receiver.start();

        Stage chatStage = (Stage)messageArea.getScene().getWindow();
        chatStage.setOnCloseRequest(windowEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("Click OK to exit chat!");
            alert.showAndWait().ifPresent(response -> {
                if(response.getText().equals("OK")) {
                    receiving = false;
                    chatStage.close();
                }
                else {
                    windowEvent.consume();
                }
            });
        });
    }

    private void MessageReceived(String text, boolean me) {
        Platform.runLater(() -> {
            Label label = new Label(text);

            label.setWrapText(true);

            if(!me) {
                label.setAlignment(Pos.CENTER_LEFT);
                label.setStyle("-fx-background-color: gray; -fx-font-size: 16px; -fx-text-fill: white;");
            }
            else {
                label.setAlignment(Pos.CENTER_RIGHT);
                label.setStyle("-fx-background-color: blue; -fx-font-size: 16px; -fx-text-fill: white;");
            }

            if(prevme != me) {
                Region space = new Region();
                space.setPrefHeight(10);
                messagesContainer.getChildren().add(space);
            }
            messagesContainer.getChildren().add(label);
            scrollPane.setVvalue(1.0);
            prevme = me;
        });
    }

    private void SendMessage(String text) {
        try {
            //TODO test multiline messages!
            MessageReceived(text, true);
            String message = String.format("%s:%s", me.GetUsername().replace(" ", ""), text);

            HttpURLConnection connection = (HttpURLConnection) new URL(SERVER_URL).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(message.getBytes());
            }

            connection.getInputStream().close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void MessageKeyPress(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER)
            if(event.isShiftDown())
                messageArea.appendText("\n");
            else {
                for(String str : messageArea.getText().split("\n")) {
                    SendMessage(str);
                    messageArea.clear();
                }
            }
    }
}
