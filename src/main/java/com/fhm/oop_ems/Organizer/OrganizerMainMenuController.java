package com.fhm.oop_ems.Organizer;

import com.fhm.oop_ems.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import p3.User;

import java.io.IOException;

public class OrganizerMainMenuController {

    @FXML
    private Label createEventButton;
    @FXML
    private Label eventsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label myWalletButton;
    @FXML
    private Label profileButton;

    private User currentUser;

    public void InitData(User user) {
        this.currentUser = user;
    }

    @FXML
    private void EventsButtonPressed(){
        try {
            System.out.println("events button pressed");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerEventMenu.fxml"));
            Parent root = loader.load();

            OrganizerEventMenuController organizerEventMenuController = loader.getController();
            organizerEventMenuController.InitData(currentUser);

            Scene scene2 = new Scene(root);
            Stage stage = (Stage)logoutButton.getScene().getWindow();
            stage.setScene(scene2);
        }
        catch (Exception ex) {
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
            Stage stage = (Stage)logoutButton.getScene().getWindow();
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void CreateEventButtonPressed(){
        try {
            System.out.println("Create event button pressed");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerCreateEventMenu.fxml"));
            Parent root = loader.load();

            OrganizerCreateEventMenuController organizerCreateEventMenuController = loader.getController();
            organizerCreateEventMenuController.Init(currentUser);

            Scene scene2 = new Scene(root);
            Stage stage = (Stage)logoutButton.getScene().getWindow();
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void MyWalletButtonPressed(){
        try {
            System.out.println("Wallet button pressed");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerWalletMenu.fxml"));
            Parent root = loader.load();

            OrganizerWalletMenuController organizerWalletMenuController = loader.getController();
            organizerWalletMenuController.Init(currentUser);

            Scene scene2 = new Scene(root);
            Stage stage = (Stage)logoutButton.getScene().getWindow();
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ButtonHovered(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #d62300; -fx-background-radius: 20; -fx-text-fill: white;");
    }

    @FXML
    private void ButtonNotHovered(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #F0E6D7; -fx-background-radius: 20; -fx-text-fill: #885133;");
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
    private void LogoutPressed() throws IOException {
        System.out.println("logout button pressed");
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)logoutButton.getScene().getWindow();
        stage.setScene(scene);
    }
}
