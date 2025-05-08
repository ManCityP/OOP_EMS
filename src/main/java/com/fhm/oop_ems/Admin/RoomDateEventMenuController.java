package com.fhm.oop_ems.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import p1.Database;
import p1.MyDate;
import p1.Room;
import p2.Event;
import p3.User;

public class RoomDateEventMenuController {

    @FXML
    private Button backButton;
    @FXML
    private Button chatButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Label dateLabel;
    @FXML
    private Button refreshButton;
    @FXML
    private Label roomIDLabel;
    @FXML
    private Label roomLocationLabel;
    @FXML
    private VBox roomsContainer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label username;

    private User currentUser;
    private Room currentRoom;
    private MyDate currentDate;

    public void Init(User user, Room room, MyDate date) {
        this.currentUser = user;
        this.currentRoom = room;
        this.currentDate = date;

        this.username.setText(currentUser.GetUsername());

        this.roomIDLabel.setText(String.format("Room ID: %s", this.currentRoom.GetID()));
        this.roomLocationLabel.setText(String.format("Room Location: %s", this.currentRoom.GetLocation()));
        this.dateLabel.setText(currentDate.toString());

        try {
            for (Event event : Database.GetEvents()) {
                if(event.GetRoomID() == currentRoom.GetID() && event.GetDate().equals(currentDate)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EventPaneTemplate.fxml"));
                    Node roomNode = loader.load();

                    EventPaneTemplateController controller = loader.getController();
                    controller.Init(event);

                    roomsContainer.getChildren().add(roomNode);
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    void BackPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomCalendar.fxml"));
            Parent root = loader.load();

            RoomCalendarController roomCalendarController = loader.getController();
            roomCalendarController.Init(currentUser, this.currentRoom, Database.GetEvents());

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)refreshButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void ButtonHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #5F6368;");
    }

    @FXML
    void ButtonNotHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: transparent;");
    }

    @FXML
    void ChatPressed() {
        System.out.println("Chat Pressed!");
    }

    @FXML
    void DashboardPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminMainMenu.fxml"));
            Parent root = loader.load();

            AdminMainMenuController adminMainMenuController = loader.getController();
            adminMainMenuController.InitData(currentUser);

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)backButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void HandleKeyPress(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            BackPressed();
        }
    }

    @FXML
    void RefreshPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomDateEventMenu.fxml"));
            Parent root = loader.load();
            RoomDateEventMenuController roomDateEventMenuController = loader.getController();
            roomDateEventMenuController.Init(currentUser, this.currentRoom, this.currentDate);
            // Create the second scene
            Scene scene2 = new Scene(root);
            // Get the current stage
            Stage stage = (Stage)backButton.getScene().getWindow();
            // Set the new scene
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
