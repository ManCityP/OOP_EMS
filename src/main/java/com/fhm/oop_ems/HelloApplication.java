package com.fhm.oop_ems;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import p1.Database;
import p1.Day;
import p2.Organizer;

import java.io.IOException;

//Test Commit

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        Day.Init();
        Database.Connect();
        launch();
        Database.CloseConnection();
    }
}

/*
fris:
Attendee;

Ahmed:
Organizer;

ManCity player:
Admin;*/
