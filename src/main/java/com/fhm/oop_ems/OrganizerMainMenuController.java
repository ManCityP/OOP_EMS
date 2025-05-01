package com.fhm.oop_ems;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)logoutButton.getScene().getWindow();
        stage.setScene(scene);
    }
}
