package com.fhm.oop_ems.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import p1.Admin;
import p1.Database;
import p1.Day;
import p1.TimeRange;
import p3.User;

import java.util.ArrayList;
import java.util.Map;

public class AdminProfileMenuController {

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
    private Label roleLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label username;

    @FXML
    private Label usernameLabel;

    @FXML
    private GridPane workingHoursGrid;

    private User currentUser;

    public void Init(User user) {
        this.currentUser = user;
        this.username.setText(currentUser.GetUsername());
        this.typeLabel.setText("Admin");
        this.usernameLabel.setText(currentUser.GetUsername());
        this.emailLabel.setText(currentUser.GetEmail());
        this.genderLabel.setText(currentUser.GetGender().toString());
        this.birthDateLabel.setText(currentUser.GetBirthDate().toString());
        this.roleLabel.setText(((Admin)currentUser).GetRole());

        Map<String, ArrayList<TimeRange>> workingHours = ((Admin) currentUser).GetWorkingHours().GetMap();

        for(Node node : workingHoursGrid.getChildren()) {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            if(row == null)
                row = 0;
            if(col == null)
                col = 0;

            if(row == 1 && node instanceof Label) {
                ArrayList<TimeRange> timeRanges = workingHours.get(Day.Translate(col).toString());
                if(timeRanges == null || timeRanges.isEmpty()) {
                    ((Label) node).setText("N/A");
                }
                else {
                    StringBuilder time = new StringBuilder();
                    for (TimeRange timeRange : timeRanges) {
                        time.append(timeRange.toString() + '\n');
                    }
                    if (!time.isEmpty())
                        time.delete(time.length() - 1, time.length());
                    ((Label) node).setText(time.toString());
                }
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminProfileMenu.fxml"));
            Parent root = loader.load();

            AdminProfileMenuController adminProfileMenuController = loader.getController();
            adminProfileMenuController.Init(currentUser);

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
