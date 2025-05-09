package com.fhm.oop_ems.Admin;

import com.fhm.oop_ems.HelloApplication;
import com.fhm.oop_ems.UsersMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import p1.Database;
import p3.User;

public class AdminMainMenuController {
    @FXML
    private Label categoriesButton;
    @FXML
    private Label usersButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Label eventsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private ImageView profileButton;
    @FXML
    private Label roomsButton;
    @FXML
    private Label username;

    private User currentUser;

    public void InitData(User user) {
        currentUser = user;
        username.setText(currentUser.GetUsername());
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
    private void LabelHovered(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-underline: true;");
    }

    @FXML
    private void LabelNotHovered(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-underline: false;");
    }

    @FXML
    void CategoriesPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCategoryMenu.fxml"));
            Parent root = loader.load();

            AdminCategoryMenuController adminCategoryMenuController = loader.getController();
            adminCategoryMenuController.InitData(currentUser, Database.GetCategories());

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)categoriesButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void ChatPressed() {
        System.out.println("Chat loaded!");
    }

    @FXML
    void DashboardPressed() {
        System.out.println("Dashboard refreshed!");
    }

    @FXML
    void UsersPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("UsersMenu.fxml"));
            Parent root = loader.load();

            UsersMenuController usersMenuController = loader.getController();
            usersMenuController.Init(currentUser, User.GetUsers());

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)usersButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void LogoutPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
            Parent root = loader.load();

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)logoutButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void ProfilePressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminProfileMenu.fxml"));
            Parent root = loader.load();

            AdminProfileMenuController adminProfileMenuController = loader.getController();
            adminProfileMenuController.Init(currentUser);

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)categoriesButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void RoomsPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminRoomMenu.fxml"));
            Parent root = loader.load();

            AdminRoomMenuController adminRoomMenuController = loader.getController();
            adminRoomMenuController.InitData(currentUser, Database.GetRooms());

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)logoutButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
