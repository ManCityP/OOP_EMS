package com.fhm.oop_ems;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label timeLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ImageView imageView;
    @FXML
    private AnchorPane anchorPane;

    private Timeline timeline;
    private float progress = 0.0f;
    private int durationInSeconds = 8;

    public void initialize() {
    }

    @FXML
    protected void onHelloButtonClick() {
        if(timeline != null)
            timeline.stop();
        progress = 0;
        imageView.setVisible(false);
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> UpdateProgress()));
        timeline.setCycleCount(durationInSeconds * 10);
        timeline.play();
        //welcomeText.setText("Welcome to JavaFX Application!");
    }

    private void UpdateProgress() {
        progress += (1.0f/ (durationInSeconds * 10.0f));
        progressBar.setProgress(progress);
        float percentage = progress * 100;
        timeLabel.setText(String.format("Progress: %.2f", percentage));
        //System.out.println(progress);
        if(Math.round(progress * 100.0f) / 100.0f >= 1.0f) {
            timeline.stop();
            welcomeText.setText("Done!");
            imageView.setVisible(true);
        }
    }
}