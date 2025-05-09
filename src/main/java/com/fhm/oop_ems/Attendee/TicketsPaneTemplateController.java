package com.fhm.oop_ems.Attendee;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import p2.Event;
import p3.Attendee;
import p3.User;

import java.util.ArrayList;

import static p1.Room.FindRoom;

public class TicketsPaneTemplateController {
    @FXML
    private Label eventStatus;
    @FXML
    private Label inStock;
    @FXML
    private Label eventDate;
    @FXML
    private Label maxAttendees;
    @FXML
    private Label currentAttendees;
    @FXML
    private Label currentTickets;
    @FXML
    private Button buyButton;
    @FXML
    private Button refundButton;
    @FXML
    private Label eventCategoryLabel;
    @FXML
    private Label eventIDLabel;
    @FXML
    private Label eventOrganizerLabel;
    @FXML
    private Label eventPriceLabel;
    @FXML
    private Label eventTimeRangeLabel;
    @FXML
    private Label eventTitleLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label reservedCalendarButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Spinner<Integer> spinner;
    SpinnerValueFactory<Integer> valueFactory;
    @FXML
    public void initialize() {
        if(event1 !=null) {
            // initialize teh valuefactry
            valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, MaxVar, 1); // min, max, initial
            spinner.setValueFactory(valueFactory);
        }
        else{
            valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1); // min, max, initial
            spinner.setValueFactory(valueFactory);
        }
        spinner.valueProperty().addListener((obs, oldVal, newVal) -> DynamicPrice());
    }

    public void updateMaxVar(int newMax) {
        if(event1 !=null) {
            MaxVar = newMax;

            SpinnerValueFactory<Integer> valueFactory = spinner.getValueFactory();
            if (valueFactory instanceof SpinnerValueFactory.IntegerSpinnerValueFactory) {
                SpinnerValueFactory.IntegerSpinnerValueFactory intFactory =
                        (SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory;
                intFactory.setMax(MaxVar);

                //out of bounds handing
                int currentValue = spinner.getValue();
                if (currentValue > MaxVar) {
                    intFactory.setValue(MaxVar);
                } else if (currentValue < 1) {
                    intFactory.setValue(1);
                }
            }
        }
    }


    private User currentUser;

    private AttendeeTicketsMenuController attendeeTicketsMenuController;

    private Event event1;
    //TODO max var
    private int MaxVar;


    public void init(User user, Event event, AttendeeTicketsMenuController attendeeTicketsMenuController) {
        currentUser = user;
        this.event1 = event;
        this.attendeeTicketsMenuController = attendeeTicketsMenuController;
        this.MaxVar = event.GetMaxNumOfAttendees();
        idLabel.setText(String.format("%s", event.GetRoomID()));
        try {
            locationLabel.setText(FindRoom(attendeeTicketsMenuController.rooms, event.GetRoomID()).GetLocation());
        } catch (Exception e) {
        e.printStackTrace();
        }
        if(event == null) {
            eventTitleLabel.setText("N/A");
            eventIDLabel.setText("N/A");
            eventCategoryLabel.setText("N/A");
            eventOrganizerLabel.setText("N/A");
            eventTimeRangeLabel.setText("N/A");
            eventPriceLabel.setText("N/A");
            eventDate.setText("N/A");
        }
        else {
            eventStatus.setText(event.GetStatus().toString());
            eventTitleLabel.setText(event.GetEventTitle());
            eventIDLabel.setText(String.format("%s", event.GetID()));
            eventCategoryLabel.setText(event.GetCategory().toString());
            eventOrganizerLabel.setText(event.GetOrganizer().GetUsername());
            eventTimeRangeLabel.setText(event.GetTimeRange().toString());
            eventPriceLabel.setText(String.format("$%s", event.GetPrice()));
            eventDate.setText(event.GetDate().toString());
            if(((Attendee)currentUser).GetTickets().containsKey(event.GetID())){
                this.currentTickets.setText(((Attendee)currentUser).GetTickets().get(event.GetID()).toString());
            }
            if(event.GetOrganizer().GetTicketsSold().containsKey(event.GetID())) {
                currentAttendees.setText(event.GetOrganizer().GetTicketsSold().get(event.GetID()).toString());
                updateMaxVar(event.GetMaxNumOfAttendees() - event.GetOrganizer().GetTicketsSold().get(event.GetID()));
            }
            else updateMaxVar(this.MaxVar);
            maxAttendees.setText(""+event.GetMaxNumOfAttendees());
            inStock.setText(String.valueOf((Integer)MaxVar));
        }
    }
private void DynamicPrice(){
    eventPriceLabel.setText(String.format("$%s", this.event1.GetPrice()*spinner.getValue()));
}

    @FXML
    void buyButton(MouseEvent event){
        try {
            System.out.println("buy button" + spinner.getValue());
            ((Attendee) currentUser).PurchaseEvent(event1, spinner.getValue());
        this.attendeeTicketsMenuController.RefreshPressed();
        } catch (Exception e) {
            e.printStackTrace();
            this.attendeeTicketsMenuController.errorText.setText(e.getMessage());
        }
    }
    @FXML
    void refundButton(MouseEvent event){
        try {
            System.out.println("buy button" + spinner.getValue());
            ((Attendee) currentUser).RefundTicket(event1, spinner.getValue());
            this.attendeeTicketsMenuController.RefreshPressed();
        } catch (Exception e) {
            e.printStackTrace();
            this.attendeeTicketsMenuController.errorText.setText(e.getMessage());
        }
    }
    @FXML
    void ButtonHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #121212; -fx-border-color: #ffff00; -fx-border-width: 4px; -fx-text-fill: #ffff00");
    }

    @FXML
    void ButtonNotHovered(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #121212; -fx-border-color: #ff7500; -fx-border-width: 4px;");
    }

    @FXML
    void LabelHovered(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-font-weight: bold; -fx-underline: true;");
    }

    @FXML
    void LabelNotHovered(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-font-weight: normal; -fx-underline: false;");
    }

    @FXML
    void calendarPressed(MouseEvent event) {
        System.out.println("Calendar Pressed");
    }
}
