package com.fhm.oop_ems.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import p2.Event;

public class EventPaneTemplateController {

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
    private Label maximumAttendeesLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label ticketsSoldLabel;

    public void Init(Event event) {
        eventTitleLabel.setText(event.GetEventTitle());
        eventIDLabel.setText(String.format("%s", event.GetID()));
        eventCategoryLabel.setText(event.GetCategory().toString());
        eventOrganizerLabel.setText(event.GetOrganizer().GetUsername());
        eventTimeRangeLabel.setText(event.GetTimeRange().toString());
        eventPriceLabel.setText(String.format("$%s", event.GetPrice()));
        statusLabel.setText(event.GetStatus().toString());
        maximumAttendeesLabel.setText(String.format("%s", event.GetMaxNumOfAttendees()));
        ticketsSoldLabel.setText(String.format("%s", event.GetOrganizer().GetTicketsSold().getOrDefault(event.GetID(), 0)));
    }
}
