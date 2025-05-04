package com.fhm.oop_ems;

import com.fhm.oop_ems.Admin.AdminMainMenuController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import p1.Admin;
import p2.Organizer;
import p3.User;

public class LoginController {

    @FXML
    private Label errorText;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField password;
    @FXML
    private Label registerLabel;
    @FXML
    private TextField username;

    @FXML
    public void initialize() {
        Platform.runLater(() -> username.requestFocus());
        username.setTextFormatter(new TextFormatter<>(change -> {
            return change.getControlNewText().length() <= 128 ? change : null;
        }));
        password.setTextFormatter(new TextFormatter<>(change -> {
            return change.getControlNewText().length() <= 32 ? change : null;
        }));
    }

    @FXML
    void HandleKeyPress(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            Login();
        }
        else if(event.getCode() == KeyCode.TAB) {
            if (username.isFocused()) {
                password.requestFocus();
                event.consume();
            }
            else {
                username.requestFocus();
                event.consume();
            }
        }
    }

    @FXML
    protected void Login(){
        try {
            User user = User.Login(this.username.getText(),this.password.getText());
            if(user instanceof Admin) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin/AdminMainMenu.fxml"));
                Parent root = loader.load();

                AdminMainMenuController adminMainMenuController = loader.getController();
                adminMainMenuController.InitData(user);

                // Create the second scene
                Scene scene2 = new Scene(root);

                // Get the current stage
                Stage stage = (Stage)loginButton.getScene().getWindow();

                // Set the new scene
                stage.setScene(scene2);
            }
            else if(user instanceof Organizer) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerMainMenu.fxml"));
                Parent root = loader.load();

                OrganizerMainMenuController organizerMainMenuController = loader.getController();
                organizerMainMenuController.InitData(user);

                // Create the second scene
                Scene scene2 = new Scene(root);

                // Get the current stage
                Stage stage = (Stage)loginButton.getScene().getWindow();

                // Set the new scene
                stage.setScene(scene2);
            }
            else {

            }
        }
        catch (Exception ex){
            errorText.setText(ex.getMessage());
        }
    }
}
