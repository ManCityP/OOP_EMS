package p2;

import p1.*;
import p3.Gender;
import p3.User;
import p3.Wallet;

import java.util.ArrayList;
import java.util.Map;

public class Organizer extends User {
    private final Wallet wallet;         //Almost there

    public Organizer(String username, String email, String password, MyDate dob, Gender gender, Wallet wallet) throws Exception {
        super(username, email, password, dob, gender);
        if(wallet == null)
            throw new Exception("Invalid Wallet");
        this.wallet = wallet;
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
        Database.Execute(String.format("INSERT INTO user (username, email, password, birth_year, birth_month, birth_day, gender, type) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                username, email, password, dob.GetYear(), dob.GetMonth(), dob.GetDay(), gender, "Organizer"));
        Wallet.CreateWallet(username, balance);
    }

    public static Organizer FindOrganizer(ArrayList<Organizer> organizers, String username){
        for (Organizer organizer : organizers)
            if (organizer.username.equals(username))
                return organizer;

        return null;
    }

    public void CreateEvent(double price, Room room, Category category, MyDate date, TimeRange timeRange) throws Exception {
        Database.Execute(String.format("INSERT INTO event (username, price, category, room_id, day, time_range) " +
                                       "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                                       username, price, category, room.GetID(), date.toString(), timeRange.toString()));
        System.out.println("Event added successfully");
    }

    //TODO read events

    public void EditEventPrice(Event event, double price) throws Exception{
        if(!(event.GetOrganizer().GetUsername().equals(this.username)))
            throw new Exception("This Event belongs to another organizer");
        event.EditPrice(price);
    }

    public void EditEventRoom(Event event, Room room) throws Exception{
        if(!(event.GetOrganizer().GetUsername().equals(this.username)))
            throw new Exception("This Event belongs to another organizer");
        event.EditRoom(room);
    }

    public void EditEventCategory(Event event, Category category) throws Exception{
        if(!(event.GetOrganizer().GetUsername().equals(this.username)))
            throw new Exception("This Event belongs to another organizer");
        event.EditCategory(category);
    }

    public void DeleteEvent(Event event) throws Exception {
        Database.Execute(String.format("DELETE FROM event WHERE id = '%s'", event.GetID()));
        System.out.println("Event deleted successfully");
    }

    public String GetUsername(){
        return this.username;
    }

    public Wallet GetWallet(){
        return this.wallet;
    }

    @Override
    public String toString(){
        return  "User name: " + this.username +
                "\tDate of birth: " + this.dob +
                "\tGender: " + this.gender +
                "\tEmail: " + this.email +
                "\tPassword: " + this.password +
                "\tWallet: " + this.wallet;
    }
}
