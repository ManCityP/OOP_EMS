package p2;

import p1.*;
import p3.Gender;
import p3.User;
import p3.Wallet;

import java.util.ArrayList;
import java.util.Map;

public class Organizer extends User {
    Wallet wallet;
    ArrayList<Event> events;

    public Organizer(String username, String email, String password, MyDate dob, Gender gender, Wallet wallet) {
        super(username, email, password, dob, gender);
        Database.Execute(String.format("INSERT INTO user (username, email, password, birth_year, birth_month, birth_day, gender, type) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                username, email, password, dob.GetYear(), dob.GetMonth(), dob.GetDay(), gender, "Organizer"));
    }

    public void AddEvent(double price, Room room, Category category, MyDate date, TimeRange timeRange) throws Exception {
        Database.Execute(String.format("INSERT INTO event (username, price, category, room_id, day, time_range) " +
                                       "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                                       username, price, category, room.GetID(), date.toString(), timeRange.toString()));
        System.out.println("Event added successfully");
    }

    public void DeleteEvent(Event event) throws Exception {
        Database.Execute(String.format("DELETE FROM event WHERE id = '%s'", event.ID));
        System.out.println("Event deleted successfully");
    }

    public void EditWalletBalance(double amount) throws Exception {
        this.wallet.EditBalance(amount);
    }
}
