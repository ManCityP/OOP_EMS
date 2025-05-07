package com.fhm.oop_ems.Organizer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import p1.Database;
import p2.Event;
import p3.User;

import java.io.IOException;

public class OrganizerEventMenuController {

    @FXML
    private Button backButton;
    @FXML
    private VBox eventsContainer;
    @FXML
    private Label noEventsLabel;

    private User user;
    private int eventsLoaded = 0;

    public void InitData(User user) {
        this.user = user;

        try {
            for (Event event : Database.GetEvents()) {
                if(event.GetOrganizer().GetUsername().equals(this.user.GetUsername())){

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EventPaneTemplate.fxml"));
                    Node roomNode = loader.load();
                    EventPaneTemplateController controller = loader.getController();
                    controller.displayevent(event);

                    eventsContainer.getChildren().add(roomNode);
                    eventsLoaded++;
                }
            }

            if (eventsLoaded == 0)
                noEventsLabel.setText("No Events Available");
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void BackButtonPressed() throws IOException {
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
