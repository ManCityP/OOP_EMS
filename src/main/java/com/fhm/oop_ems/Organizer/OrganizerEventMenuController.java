package com.fhm.oop_ems.Organizer;

import com.fhm.oop_ems.Attendee.AttendeeTicketsMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import p1.Database;
import p2.Event;
import p3.Attendee;
import p3.User;

import java.io.IOException;
import java.util.ArrayList;

public class OrganizerEventMenuController {

    @FXML
    private Button backButton;
    @FXML
    private VBox eventsContainer;
    @FXML
    private Label noEventsLabel;
    @FXML
    private Button dashboardButton;
    @FXML
    private Label username;
    @FXML
    private Label addEventButton;
    @FXML
    private TextField searchBar;

    private User user;
    private int eventsLoaded = 0;
    private ArrayList<Event> events;

    public void InitData(User user, ArrayList<Event> events) { //TODO
        this.user = user;
        this.events = events;
        this.username.setText(user.GetUsername());

        try {
            for (Event event : events) {
                if(event.GetOrganizer().GetUsername().equals(this.user.GetUsername())){

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("DefaultEventPaneTemplate.fxml"));
                        Node roomNode = loader.load();
                        EventPaneTemplateController controller = loader.getController();
                        controller.displayevent(this.user, event);

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

    @FXML
    private void DashboardPressed() {
        try{
            BackButtonPressed();
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void AddEventButtonPressed(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerCreateEventMenu.fxml"));
            Parent root = loader.load();

            OrganizerCreateEventMenuController organizerCreateEventMenuController = loader.getController();
            organizerCreateEventMenuController.Init(user);

            Scene scene2 = new Scene(root);
            Stage stage = (Stage)backButton.getScene().getWindow();
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    void Search(){
        String prompt = searchBar.getText();
        ArrayList<Event> searchEvents = new ArrayList<>();
        try {
            int eventID = Integer.parseInt(prompt);
            for(Event event1 : Database.GetEvents()) {
                if(eventID == event1.GetID())
                    searchEvents.add(event1);
                else if(event1.GetRoomID() == eventID)
                    searchEvents.add(event1);
                else if(event1.GetEventTitle().toLowerCase().contains(prompt.toLowerCase()))
                    searchEvents.add(event1);
                else if(prompt.equals(event1.GetDate().toString()))
                    searchEvents.add(event1);
                else if (event1.GetOrganizer().toString().toLowerCase().contains(prompt.toLowerCase()))
                    searchEvents.add(event1);
                else if (event1.GetCategory().toString().toLowerCase().contains(prompt.toLowerCase()))
                    searchEvents.add(event1);
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerEventMenu.fxml"));
            Parent root = loader.load();

            OrganizerEventMenuController organizerEventMenuController = loader.getController();
            organizerEventMenuController.InitData(user, searchEvents);
            organizerEventMenuController.searchBar.setText(prompt);

            Scene scene2 = new Scene(root);
            Stage stage = (Stage)backButton.getScene().getWindow();
            stage.setScene(scene2);
        }
        catch (NumberFormatException ex) {
            try {
                for(Event event1 : Database.GetEvents()){
                    if(event1.GetEventTitle().toLowerCase().contains(prompt.toLowerCase()))
                        searchEvents.add(event1);
                    else if(prompt.equals(event1.GetDate().toString()))
                        searchEvents.add(event1);
                    else if (event1.GetOrganizer().toString().toLowerCase().contains(prompt.toLowerCase()))
                        searchEvents.add(event1);
                    else if (event1.GetCategory().toString().toLowerCase().contains(prompt.toLowerCase()))
                        searchEvents.add(event1);
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerEventMenu.fxml"));
                Parent root = loader.load();

                OrganizerEventMenuController organizerEventMenuController = loader.getController();
                organizerEventMenuController.InitData(user, searchEvents);
                organizerEventMenuController.searchBar.setText(prompt);

                Scene scene2 = new Scene(root);
                Stage stage = (Stage)backButton.getScene().getWindow();
                stage.setScene(scene2);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void SearchPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            Search();
        }
    }

    @FXML
    private void AddEventButtonHovered(){
        this.addEventButton.setUnderline(true);
    }

    @FXML
    private void AddEventButtonNotHovered(){
        this.addEventButton.setUnderline(false);
    }

    @FXML
    private void ProfileImagePressed(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerProfileMenu.fxml"));
            Parent root = loader.load();

            OrganizerProfileMenuController organizerProfileMenuController = loader.getController();
            organizerProfileMenuController.Init(user);

            Scene scene2 = new Scene(root);
            Stage stage = (Stage)backButton.getScene().getWindow();
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
