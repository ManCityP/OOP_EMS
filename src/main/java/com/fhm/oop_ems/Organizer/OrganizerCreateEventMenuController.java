package com.fhm.oop_ems.Organizer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import p1.*;
import p2.Organizer;
import p3.User;

public class OrganizerCreateEventMenuController {

    @FXML
    private Button backButton;
    @FXML
    private ComboBox<String> categories;
    @FXML
    private TextField day;
    @FXML
    private TextField endHourTextField;
    @FXML
    private TextField endMinuteTextField;
    @FXML
    private TextField eventTitle;
    @FXML
    private TextField maxNumOfAttendees;
    @FXML
    private TextField month;
    @FXML
    private TextField price;
    @FXML
    private ComboBox<Room> rooms;
    @FXML
    private TextField startHourTextField;
    @FXML
    private TextField startMinuteTextField;
    @FXML
    private Label username;
    @FXML
    private TextField year;

    private User user;
    private Room room;
    private Category category;

    @FXML
    void Init(User user) {
        try{
            this.user = user;
            username.setText(this.user.GetUsername());
            categories.setItems(FXCollections.observableArrayList(Database.GetCategories()));
            rooms.setItems(FXCollections.observableArrayList(Database.GetRooms()));
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void CreatePressed() throws Exception {
        for(Room room : Database.GetRooms())
            if(room.toString().equals(rooms.getValue().toString()))
                this.room = room;

        for(String category : Database.GetCategories())
            if(category.equals(categories.getValue()))
                this.category = new Category(category);

        ((Organizer)this.user).CreateEvent(Double.parseDouble(price.getText()), this.room, eventTitle.getText(), this.category , new MyDate(day.getText() + "/" + month.getText() + "/" + year.getText()), new TimeRange(String.format("%s:%s", startHourTextField.getText(), startMinuteTextField.getText()), String.format("%s:%s", endHourTextField.getText(), endMinuteTextField.getText())), Integer.parseInt(maxNumOfAttendees.getText()));
    }

    @FXML
    void StartHourUpPressed() {
        int hour = Integer.parseInt(startHourTextField.getText());
        if(hour == 23)
            startHourTextField.setText("00");
        else
            startHourTextField.setText(String.format("%s", ++hour));
    }

    @FXML
    void StartHourDownPressed() {
        int hour = Integer.parseInt(startHourTextField.getText());
        if(hour == 0)
            startHourTextField.setText("23");
        else
            startHourTextField.setText(String.format("%s", --hour));
    }

    @FXML
    void StartMinuteUpPressed() {
        int min = Integer.parseInt(startMinuteTextField.getText());
        if(min == 59)
            startMinuteTextField.setText("00");
        else
            startMinuteTextField.setText(String.format("%s", ++min));
    }

    @FXML
    void StartMinuteDownPressed() {
        int min = Integer.parseInt(startMinuteTextField.getText());
        if(min == 0)
            startMinuteTextField.setText("59");
        else
            startMinuteTextField.setText(String.format("%s", --min));
    }

    @FXML
    void EndHourUpPressed() {
        int hour = Integer.parseInt(endHourTextField.getText());
        if(hour == 23)
            endHourTextField.setText("00");
        else
            endHourTextField.setText(String.format("%s", ++hour));
    }

    @FXML
    void EndHourDownPressed() {
        int hour = Integer.parseInt(endHourTextField.getText());
        if(hour == 0)
            endHourTextField.setText("23");
        else
            endHourTextField.setText(String.format("%s", --hour));
    }

    @FXML
    void EndMinuteUpPressed() {
        int min = Integer.parseInt(endMinuteTextField.getText());
        if(min == 59)
            endMinuteTextField.setText("00");
        else
            endMinuteTextField.setText(String.format("%s", ++min));
    }

    @FXML
    void EndMinuteDownPressed() {
        int min = Integer.parseInt(endMinuteTextField.getText());
        if(min == 0)
            endMinuteTextField.setText("59");
        else
            endMinuteTextField.setText(String.format("%s", --min));
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
    void DashboardPressed() {
        BackButtonPressed();
    }

}
