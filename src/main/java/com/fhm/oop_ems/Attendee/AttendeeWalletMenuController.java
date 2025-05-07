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

public class AttendeeWalletMenuController {

    @FXML
    private Button backButton;
    @FXML
    private Label errorText;
    @FXML
    private Button chatButton;


    @FXML
    private Button dashboardButton;


    @FXML
    private Button refreshButton;

    @FXML
    private Label walletNumber;
    @FXML
    private Label walletBalance;
    @FXML
    private Label username;
    @FXML
    private Button withdrawButton;
    @FXML
    private Button depositButton;


    private User currentUser;

    public void Init(User user) {
        this.currentUser = user;
        this.username.setText(currentUser.GetUsername());
        walletBalance.setText(String.format("$%s", ((Attendee) currentUser).GetWallet().GetBalance()));
        walletNumber.setText(String.valueOf((Integer) (((Attendee) currentUser).GetWallet().GetWalletNumber())));
    }

    @FXML
    void HandleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            BackPressed();
        }
    }

    @FXML
    void ButtonHovered(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #6EACDA;");
    }

    @FXML
    void ButtonNotHovered(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: transparent;");
    }

    @FXML
    void WalletButtonNotHovered(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: transparent; -fx-border-color: white");
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
    void WithdrawPressed() {
        System.out.println("withdraw pressed");
        try {
            ((Attendee) currentUser).GetWallet().EditBalance(-100);
            RefreshPressed();
        } catch (Exception e) {
            e.printStackTrace();
            errorText.setText(e.getMessage());
        }
    }

    @FXML
    void DepositPressed() {
        System.out.println("deposit pressed");
        try {
            ((Attendee) currentUser).GetWallet().EditBalance(100);
            RefreshPressed();
        } catch (Exception e) {
            e.printStackTrace();
            errorText.setText(e.getMessage());
        }
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
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void RefreshPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeWalletMenu.fxml"));
            Parent root = loader.load();

            AttendeeWalletMenuController attendeeWalletMenuController = loader.getController();
            attendeeWalletMenuController.Init(currentUser);

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) refreshButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
