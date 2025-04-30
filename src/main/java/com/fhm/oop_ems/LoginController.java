package com.fhm.oop_ems;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    protected void Login(ActionEvent event){
        try {
            User.Login(this.username.getText(),this.password.getText());
        }catch (Exception ex){
            errorText.setText(ex.getMessage());
        }
    }
}
