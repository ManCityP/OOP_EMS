package com.fhm.oop_ems;

import com.fhm.oop_ems.Admin.AdminMainMenuController;
import com.fhm.oop_ems.Admin.AdminRoomMenuController;
import com.fhm.oop_ems.Admin.RoomPaneTemplateController;
import com.fhm.oop_ems.Attendee.AttendeeMainMenuController;
import com.fhm.oop_ems.Organizer.OrganizerMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import p1.Admin;
import p1.Database;
import p1.Room;
import p2.Organizer;
import p3.Attendee;
import p3.User;

import java.util.ArrayList;

public class UsersMenuController {

    @FXML
    private Button backButton;
    @FXML
    private Button chatButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button refreshButton;
    @FXML
    private VBox usersContainer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField searchField;
    @FXML
    private Label username;

    private User currentUser;
    private ArrayList<User> users;

    public void Init(User currentUser, ArrayList<User> users) {
        this.currentUser = currentUser;
        this.username.setText(this.currentUser.GetUsername());
        this.users = users;

        try {
            for(User user : this.users) {
                if(user.GetUsername().equals(this.currentUser.GetUsername()))
                    continue;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPaneTemplate.fxml"));
                Node userNode = loader.load();

                UserPaneTemplateController controller = loader.getController();
                controller.Init(currentUser, user);

                usersContainer.getChildren().add(userNode);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void BackPressed() {
        DashboardPressed();
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
    void DashboardPressed() {
        try {
            if(currentUser instanceof Admin) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin/AdminMainMenu.fxml"));
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
            else if(currentUser instanceof Organizer) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Organizer/OrganizerMainMenu.fxml"));
                Parent root = loader.load();
                OrganizerMainMenuController organizerMainMenuController = loader.getController();
                organizerMainMenuController.InitData(currentUser);
                // Create the second scene
                Scene scene2 = new Scene(root);
                // Get the current stage
                Stage stage = (Stage)backButton.getScene().getWindow();
                // Set the new scene
                stage.setScene(scene2);
            }
            else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Attendee/AttendeeMainMenu.fxml"));
                Parent root = loader.load();
                AttendeeMainMenuController attendeeMainMenuController = loader.getController();
                attendeeMainMenuController.InitData(currentUser);
                // Create the second scene
                Scene scene2 = new Scene(root);
                // Get the current stage
                Stage stage = (Stage)backButton.getScene().getWindow();
                // Set the new scene
                stage.setScene(scene2);
            }
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersMenu.fxml"));
            Parent root = loader.load();

            UsersMenuController usersMenuController = loader.getController();
            usersMenuController.Init(currentUser, User.GetUsers());

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
    void SearchPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            String prompt = searchField.getText();
            ArrayList<User> searchUsers = new ArrayList<>();
            try {
                for(User user : User.GetUsers()) {
                    if(user.GetUsername().toLowerCase().contains(prompt.toLowerCase()))
                        searchUsers.add(user);
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersMenu.fxml"));
                Parent root = loader.load();

                UsersMenuController usersMenuController = loader.getController();
                usersMenuController.Init(currentUser, searchUsers);
                usersMenuController.searchField.setText(prompt);

                // Create the second scene
                Scene scene2 = new Scene(root);

                // Get the current stage
                Stage stage = (Stage)refreshButton.getScene().getWindow();

                // Set the new scene
                stage.setScene(scene2);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
