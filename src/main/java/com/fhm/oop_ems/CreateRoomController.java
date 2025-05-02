package com.fhm.oop_ems;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import p1.Admin;
import p1.Day;
import p1.Hours;
import p1.TimeRange;
import p3.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CreateRoomController {
    @FXML
    private Button addTimeButton;
    @FXML
    private Button removeTimeButton;
    @FXML
    private GridPane availableHoursGrid;
    @FXML
    private Button backButton;
    @FXML
    private TextField capacityField;
    @FXML
    private Button chatButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private ComboBox<?> dayComboBox;
    @FXML
    private Button endHourDownButton;
    @FXML
    private TextField endHourTextField;
    @FXML
    private Button endHourUpButton;
    @FXML
    private Button endMinuteDownButton;
    @FXML
    private TextField endMinuteTextField;
    @FXML
    private Button endMinuteUpButton;
    @FXML
    private TextField locationField;
    @FXML
    private Button refreshButton;
    @FXML
    private Button startHourDownButton;
    @FXML
    private TextField startHourTextField;
    @FXML
    private Button startHourUpButton;
    @FXML
    private Button startMinuteDownButton;
    @FXML
    private TextField startMinuteTextField;
    @FXML
    private Button startMinuteUpButton;
    @FXML
    private Button createButton;
    @FXML
    private Label username;
    @FXML
    private Label errorLabel;

    private Hours availableHours;

    private User currentUser;

    public void Init(User user) {
        currentUser = user;
        availableHours = new Hours(new LinkedHashMap<>());

        locationField.setTextFormatter(new TextFormatter<>(change -> {
            return change.getControlNewText().length() <= 128 ? change : null;
        }));

        capacityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty())
                capacityField.setText("0");
            else if(!newValue.matches("\\d*"))
                capacityField.setText(oldValue.isEmpty()? "0" : oldValue);
            else {
                try {
                    int x = Integer.parseInt(newValue);
                    capacityField.setText(String.format("%s", x));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    capacityField.setText(oldValue.isEmpty()? "0" : oldValue);
                }
            }
        });

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
    void HandleKeyPress(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            BackPressed();
        }
    }

    @FXML
    void CreatePressed() {
        errorLabel.setText("");
        try {
            ((Admin) currentUser).CreateRoom(availableHours, locationField.getText(), Integer.parseInt(capacityField.getText()));
            BackPressed();
        }
        catch (NumberFormatException ex) {
            errorLabel.setText("Invalid Capacity!");
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void AddTimePressed() {
        errorLabel.setText("");
        try {
            TimeRange timeRange = new TimeRange(String.format("%s:%s", startHourTextField.getText(), startMinuteTextField.getText()),
                                                String.format("%s:%s", endHourTextField.getText(), endMinuteTextField.getText()));
            availableHours.AddTime((String) dayComboBox.getValue(), timeRange);
            UpdateMap();
        }
        catch (Exception ex) {
            errorLabel.setText("Unable to add the specified time!");
        }
    }

    @FXML
    void RemoveTimePressed() {
        errorLabel.setText("");
        try {
            TimeRange timeRange = new TimeRange(String.format("%s:%s", startHourTextField.getText(), startMinuteTextField.getText()),
                                                String.format("%s:%s", endHourTextField.getText(), endMinuteTextField.getText()));
            availableHours.RemoveTime((String) dayComboBox.getValue(), timeRange);
            UpdateMap();
        }
        catch (Exception ex) {
            errorLabel.setText("Unable to remove the specified time!");
        }
    }

    void UpdateMap() {
        for(Node node : availableHoursGrid.getChildren()) {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            if(row == null)
                row = 0;
            if(col == null)
                col = 0;

            if(row == 1 && node instanceof Label) {
                ArrayList<TimeRange> timeRanges = availableHours.GetMap().get(Day.Translate(col).toString());
                if(timeRanges == null || timeRanges.isEmpty()) {
                    ((Label) node).setText("N/A");
                }
                else {
                    StringBuilder time = new StringBuilder();
                    for (TimeRange tr : timeRanges) {
                        time.append(tr.toString() + '\n');
                    }
                    if (!time.isEmpty())
                        time.delete(time.length() - 1, time.length());
                    ((Label) node).setText(time.toString());
                }
            }
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
    void ChatPressed() {
        System.out.println("Chat Pressed!");
    }

    @FXML
    void RefreshPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateRoomMenu.fxml"));
            Parent root = loader.load();

            CreateRoomController createRoomController = loader.getController();
            createRoomController.Init(currentUser);

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
    void BackPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminRoomMenu.fxml"));
            Parent root = loader.load();

            AdminRoomMenuController adminRoomMenuController = loader.getController();
            adminRoomMenuController.InitData(currentUser);

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
    void ButtonHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #5F6368;");
    }

    @FXML
    void ButtonNotHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: transparent;");
    }
}
