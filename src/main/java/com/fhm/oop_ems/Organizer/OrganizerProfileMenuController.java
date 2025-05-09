package com.fhm.oop_ems.Organizer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import p2.Organizer;
import p3.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;

public class OrganizerProfileMenuController {
    @FXML private Button backButton;
    @FXML private Label birthDateLabel;
    @FXML private Label createTimeLabel;
    @FXML private Label emailLabel;
    @FXML private Label genderLabel;
    @FXML private Label typeLabel;
    @FXML private Label usernameLabel;
    @FXML private Label walletBalanceLabel;
    @FXML private Label walletIDLabel;

    private User user;

    public void Init(User user) {
        this.user = user;
        typeLabel.setText("Organizer");
        usernameLabel.setText(user.GetUsername());
        emailLabel.setText(user.GetEmail());
        genderLabel.setText(String.format("%s", user.GetGender()));
        birthDateLabel.setText(String.format("%s", user.GetBirthDate()));
        createTimeLabel.setText(String.format("%s", user.GetDateCreated()));
        walletIDLabel.setText(String.format("%s", ((Organizer)user).GetWallet().GetWalletNumber()));
        walletBalanceLabel.setText(String.format("%s", ((Organizer)user).GetWallet().GetBalance()));
    }

    @FXML
    private void BackButtonPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerMainMenu.fxml"));
            Parent root = loader.load();

            OrganizerMainMenuController organizerMainMenuController = loader.getController();
            organizerMainMenuController.InitData(user);

            Scene scene2 = new Scene(root);
            Stage stage = (Stage)backButton.getScene().getWindow();
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
