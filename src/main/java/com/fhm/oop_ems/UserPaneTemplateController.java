package com.fhm.oop_ems;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import p1.Admin;
import p3.Attendee;
import p3.User;

public class UserPaneTemplateController {

    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private ImageView chatButton;
    @FXML
    private Label createTimeLabel;
    @FXML
    private Label flexLabel;
    @FXML
    private Label birthDateLabel;

    private User currentUser;
    private User displayedUser;

    public void Init(User currentUser, User displayedUser) {
        this.currentUser = currentUser;
        this.displayedUser = displayedUser;

        this.usernameLabel.setText(displayedUser.GetUsername());
        this.emailLabel.setText(displayedUser.GetEmail());
        this.genderLabel.setText(displayedUser.GetGender().toString());
        this.birthDateLabel.setText(displayedUser.GetBirthDate().toString());
        this.createTimeLabel.setText(displayedUser.GetDateCreated().toString());
        if(displayedUser instanceof Admin) {
            this.flexLabel.setText(String.format("Role: %s", ((Admin)this.displayedUser).GetRole()));
        }
        else if(displayedUser instanceof Attendee) {
            this.flexLabel.setText(String.format("Interests: %s", ((Attendee)this.displayedUser).GetInterests().toString()));
        }
        else {
            this.flexLabel.setVisible(false);
            this.flexLabel.setDisable(false);
        }
    }

    @FXML
    void ChatPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
            Parent newRoot = loader.load(); // Load the FXML content

            // Create a new stage (new window)
            Stage newStage = new Stage();
            Scene newScene = new Scene(newRoot, 600, 600);
            newStage.setMinWidth(600);
            newStage.setMinHeight(600);
            newStage.setTitle("New Window");
            newStage.setScene(newScene);

            // Set the modality to block interaction with the primary stage
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(chatButton.getScene().getWindow()); // Set the owner to the primary stage

            ChatController chatController = loader.getController();
            chatController.Init(currentUser, displayedUser);

            // Show the new stage (it will block interaction with the primary window until closed)
            newStage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
