package com.fhm.oop_ems.Organizer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import p1.Database;
import p2.Event;
import p2.Organizer;
import p3.User;

public class EventPaneTemplateController {
    @FXML
    private Label category;
    @FXML
    private Label date;
    @FXML
    private Label eventID;
    @FXML
    private Label eventTitle;
    @FXML
    private Label maxNumOfAttendees;
    @FXML
    private Label price;
    @FXML
    private Label status;
    @FXML
    private Label timeRange;
    @FXML
    private Label ticketsSold;
    @FXML
    private Label roomID;

    private Event event;
    private User user;

    public void displayevent(User user, Event event){
       try{
           this.user = user;
           this.event = event;
           eventTitle.setText(event.GetEventTitle());
           eventID.setText(String.format("%s",event.GetID()));
           category.setText(event.GetCategory().toString());
           maxNumOfAttendees.setText(String.format("%s",event.GetMaxNumOfAttendees()));
           price.setText(String.format("%s",event.GetPrice()));
           date.setText(event.GetDate().toString());
           timeRange.setText(event.GetTimeRange().toString());
           status.setText(event.GetStatus().toString());
           roomID.setText(String.format("%s",event.GetRoomID()));
           if(((Organizer)user).GetTicketsSold().containsKey(event.GetID())){
               ticketsSold.setText(String.format("%s", ((Organizer)user).GetTicketsSold().get(event.GetID())));
           }else
               ticketsSold.setText("0");
       } catch (Exception ex) {
           System.out.println(ex.getMessage());;
       }
    }

    @FXML
    private void DeleteEvent(){
        try{
            ((Organizer)this.user).CancelEvent(this.event);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerEventMenu.fxml"));
            Parent root = loader.load();

            OrganizerEventMenuController organizerEventMenuController = loader.getController();
            organizerEventMenuController.InitData(user, Database.GetEvents());

            Scene scene2 = new Scene(root);
            Stage stage = (Stage)category.getScene().getWindow();
            stage.setScene(scene2);
            System.out.println("events menu updated");

        }catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
