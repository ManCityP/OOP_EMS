package com.fhm.oop_ems.Attendee;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import p1.Day;
import p1.Room;
import p1.TimeRange;
import p2.Event;
import p3.User;

import java.util.ArrayList;
import java.util.Map;

public class TicketsPaneTemplateController {

    @FXML
    private GridPane availableHoursGrid;
    @FXML
    private Label eventCategoryLabel;
    @FXML
    private Label eventIDLabel;
    @FXML
    private Label eventOrganizerLabel;
    @FXML
    private Label eventPriceLabel;
    @FXML
    private Label eventTimeRangeLabel;
    @FXML
    private Label eventTitleLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label reservedCalendarButton;
    @FXML
    private Button deleteButton;

    private User currentUser;

    private AttendeeTicketsMenuController attendeeTicketsMenuController;

    private Event event;

    public void init(User user, Event event, AttendeeTicketsMenuController attendeeTicketsMenuController) {
        currentUser = user;
        this.event = event;
        this.attendeeTicketsMenuController = this.attendeeTicketsMenuController;

        idLabel.setText(String.format("%s", event.GetRoomID()));
        locationLabel.setText(event.GetEventTitle());

        if(event == null) {
            eventTitleLabel.setText("N/A");
            eventIDLabel.setText("N/A");
            eventCategoryLabel.setText("N/A");
            eventOrganizerLabel.setText("N/A");
            eventTimeRangeLabel.setText("N/A");
            eventPriceLabel.setText("N/A");
        }
        else {
            eventTitleLabel.setText(event.GetEventTitle());
            eventIDLabel.setText(String.format("%s", event.GetID()));
            eventCategoryLabel.setText(event.GetCategory().toString());
            eventOrganizerLabel.setText(event.GetOrganizer().GetUsername());
            eventTimeRangeLabel.setText(event.GetTimeRange().toString());
            eventPriceLabel.setText(String.format("$%s", event.GetPrice()));
        }




    }



    @FXML
    void ButtonHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #5F6368;");
    }

    @FXML
    void ButtonNotHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: transparent;");
    }

    @FXML
    void LabelHovered(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-font-weight: bold; -fx-underline: true;");
    }

    @FXML
    void LabelNotHovered(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-font-weight: normal; -fx-underline: false;");
    }

    @FXML
    void calendarPressed(MouseEvent event) {
        System.out.println("Calendar Pressed");
    }
}
