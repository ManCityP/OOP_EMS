package com.fhm.oop_ems.Attendee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import p1.Database;
import p3.Attendee;
import p3.User;

public class AttendeeWalletMenuController {
    @FXML
    private TextField moneyAmount;
    @FXML
    private Button backButton;
    @FXML
    private Label errorText;
    @FXML
    private Button chatButton;
    @FXML
    private ImageView profile;

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
        try{
            this.currentUser = Attendee.FindAttendee(Database.GetAttendees(), user.GetUsername());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        this.username.setText(currentUser.GetUsername());
        walletBalance.setText(String.format("$%s", ((Attendee) currentUser).GetWallet().GetBalance()));
        walletNumber.setText(String.valueOf((Integer) (((Attendee) currentUser).GetWallet().GetWalletNumber())));
        moneyAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty())
                moneyAmount.setText("0");
            else if(!newValue.matches("\\d*"))
                moneyAmount.setText(oldValue.isEmpty()? "0" : oldValue);
            else {
                try {
                    int x = Integer.parseInt(newValue);
                    moneyAmount.setText(String.format("%s", x));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    moneyAmount.setText(oldValue.isEmpty()? "0" : oldValue);
                }
            }
        });
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
    private void ProfilePressed(){
        try {
            System.out.println("profile pressed");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeProfileMenu.fxml"));
            Parent ro = loader.load();

            AttendeeProfileMenuController attendeeProfileMenuController = loader.getController();
            attendeeProfileMenuController.Init(currentUser);

            Scene scene2 = new Scene(ro);
            Stage stage = (Stage) profile.getScene().getWindow();
            stage.setScene(scene2);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    void WithdrawPressed() {
        System.out.println("withdraw pressed");
        try {
            ((Attendee) currentUser).GetWallet().EditBalance(Double.parseDouble(moneyAmount.getText())*-1);
            walletBalance.setText(String.format("$%s", ((Attendee) currentUser).GetWallet().GetBalance()));
        } catch (Exception e) {
            e.printStackTrace();
            errorText.setText(e.getMessage());
        }
    }

    @FXML
    void DepositPressed() {
        System.out.println("deposit pressed");
        try {
            ((Attendee) currentUser).GetWallet().EditBalance(Double.parseDouble(moneyAmount.getText()));
            walletBalance.setText(String.format("$%s", ((Attendee) currentUser).GetWallet().GetBalance()));
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
