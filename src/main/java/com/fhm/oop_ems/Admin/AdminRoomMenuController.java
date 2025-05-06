package com.fhm.oop_ems.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import p1.Database;
import p1.Room;
import p3.User;

import java.util.ArrayList;
import java.util.List;

public class AdminRoomMenuController {

    @FXML
    private Button backButton;
    @FXML
    private Button chatButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button refreshButton;
    @FXML
    private VBox roomsContainer;
    @FXML
    private TextField searchField;
    @FXML
    private Label username;

    private ArrayList<Room> rooms;

    private User currentUser;

    public void InitData(User user, ArrayList<Room> rooms) {
        currentUser = user;
        this.username.setText(currentUser.GetUsername());
        this.rooms = rooms;

        try {
            for (Room room : this.rooms) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomPaneTemplate.fxml"));
                Node roomNode = loader.load();

                RoomPaneTemplateController controller = loader.getController();
                controller.init(currentUser, room, this);

                roomsContainer.getChildren().add(roomNode);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    void SearchPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            String prompt = searchField.getText();
            ArrayList<Room> searchRooms = new ArrayList<>();
            try {
                int roomNumber = Integer.parseInt(prompt);

                for(Room room : Database.GetRooms()) {
                    if(roomNumber == room.GetID())
                        searchRooms.add(room);
                    else if(room.GetLocation().toLowerCase().contains(prompt.toLowerCase()))
                        searchRooms.add(room);
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminRoomMenu.fxml"));
                Parent root = loader.load();

                AdminRoomMenuController adminRoomMenuController = loader.getController();
                adminRoomMenuController.InitData(currentUser, searchRooms);
                adminRoomMenuController.searchField.setText(prompt);

                // Create the second scene
                Scene scene2 = new Scene(root);

                // Get the current stage
                Stage stage = (Stage)refreshButton.getScene().getWindow();

                // Set the new scene
                stage.setScene(scene2);
            }
            catch (NumberFormatException ex) {
                try {
                    for(Room room : Database.GetRooms())
                        if(room.GetLocation().toLowerCase().contains(prompt.toLowerCase()))
                            searchRooms.add(room);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminRoomMenu.fxml"));
                    Parent root = loader.load();

                    AdminRoomMenuController adminRoomMenuController = loader.getController();
                    adminRoomMenuController.InitData(currentUser, searchRooms);
                    adminRoomMenuController.searchField.setText(prompt);

                    // Create the second scene
                    Scene scene2 = new Scene(root);

                    // Get the current stage
                    Stage stage = (Stage)refreshButton.getScene().getWindow();

                    // Set the new scene
                    stage.setScene(scene2);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    void HandleKeyPress(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            BackPressed();
        }
    }

    @FXML
    void LabelHovered(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-underline: true;");
    }

    @FXML
    void LabelNotHovered(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-underline: false;");
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
    void CreateRoomPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateRoomMenu.fxml"));
            Parent root = loader.load();

            CreateRoomController createRoomController = loader.getController();
            createRoomController.Init(currentUser);

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
    void BackPressed() {
        DashboardPressed();
    }

    @FXML
    void RefreshPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminRoomMenu.fxml"));
            Parent root = loader.load();

            AdminRoomMenuController adminRoomMenuController = loader.getController();
            adminRoomMenuController.InitData(currentUser, Database.GetRooms());

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
}
