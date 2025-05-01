package com.fhm.oop_ems;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class AdminMainMenuController {
    @FXML
    private Button categoriesButton;
    @FXML
    private Button chatButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button eventsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button roomsButton;
    @FXML
    private Label username;

    @FXML
    void ButtonHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #3053ba;");
    }

    @FXML
    void ButtonNotHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #885133;");
    }

    @FXML
    void CategoriesPressed() {
        System.out.println("Categories Menu loaded!");
    }

    @FXML
    void ChatPressed() {
        System.out.println("Chat loaded!");
    }

    @FXML
    void DashboardPressed() {
        System.out.println("Dashboard refreshed!");
    }

    @FXML
    void EventsPressed() {
        System.out.println("Events Menu loaded!");
    }

    @FXML
    void LogoutPressed() {
        System.out.println("Logged out!");
    }

    @FXML
    void ProfilePressed() {
        System.out.println("Profile Menu Loaded!");
    }

    @FXML
    void RoomsPressed() {
        System.out.println("Rooms Menu loaded!");
    }

}
