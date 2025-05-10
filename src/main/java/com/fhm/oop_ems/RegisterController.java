package com.fhm.oop_ems;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import p1.*;
import p2.Organizer;
import p3.Attendee;
import p3.Gender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RegisterController {

    @FXML
    private Button addInterestButton;
    @FXML
    private Button addTimeButton;
    @FXML
    private AnchorPane adminPane;
    @FXML
    private AnchorPane attendeePane;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField emailField;
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
    private Label errorText;
    @FXML
    private AnchorPane forAllPane;
    @FXML
    private ComboBox<String> genderBox;
    @FXML
    private ComboBox<String> interestsBox;
    @FXML
    private Label interestsLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button registerButton;
    @FXML
    private Button removeInterestButton;
    @FXML
    private Button removeTimeButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField roleField;
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
    private ComboBox<String> dayComboBox;
    @FXML
    private ComboBox<String> userTypeBox;
    @FXML
    private TextField usernameField;
    @FXML
    private GridPane workingHoursGrid;

    private Hours workingHours;
    private ArrayList<String> interests;

    public void Init() {
        workingHours = new Hours(new LinkedHashMap<>());
        interests = new ArrayList<>(List.of());
        dayComboBox.setItems(FXCollections.observableArrayList(new ArrayList<>(List.of("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"))));
        userTypeBox.setItems(FXCollections.observableArrayList(new ArrayList<>(List.of("Attendee", "Organizer", "Admin"))));
        genderBox.setItems(FXCollections.observableArrayList(new ArrayList<>(List.of("Male", "Female"))));
        try {
            ArrayList<String> categories = Database.GetCategories();
            categories.remove("Default");
            interestsBox.setItems(FXCollections.observableArrayList(categories));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        userTypeBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("Admin")) {
                attendeePane.setVisible(false);
                attendeePane.setDisable(true);
                adminPane.setVisible(true);
                adminPane.setDisable(false);
            }
            else if(newValue.equals("Organizer")) {
                attendeePane.setVisible(false);
                attendeePane.setDisable(true);
                adminPane.setVisible(false);
                adminPane.setDisable(true);
            }
            else if(newValue.equals("Attendee")) {
                attendeePane.setVisible(true);
                attendeePane.setDisable(false);
                adminPane.setVisible(false);
                adminPane.setDisable(true);
            }
        });

        TextField[] fields = {startHourTextField, startMinuteTextField, endHourTextField, endMinuteTextField};
        for (TextField field : fields) {
            field.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.W || event.getCode() == KeyCode.S ||
                        event.getCode() == KeyCode.COMMA || event.getCode() == KeyCode.PERIOD || event.getCode() == KeyCode.D) {
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
    void ButtonHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #5F6368;");
    }

    @FXML
    void ButtonNotHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: transparent;");
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
        if(code == KeyCode.ENTER)
            Login();
        else if(code == KeyCode.D)
            if(dayComboBox.getSelectionModel().isSelected(6))
                dayComboBox.getSelectionModel().selectFirst();
            else
                dayComboBox.getSelectionModel().selectNext();
        else if(code == KeyCode.PERIOD)
            AddTimePressed();
        else if(code == KeyCode.COMMA)
            RemoveTimePressed();
        else if(code == KeyCode.TAB) {
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
        event.consume();
    }

    void UpdateMap() {
        for(Node node : workingHoursGrid.getChildren()) {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            if(row == null)
                row = 0;
            if(col == null)
                col = 0;

            if(row == 1 && node instanceof Label) {
                ArrayList<TimeRange> timeRanges = workingHours.GetMap().get(Day.Translate(col).toString());
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
    void AddInterestPressed() {
        try {
            if(interests.contains(interestsBox.getValue()))
                throw new Exception();
            interests.add(interestsBox.getValue());
            ArrayList<Category> categories = new ArrayList<>();
            for(String str : interests)
                categories.add(new Category(str));
            this.interestsLabel.setText(Attendee.strConvert(categories).toString());
        }
        catch (Exception ex) {
            System.out.println("Could not add category!");
        }
    }

    @FXML
    void RemoveInterestPressed() {
        try {
            if(!interests.contains(interestsBox.getValue()))
                throw new Exception();
            interests.remove(interestsBox.getValue());
            ArrayList<Category> categories = new ArrayList<>();
            for(String str : interests)
                categories.add(new Category(str));
            this.interestsLabel.setText(Attendee.strConvert(categories).toString());
        }
        catch (Exception ex) {
            System.out.println("Could not remove category!");
        }
    }

    @FXML
    void AddTimePressed() {
        errorText.setText("");
        try {
            TimeRange timeRange = new TimeRange(String.format("%s:%s", startHourTextField.getText(), startMinuteTextField.getText()),
                    String.format("%s:%s", endHourTextField.getText(), endMinuteTextField.getText()));
            workingHours.AddTime((String) dayComboBox.getValue(), timeRange);
            UpdateMap();
        }
        catch (Exception ex) {
            errorText.setText("Unable to add the specified time!");
        }
    }

    @FXML
    void RemoveTimePressed() {
        errorText.setText("");
        try {
            TimeRange timeRange = new TimeRange(String.format("%s:%s", startHourTextField.getText(), startMinuteTextField.getText()),
                    String.format("%s:%s", endHourTextField.getText(), endMinuteTextField.getText()));
            workingHours.RemoveTime((String) dayComboBox.getValue(), timeRange);
            UpdateMap();
        }
        catch (Exception ex) {
            errorText.setText("Unable to remove the specified time!");
        }
    }

    @FXML
    void BackPressed() {
        try {
            System.out.println("logout button pressed");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage)backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void Login() {
        if(!passwordField.getText().equals(confirmPasswordField.getText())) {
            errorText.setText("Please confirm your password!");
            return;
        }
        try {
            LocalDate localDate = birthDatePicker.getValue();
            MyDate date1 = new MyDate(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
            if (userTypeBox.getValue().equals("Admin")) {
                Admin.RegisterAdmin(usernameField.getText(), emailField.getText(), passwordField.getText(), date1,
                                    genderBox.getValue().equals("Male")? Gender.MALE : Gender.FEMALE, roleField.getText(), workingHours);
                BackPressed();
            }
            else if (userTypeBox.getValue().equals("Organizer")) {
                Organizer.RegisterOrganizer(usernameField.getText(), emailField.getText(), passwordField.getText(), date1,
                        genderBox.getValue().equals("Male")? Gender.MALE : Gender.FEMALE, 0);
                BackPressed();
            }
            else if (userTypeBox.getValue().equals("Attendee")) {
                ArrayList<Category> categories = new ArrayList<>();
                for(String str : interests) {
                    categories.add(new Category(str));
                }
                Attendee.RegisterAttendee(usernameField.getText(), emailField.getText(), passwordField.getText(), date1,
                        genderBox.getValue().equals("Male")? Gender.MALE : Gender.FEMALE, categories, 0);
                BackPressed();
            }
            else {
                errorText.setText("Choose a user type!");
            }
        }
        catch (Exception ex) {
            errorText.setText("Could not create user!");
        }
    }
}
