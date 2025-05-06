package com.fhm.oop_ems.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import p1.Admin;
import p1.Database;
import p3.User;

public class CreateCategoryController {

    @FXML
    private Button backButton;

    @FXML
    private TextField categoryField;

    @FXML
    private Button chatButton;

    @FXML
    private Button createButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button refreshButton;

    @FXML
    private Label username;

    private User currentUser;

    public void Init(User user) {
        this.currentUser = user;
        this.username.setText(currentUser.GetUsername());
        categoryField.setTextFormatter(new TextFormatter<>(change -> {
            return change.getControlNewText().length() <= 32 ? change : null;
        }));
    }

    @FXML
    void CreatePressed() {
        if(categoryField.getText().isEmpty())
            errorLabel.setText("Category name cannot be empty!");
        else {
            try {
                ((Admin)currentUser).AddCategory(categoryField.getText());
                BackPressed();
            }
            catch (Exception ex) {
                errorLabel.setText("Category already exists!");
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
    void RefreshPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateCategoryMenu.fxml"));
            Parent root = loader.load();

            CreateCategoryController createCategoryController = loader.getController();
            createCategoryController.Init(currentUser);

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCategoryMenu.fxml"));
            Parent root = loader.load();

            AdminCategoryMenuController adminCategoryMenuController = loader.getController();
            adminCategoryMenuController.InitData(currentUser, Database.GetCategories());

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
    void ChatPressed() {
        System.out.println("Chat Pressed!");
    }

    @FXML
    void ButtonHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #5F6368;");
    }

    @FXML
    void ButtonNotHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: transparent;");
    }
}
