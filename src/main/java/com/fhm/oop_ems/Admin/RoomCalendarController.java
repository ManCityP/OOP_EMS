package com.fhm.oop_ems.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import p1.Database;
import p1.Day;
import p1.MyDate;
import p1.Room;
import p2.Event;
import p3.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomCalendarController {

    @FXML
    private Button backButton;
    @FXML
    private FlowPane calendar;
    @FXML
    private Button chatButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Text month;
    @FXML
    private Button refreshButton;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label username;
    @FXML
    private Text year;

    private User currentUser;
    private MyDate dateFocus;
    private MyDate today;
    private Room currentRoom;
    private ArrayList<Event> filteredEvents;

    public void Init(User user, Room room, ArrayList<Event> filteredEvents) {
        try {
            this.currentUser = user;
            this.currentRoom = room;
            this.filteredEvents = filteredEvents;
            this.today = new MyDate();
            this.dateFocus = new MyDate(1, today.GetMonth(), today.GetYear());
            drawCalendar();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.GetYear()));
        month.setText(String.valueOf(dateFocus.GetMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        //List of activities for a given month
        Map<Integer, ArrayList<Event>> monthEvents = GetMonthEvents(dateFocus);

        //TODO
        int monthMaxDate = 28;

        int n = Day.days.get(MyDate.GetDayOfTheWeek(dateFocus).toString());
        int dateOffset = (n == 0 ? 7 : n);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        ArrayList<Event> calendarActivities = monthEvents.get(currentDate);
                        if (calendarActivities != null) {
                            CreateMonthEvent(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                        rectangle.setOnMouseClicked(e -> {
                            try {
                                System.out.println(String.format("%s Pressed!", new MyDate(currentDate, dateFocus.GetMonth(), dateFocus.GetYear())));
                            }
                            catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        });
                    }
                    if (today.GetYear() == dateFocus.GetYear() && today.GetMonth() == dateFocus.GetMonth() && today.GetDay() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }

        }
    }

    private void CreateMonthEvent(ArrayList<Event> events, double height, double width, StackPane stackPane) {
        VBox monthEventsBox = new VBox();
        for (int k = 0; k < events.size(); k++) {
            if(k >= 2) {
                Text moreActivities = new Text("...");
                monthEventsBox.getChildren().add(moreActivities);
                break;
            }
            Text text = new Text(events.get(k).GetID() + ": " + events.get(k).GetTimeRange());
            monthEventsBox.getChildren().add(text);
        }
        monthEventsBox.setTranslateY((height / 2) * 0.20);
        monthEventsBox.setMaxWidth(width * 0.8);
        monthEventsBox.setMaxHeight(height * 0.65);
        monthEventsBox.setStyle("-fx-background-color:GRAY");
        monthEventsBox.setMouseTransparent(true);
        stackPane.getChildren().add(monthEventsBox);
    }

    private Map<Integer, ArrayList<Event>> GetMonthEvents(MyDate date) {
        Map<Integer, ArrayList<Event>> monthEvents = new HashMap<>();

        for (Event event : this.filteredEvents) {
            if(event.GetDate().GetYear() == date.GetYear() && event.GetDate().GetMonth() == date.GetMonth()) {
                if(!monthEvents.containsKey(event.GetDate().GetDay()))
                    monthEvents.put(event.GetDate().GetDay(), new ArrayList<>(List.of(event)));
                else
                    monthEvents.get(event.GetDate().GetDay()).add(event);
            }
        }
        return monthEvents;
    }

    @FXML
    void BackPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminRoomMenu.fxml"));
            Parent root = loader.load();

            AdminRoomMenuController adminRoomMenuController = loader.getController();
            adminRoomMenuController.InitData(currentUser, Database.GetRooms());

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
    void ButtonHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #5F6368;");
    }

    @FXML
    void ButtonNotHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: transparent;");
    }

    @FXML
    void ChatPressed() {
        System.out.println("Chat pressed!");
    }

    @FXML
    void DashboardPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminMainMenu.fxml"));
            Parent root = loader.load();

            AdminMainMenuController adminMainMenuController = loader.getController();
            adminMainMenuController.InitData(currentUser);

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
    void HandleKeyPress(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            BackPressed();
        }
    }

    @FXML
    void RefreshPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomCalendarController.fxml"));
            Parent root = loader.load();

            RoomCalendarController roomCalendarController = loader.getController();
            roomCalendarController.Init(currentUser, this.currentRoom, Database.GetEvents());

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
    void BackOneMonth() {
        try {
            dateFocus = dateFocus.LastMonth();
            calendar.getChildren().clear();
            drawCalendar();
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void ForwardOneMonth() {
        try {
            dateFocus = dateFocus.NextMonth();
            calendar.getChildren().clear();
            drawCalendar();
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
