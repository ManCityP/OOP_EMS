package com.fhm.oop_ems.Organizer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import p1.*;
import p2.Organizer;
import p3.User;

import java.util.ArrayList;

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
    @FXML
    private Label errorMessage;
    @FXML
    private DatePicker date;

    private User user;
    private Room room;
    private Category category;
    private ArrayList<String> categoryList ;
    private ArrayList<Room> roomList;

    @FXML
    void Init(User user) {
        try{
            this.user = user;
            username.setText(this.user.GetUsername());
            categoryList = Database.GetCategories();
            roomList = Database.GetRooms();
            categories.setItems(FXCollections.observableArrayList(categoryList));
            rooms.setItems(FXCollections.observableArrayList(roomList));
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        maxNumOfAttendees.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty())
                maxNumOfAttendees.setText("0");
            else if(!newValue.matches("\\d*"))
                maxNumOfAttendees.setText(oldValue.isEmpty()? "0" : oldValue);
            else {
                try {
                    int x = Integer.parseInt(newValue);
                    maxNumOfAttendees.setText(String.format("%s", x));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    maxNumOfAttendees.setText(oldValue.isEmpty()? "0" : oldValue);
                }
            }
        });
        price.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty())
                price.setText("0");
            else if(!newValue.matches("\\d*"))
                price.setText(oldValue.isEmpty()? "0" : oldValue);
            else {
                try {
                    int x = Integer.parseInt(newValue);
                    price.setText(String.format("%s", x));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    price.setText(oldValue.isEmpty()? "0" : oldValue);
                }
            }
        });

        TextField[] fields = {startHourTextField, startMinuteTextField, endHourTextField, endMinuteTextField};
        for (TextField field : fields) {
            field.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.W || event.getCode() == KeyCode.S) {
                    HandleKeyPress(event);
                    event.consume();
                }
            });
        }

        try {
            startHourTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*") || newValue.isEmpty()) {
                    startHourTextField.setText(oldValue); // Revert to the old value if the input is not numeric
                } else {
                    int h = Integer.parseInt(newValue);
                    if (h < 0 || h >= 24)
                        startHourTextField.setText(oldValue);
                    else
                        startHourTextField.setText(String.format("%02d", h));
                }
            });
        }
        catch (Exception ex) {
            startHourTextField.setText("00");
        }

        try {
            endHourTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*") || newValue.isEmpty()) {
                    endHourTextField.setText(oldValue); // Revert to the old value if the input is not numeric
                } else {
                    int h = Integer.parseInt(newValue);
                    if (h < 0 || h >= 24)
                        endHourTextField.setText(oldValue);
                    else
                        endHourTextField.setText(String.format("%02d", h));
                }
            });
        }
        catch (Exception ex) {
            endHourTextField.setText("00");
        }

        try {
            startMinuteTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*") || newValue.isEmpty()) {
                    startMinuteTextField.setText(oldValue); // Revert to the old value if the input is not numeric
                } else {
                    int m = Integer.parseInt(newValue);
                    if (m < 0 || m >= 60)
                        startMinuteTextField.setText(oldValue);
                    else
                        startMinuteTextField.setText(String.format("%02d", m));
                }
            });
        }
        catch (Exception ex) {
            startMinuteTextField.setText("00");
        }

        try {
            endMinuteTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*") || newValue.isEmpty()) {
                    endMinuteTextField.setText(oldValue); // Revert to the old value if the input is not numeric
                } else {
                    int m = Integer.parseInt(newValue);
                    if (m < 0 || m >= 60)
                        endMinuteTextField.setText(oldValue);
                    else
                        endMinuteTextField.setText(String.format("%02d", m));
                }
            });
        }
        catch (Exception ex) {
            endMinuteTextField.setText("00");
        }

    }

    @FXML
    private void CreatePressed() {
        try{
            for(Room room : roomList)
                if(room.toString().equals(rooms.getValue().toString()))
                    this.room = room;

            for(String category : categoryList)
                if(category.equals(categories.getValue()))
                    this.category = new Category(category);

            String[] d = date.getEditor().getText().split("/");
            MyDate date1 = new MyDate(Integer.parseInt(d[1].trim()), Integer.parseInt(d[0].trim()), Integer.parseInt(d[2].trim()));
            System.out.println(date1);

            ((Organizer)this.user).CreateEvent(Double.parseDouble(price.getText()), this.room, eventTitle.getText(), this.category , new MyDate(date1.toString()), new TimeRange(String.format("%s:%s", startHourTextField.getText(), startMinuteTextField.getText()), String.format("%s:%s", endHourTextField.getText(), endMinuteTextField.getText())), Integer.parseInt(maxNumOfAttendees.getText()));

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

        }catch(Exception ex){
            System.out.println(ex.getMessage());
//            errorMessage.setText(ex.getMessage());
            errorMessage.setText("Invalid data !");
        }
    }

    @FXML
    private void ProfileButtonPressed(){
        try {
            System.out.println("Profile button pressed");
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
    void HandleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code == KeyCode.TAB) {
            if(startHourTextField.isFocused())
                startMinuteTextField.requestFocus();
            else if(startMinuteTextField.isFocused())
                endHourTextField.requestFocus();
            else if(endHourTextField.isFocused())
                endMinuteTextField.requestFocus();
            else
                startHourTextField.requestFocus();
        }
        else if(code == KeyCode.UP || code == KeyCode.W) {
            if(startHourTextField.isFocused())
                StartHourUpPressed();
            else if(startMinuteTextField.isFocused())
                StartMinuteUpPressed();
            else if(endHourTextField.isFocused())
                EndHourUpPressed();
            else if(endMinuteTextField.isFocused())
                EndMinuteUpPressed();
        }
        else if(code == KeyCode.DOWN || code == KeyCode.S) {
            if(startHourTextField.isFocused())
                StartHourDownPressed();
            else if(startMinuteTextField.isFocused())
                StartMinuteDownPressed();
            else if(endHourTextField.isFocused())
                EndHourDownPressed();
            else if(endMinuteTextField.isFocused())
                EndMinuteDownPressed();
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
    void DashboardPressed() {
        BackButtonPressed();
    }
}
