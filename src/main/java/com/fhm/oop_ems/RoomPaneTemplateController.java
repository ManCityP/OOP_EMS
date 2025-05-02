package com.fhm.oop_ems;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import p1.Day;
import p1.Room;
import p1.TimeRange;
import p2.Event;

import java.util.ArrayList;
import java.util.Map;

public class RoomPaneTemplateController {

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

    private Room room;

    public void init(Room room) {
        this.room = room;

        idLabel.setText(String.format("%s", room.GetID()));
        locationLabel.setText(room.GetLocation());
        Event currentEvent = room.GetCurrentEvent();
        if(currentEvent == null) {
            eventTitleLabel.setText("N/A");
            eventIDLabel.setText("N/A");
            eventCategoryLabel.setText("N/A");
            eventOrganizerLabel.setText("N/A");
            eventTimeRangeLabel.setText("N/A");
            eventPriceLabel.setText("N/A");
        }
        else {
            eventTitleLabel.setText(currentEvent.GetEventTitle());
            eventIDLabel.setText(String.format("%s", currentEvent.GetID()));
            eventCategoryLabel.setText(currentEvent.GetCategory().toString());
            eventOrganizerLabel.setText(currentEvent.GetOrganizer().GetUsername());
            eventTimeRangeLabel.setText(currentEvent.GetTimeRange().toString());
            eventPriceLabel.setText(String.format("$%s", currentEvent.GetPrice()));
        }

        Map<String, ArrayList<TimeRange>> availableHours = room.GetAvailableHours().GetMap();

        for(Node node : availableHoursGrid.getChildren()) {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            if(row == null)
                row = 0;
            if(col == null)
                col = 0;

            if(row == 1 && node instanceof Label) {
                ArrayList<TimeRange> timeRanges = availableHours.get(Day.Translate(col).toString());
                if(timeRanges == null) {
                    ((Label) node).setText("N/A");
                }
                else {
                    StringBuilder time = new StringBuilder();
                    for (TimeRange timeRange : timeRanges) {
                        time.append(timeRange.toString() + '\n');
                    }
                    if (!time.isEmpty())
                        time.delete(time.length() - 1, time.length());
                    ((Label) node).setText(time.toString());
                }
            }
        }
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
