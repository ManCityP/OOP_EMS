package com.fhm.oop_ems.Organizer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import p2.Event;

public class EventPaneTemplateController {
    @FXML
    private Label category;
    @FXML
    private Label date;
    @FXML
    private Label eventID;
    @FXML
    private Label eventTitle;
    @FXML
    private Label maxNumOfAttendees;
    @FXML
    private Label price;
    @FXML
    private Label status;
    @FXML
    private Label timeRange;

    public void displayevent(Event event){
        eventTitle.setText(event.GetEventTitle());
        eventID.setText(String.format("%s",event.GetID()));
        category.setText(event.GetCategory().toString());
        maxNumOfAttendees.setText(String.format("%s",event.GetMaxNumOfAttendees()));
        price.setText(String.format("%s",event.GetPrice()));
        date.setText(event.GetDate().toString());
        timeRange.setText(event.GetTimeRange().toString());
        status.setText(event.GetStatus().toString());
    }
}
