package com.fhm.oop_ems.Attendee;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import p1.Database;
import p1.Room;
import p2.Event;
import p2.Status;
import p3.Attendee;
import p3.User;

import java.util.ArrayList;

public class AttendeeTicketsMenuController {
    @FXML
    private CheckBox filterInterests;
    @FXML
    private ImageView profile;
    @FXML
    Label errorText;
    @FXML
    private Button backButton;
    @FXML
    private Button chatButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button refreshButton;
    @FXML
    private VBox roomsContainer;
    @FXML
    private TextField searchField;
    @FXML
    private Label username;

    private ArrayList<Event> events;

    private User currentUser;
    ArrayList<Room> rooms;

    public void InitData(User user, ArrayList<Event> events) {
        currentUser = user;
        this.username.setText(currentUser.GetUsername());
        this.events = events;
        try {
            rooms = Database.GetRooms();
            for (Event event : this.events) {
                if(event.GetStatus()!= Status.UPCOMING){
                    continue;
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("TicketsPaneTemplate.fxml"));
                Node roomNode = loader.load();

                TicketsPaneTemplateController controller = loader.getController();
                controller.init(currentUser, event, this);

                roomsContainer.getChildren().add(roomNode);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void InitData(User user, String search) {
        currentUser = user;
        this.searchField.setText(search);
        SearchPressed(new KeyEvent(KeyEvent.KEY_PRESSED,"","",KeyCode.ENTER,false,false,false,false));

    }
    @FXML
    private void ProfilePressed(){
        try {
            System.out.println("profile pressed");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeProfileMenu.fxml"));
            Parent ro = loader.load();

            AttendeeProfileMenuController attendeeProfileMenuController = loader.getController();
            attendeeProfileMenuController.Init(currentUser);

            Scene scene2 = new Scene(ro);
            Stage stage = (Stage) profile.getScene().getWindow();
            stage.setScene(scene2);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    void SearchPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            Search();
        }
    }
    void Search(){
        String prompt = searchField.getText();
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
                else if (event1.GetOrganizer().GetUsername().toLowerCase().contains(prompt.toLowerCase()))
                    searchEvents.add(event1);
                else if (event1.GetCategory().toString().toLowerCase().contains(prompt.toLowerCase()))
                    searchEvents.add(event1);

            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeTicketsMenu.fxml"));
            Parent root = loader.load();

            AttendeeTicketsMenuController attendeeTicketsMenuController = loader.getController();
            attendeeTicketsMenuController.InitData(currentUser, searchEvents);
            attendeeTicketsMenuController.searchField.setText(prompt);
            attendeeTicketsMenuController.filterInterests.setSelected(filterInterests.isSelected());

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)refreshButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        }
        catch (NumberFormatException ex) {
            try {
                for(Event event1 : Database.GetEvents()){
                    if(filterInterests.isSelected() && !(((Attendee)currentUser).guiConcat().toString().contains(event1.GetCategory().toString()))){
                        continue;
                    }
                    if(event1.GetEventTitle().toLowerCase().contains(prompt.toLowerCase()))
                        searchEvents.add(event1);
                    else if(prompt.equals(event1.GetDate().toString()))
                        searchEvents.add(event1);
                    else if (event1.GetOrganizer().GetUsername().toLowerCase().contains(prompt.toLowerCase()))
                        searchEvents.add(event1);
                    else if (event1.GetCategory().toString().toLowerCase().contains(prompt.toLowerCase()))
                        searchEvents.add(event1);
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeTicketsMenu.fxml"));
                Parent root = loader.load();

                AttendeeTicketsMenuController attendeeTicketsMenuController = loader.getController();
                attendeeTicketsMenuController.InitData(currentUser, searchEvents);
                attendeeTicketsMenuController.searchField.setText(prompt);
                attendeeTicketsMenuController.filterInterests.setSelected(filterInterests.isSelected());

                // Create the second scene
                Scene scene2 = new Scene(root);

                // Get the current stage
                Stage stage = (Stage)refreshButton.getScene().getWindow();

                // Set the new scene
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
    void BackPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeMainMenu.fxml"));
            Parent root = loader.load();

            AttendeeMainMenuController attendeeMainMenuController = loader.getController();
            attendeeMainMenuController.InitData(currentUser);

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)backButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    void RefreshPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeTicketsMenu.fxml"));
            Parent root = loader.load();

            AttendeeTicketsMenuController attendeeTicketsMenuController = loader.getController();
            attendeeTicketsMenuController.InitData(currentUser, Database.GetEvents());

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)refreshButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    void HandleKeyPress(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            BackPressed();
        }
    }

    @FXML
    void LabelHovered(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-underline: true;");
    }

    @FXML
    void LabelNotHovered(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-underline: false;");
    }

    @FXML
    void ButtonHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #6EACDA;");
    }

    @FXML
    void ButtonNotHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: transparent;");
    }

    @FXML
    void ChatPressed() {
        System.out.println("Chat Pressed!");
    }

}
