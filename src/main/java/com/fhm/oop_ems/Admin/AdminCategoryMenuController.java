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

public class AdminCategoryMenuController {

    @FXML
    private Button backButton;
    @FXML
    private Button chatButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button refreshButton;
    @FXML
    private VBox categoriesContainer;
    @FXML
    private TextField searchField;
    @FXML
    private Label username;

    private ArrayList<String> categories;

    private User currentUser;

    public void InitData(User user, ArrayList<String> categories) {
        currentUser = user;
        username.setText(currentUser.GetUsername());
        this.categories = categories;

        try {
            for (String category : this.categories) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CategoryPaneTemplate.fxml"));
                Node roomNode = loader.load();

                CategoryPaneTemplateController controller = loader.getController();
                controller.Init(currentUser, category, this);

                categoriesContainer.getChildren().add(roomNode);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void SearchPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            String prompt = searchField.getText();
            ArrayList<String> searchCategories = new ArrayList<>();
            try {
                for(String category : Database.GetCategories()) {
                    if(category.toLowerCase().contains(prompt.toLowerCase()))
                        searchCategories.add(category);
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCategoryMenu.fxml"));
                Parent root = loader.load();

                AdminCategoryMenuController adminCategoryMenuController = loader.getController();
                adminCategoryMenuController.InitData(currentUser, searchCategories);
                adminCategoryMenuController.searchField.setText(prompt);

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
    void CreateCategoryPressed() {
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
    void ProfilePressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminProfileMenu.fxml"));
            Parent root = loader.load();

            AdminProfileMenuController adminProfileMenuController = loader.getController();
            adminProfileMenuController.Init(currentUser);

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)dashboardButton.getScene().getWindow();

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCategoryMenu.fxml"));
            Parent root = loader.load();

            AdminCategoryMenuController adminCategoryMenuController = loader.getController();
            adminCategoryMenuController.InitData(currentUser, Database.GetCategories());

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
