package com.fhm.oop_ems;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import p1.Database;
import p1.Day;


import java.io.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Alley OOP FAM");
        stage.getIcons().add(new Image(getClass().getResource("/image/Logo.png").toExternalForm()));
        stage.setScene(scene);
        //stage.setMinWidth(1092);
        //stage.setMinHeight(802);

        stage.setOnCloseRequest(windowEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("Click OK to exit Application!");
            alert.showAndWait().ifPresent(response -> {
                if(response.getText().equals("OK")) {
                    stage.close();
                }
                else {
                    windowEvent.consume();
                }
            });
        });

        stage.show();
    }

    public static void main(String[] args) {
        try {
            Day.Init();
            Database.Connect();

            launch();

            Database.CloseConnection();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
