package com.fhm.oop_ems.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import p1.Admin;
import p3.User;

public class CategoryPaneTemplateController {

    @FXML
    private Label categoryLabel;
    @FXML
    private Button deleteButton;

    private String category;
    private User currentUser;
    private AdminCategoryMenuController adminCategoryMenuController;

    public void Init(User user, String category, AdminCategoryMenuController adminCategoryMenuController) {
        this.category = category;
        this.currentUser = user;
        this.categoryLabel.setText(category);
        this.adminCategoryMenuController = adminCategoryMenuController;
        if(category.equals("Default"))
            deleteButton.setDisable(true);
    }

    @FXML
    void DeletePressed() {
        try {
            ((Admin) currentUser).RemoveCategory(category);
            this.adminCategoryMenuController.RefreshPressed();
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
