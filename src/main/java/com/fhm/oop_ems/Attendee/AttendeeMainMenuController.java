package com.fhm.oop_ems.Attendee;


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

import java.io.IOException;

public class AttendeeMainMenuController {
    @FXML
    private Label username;
    @FXML
    private Label usersButton;
    @FXML
    private Label calendarButton;
    @FXML
    private Label buyTicketsButton;
    @FXML
    private Label eventsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label myWalletButton;
    @FXML
    private ImageView profileButton;

    private User currentUser;

    public void InitData(User user) {
        this.currentUser = user;
        username.setText(user.GetUsername());
    }
    @FXML
    private void CalendarPressed(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TicketsCalendar.fxml"));
            Parent root = loader.load();

            TicketsCalendarController ticketsCalendarController = loader.getController();
            ticketsCalendarController.Init(currentUser, Database.GetEvents());

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)calendarButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void usersPressed(){
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
    private void WalletPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeWalletMenu.fxml"));
            Parent root = loader.load();

            AttendeeWalletMenuController attendeeWalletMenuController = loader.getController();
            attendeeWalletMenuController.Init(currentUser);

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)myWalletButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ProfilePressed(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeProfileMenu.fxml"));
            Parent ro = loader.load();

            AttendeeProfileMenuController attendeeProfileMenuController = loader.getController();
            attendeeProfileMenuController.Init(currentUser);

            Scene scene2 = new Scene(ro);
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene2);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void BuyTicketsPressed() {
        try {
            System.out.println("events button pressed");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeTicketsMenu.fxml"));
            Parent ro = loader.load();

            AttendeeTicketsMenuController attendeeTicketsMenuController = loader.getController();
            attendeeTicketsMenuController.InitData(currentUser, Database.GetEvents());

            Scene scene2 = new Scene(ro);
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene2);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    @FXML
    private void ButtonHovered(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #6EACDA; -fx-background-radius: 20; -fx-text-fill: white;");
    }

    @FXML
    private void ButtonNotHovered(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #021526; -fx-background-radius: 20; -fx-text-fill: #E2E2B6;");
    }

    @FXML
    void ButtonHoveredmo(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #5F6368;");
    }

    @FXML
    void ButtonNotHoveredmo(MouseEvent event) {
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
    void LogoutPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
            Parent root = loader.load();

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) logoutButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
