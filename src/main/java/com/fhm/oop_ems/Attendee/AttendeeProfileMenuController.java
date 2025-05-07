package com.fhm.oop_ems.Attendee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import p3.Attendee;
import p3.User;

public class AttendeeProfileMenuController {

    @FXML
    private Button backButton;

    @FXML
    private Label birthDateLabel;

    @FXML
    private Button chatButton;

    @FXML
    private Label createTimeLabel;

    @FXML
    private Button dashboardButton;

    @FXML
    private Label emailLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Button refreshButton;


    @FXML
    private Label typeLabel;

    @FXML
    private Label username;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label interests1;

    private User currentUser;

    public void Init(User user) {
        this.currentUser = user;
        this.username.setText(currentUser.GetUsername());
        this.typeLabel.setText("Attendee");
        this.usernameLabel.setText(currentUser.GetUsername());
        this.emailLabel.setText(currentUser.GetEmail());
        this.genderLabel.setText(currentUser.GetGender().toString());
        this.birthDateLabel.setText(currentUser.GetBirthDate().toString());
        this.interests1.setText(((Attendee)currentUser).guiConcat().toString());
        //todo this.createTimeLabel.setText((Attendee)currentUser).GetDateCreated());

    }

    @FXML
    void HandleKeyPress(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            BackPressed();
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
    void BackPressed() {
        DashboardPressed();
    }

    @FXML
    void DashboardPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeMainMenu.fxml"));
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
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void RefreshPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeProfileMenu.fxml"));
            Parent root = loader.load();

            AttendeeProfileMenuController attendeeProfileMenuController = loader.getController();
            attendeeProfileMenuController.Init(currentUser);

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
