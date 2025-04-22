package p2;

import p1.*;
import p1.MyDate;
import p3.Attendee;
import p3.Gender;
import p3.User;
import p3.Wallet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Organizer extends User {
    private final Wallet WALLET;
    private Map<Integer, Integer> ticketsSold = new HashMap<>();//Almost there

    public Organizer(String username, String email, String password, MyDate dob, Gender gender, Wallet wallet, Map<Integer, Integer> ticketsSold) throws Exception {
        super(username, email, password, dob, gender);
        if(wallet == null)
            throw new Exception("Invalid Wallet");
        this.WALLET = wallet;
        this.ticketsSold = ticketsSold;
    }

    public static void RegisterOrganizer(String username, String email, String password, MyDate dob, Gender gender, double balance) throws Exception {
        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            throw new Exception("Invalid Email format");
        if(email.length() > 128)
            throw new Exception("Email must be at most 128 characters");
        if(username.isEmpty() || username.length() > 32)
            throw new Exception("Username must be 1-32 characters");
        if(password.length() < 8 || password.length() > 32)
            throw new Exception("Password must be 8-32 characters long");
        Database.Execute(String.format("INSERT INTO user (username, email, password, birth_year, birth_month, birth_day, gender, type, tickets) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                username, email, password, dob.GetYear(), dob.GetMonth(), dob.GetDay(), gender, "Organizer", ""));
        Wallet.CreateWallet(username, balance);
    }

    public static Organizer FindOrganizer(ArrayList<Organizer> organizers, String username){
        for (Organizer organizer : organizers)
            if (organizer.username.equals(username))
                return organizer;

        return null;
    }

    public void CreateEvent(double price, Room room, String eventTitle, Category category, MyDate date, TimeRange timeRange, int maxNumOfAttendees) throws Exception {
        if (eventTitle == null)
            throw new Exception("Invalid Event title");
        if(price < 0)
            throw new Exception("Invalid ticket price");
        if (room == null)
            throw new Exception("Invalid room");
        if (maxNumOfAttendees < 0 || maxNumOfAttendees > room.GetMaxCapacity())
            throw new Exception("Invalid number of attendees");
        if (category == null)
            throw new Exception("Invalid category");
        if (date == null)
            throw new Exception("Invalid date");
        if (timeRange == null)
            throw new Exception("Invalid timerange");

        room.ReserveEvent(new Event(this, eventTitle, 0, maxNumOfAttendees, price, room.GetID(), category, date, timeRange));
        eventTitle = eventTitle.replace("'","\\'");
        Database.Execute(String.format("INSERT INTO event (username, price, category, room_id, date, time_range, title, maximum) " +
                                       "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                                       username, price, category.toString(), room.GetID(), date.toString(), timeRange.toString(), eventTitle, maxNumOfAttendees));
        System.out.println("Event added successfully");
    }

    //TODO read events

    public void EditEventPrice(Event event, double price) throws Exception{
        if(!(event.GetOrganizer().GetUsername().equals(this.username)))
            throw new Exception("This Event belongs to another organizer");
        event.EditPrice(price);
    }

    public void EditEventRoom(Event event, int roomID) throws Exception{
        if(!(event.GetOrganizer().GetUsername().equals(this.username)))
            throw new Exception("This Event belongs to another organizer");
        event.ChangeRoom(roomID);
    }

    public void EditEventCategory(Event event, Category category) throws Exception{
        if(!(event.GetOrganizer().GetUsername().equals(this.username)))
            throw new Exception("This Event belongs to another organizer");
        event.EditCategory(category);
    }

    public void EditEventDate(Event event, MyDate date) throws Exception {
        if(!(event.GetOrganizer().GetUsername().equals(this.username)))
            throw new Exception("This Event belongs to another organizer");
        event.EditDate(date);
    }

    public void CancelEvent(Event event) throws Exception {
        Database.Execute(String.format("DELETE FROM event WHERE id = '%s'", event.GetID()));
        System.out.println("Event deleted successfully");
        for(Attendee attendee : Database.GetAttendees())
            if (attendee.GetTickets().containsKey(event.GetID()))
                attendee.RefundTicket(event, attendee.GetTickets().get(event.GetID()));
    }

    public Wallet GetWallet(){
        return this.WALLET;
    }

    public Map<Integer, Integer> GetTicketsSold() {
        return this.ticketsSold;
    }

    @Override
    public String toString(){
        return  "User name: " + this.username +
                "\tDate of birth: " + this.dob +
                "\tGender: " + this.gender +
                "\tEmail: " + this.email +
                "\tPassword: " + this.password +
                "\tWallet: " + this.WALLET +
                "\tTickets sold: " + this.ticketsSold;
    }
}
