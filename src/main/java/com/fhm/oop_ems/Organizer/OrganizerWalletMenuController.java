package com.fhm.oop_ems.Organizer;

import com.fhm.oop_ems.Attendee.AttendeeMainMenuController;
import com.fhm.oop_ems.Attendee.AttendeeWalletMenuController;
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
import p1.Database;
import p2.Organizer;
import p3.Attendee;
import p3.User;

public class OrganizerWalletMenuController {


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
        try{
            this.currentUser = Organizer.FindOrganizer(Database.GetOrganizers(), user.GetUsername());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.username.setText(currentUser.GetUsername());
        walletBalance.setText(String.format("$%s", ((Organizer)currentUser).GetWallet().GetBalance()));
        walletNumber.setText(String.valueOf((Integer) (((Organizer)currentUser).GetWallet().GetWalletNumber())));
    }

/*    @FXML
    void HandleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            BackPressed();
        }
    }*/

/*    @FXML
    void ButtonHovered(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #6EACDA;");
    }*/

/*    @FXML
    void ButtonNotHovered(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: transparent;");
    }*/

/*    @FXML
    void WalletButtonNotHovered(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: transparent; -fx-border-color: white");
    }*/

/*    @FXML
    void ChatPressed() {
        System.out.println("Chat Pressed!");
    }*/

    @FXML
    void BackPressed() {
        DashboardPressed();
    }

    @FXML
    void WithdrawPressed() {
        System.out.println("withdraw pressed");
        try {
            ((Organizer)currentUser).GetWallet().EditBalance(-100);
            walletBalance.setText(String.format("$%s", ((Organizer)currentUser).GetWallet().GetBalance()));
        } catch (Exception e) {
            e.printStackTrace();
            errorText.setText(e.getMessage());
        }
    }

    @FXML
    void DepositPressed() {
        System.out.println("deposit pressed");
        try {
            ((Organizer)currentUser).GetWallet().EditBalance(100);
            walletBalance.setText(String.format("$%s", ((Organizer)currentUser).GetWallet().GetBalance()));
        } catch (Exception e) {
            e.printStackTrace();
            errorText.setText(e.getMessage());
        }
    }

    @FXML
    void DashboardPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerMainMenu.fxml"));
            Parent root = loader.load();

            OrganizerMainMenuController organizerMainMenuController = loader.getController();
            organizerMainMenuController.InitData(currentUser);

            Scene scene2 = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene2);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ProfileButtonPressed(){
        try {
            System.out.println("Profile button pressed");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerProfileMenu.fxml"));
            Parent root = loader.load();

            OrganizerProfileMenuController organizerProfileMenuController = loader.getController();
            organizerProfileMenuController.Init(currentUser);

            Scene scene2 = new Scene(root);
            Stage stage = (Stage)backButton.getScene().getWindow();
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void RefreshPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerWalletMenu.fxml"));
            Parent root = loader.load();

            OrganizerWalletMenuController organizerWalletMenuController = loader.getController();
            organizerWalletMenuController.Init(currentUser);

            Scene scene2 = new Scene(root);
            Stage stage = (Stage) refreshButton.getScene().getWindow();
            stage.setScene(scene2);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
