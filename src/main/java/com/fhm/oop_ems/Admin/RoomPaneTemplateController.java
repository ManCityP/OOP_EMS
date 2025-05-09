package com.fhm.oop_ems.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import p1.*;
import p2.Event;
import p3.User;

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
    @FXML
    private Label capacityLabel;
    @FXML
    private Button deleteButton;

    private User currentUser;

    private AdminRoomMenuController adminRoomMenuController;

    private Room room;

    public void init(User user, Room room, AdminRoomMenuController adminRoomMenuController) {
        currentUser = user;
        this.room = room;
        this.adminRoomMenuController = adminRoomMenuController;

        idLabel.setText(String.format("%s", room.GetID()));
        locationLabel.setText(room.GetLocation());
        capacityLabel.setText(String.format("%s", room.GetMaxCapacity()));
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
                if(timeRanges == null || timeRanges.isEmpty()) {
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
    void DeletePressed() {
        try {
            ((Admin) currentUser).DeleteRoom(room);
            adminRoomMenuController.RefreshPressed();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
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
    void CalendarPressed(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomCalendar.fxml"));
            Parent root = loader.load();

            RoomCalendarController roomCalendarController = loader.getController();
            roomCalendarController.Init(currentUser, this.room, Database.GetEvents());

            // Create the second scene
            Scene scene2 = new Scene(root);

            // Get the current stage
            Stage stage = (Stage)reservedCalendarButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
