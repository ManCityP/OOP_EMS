package p2;

import p1.Category;
import p1.Room;
import p3.User;
import p3.Wallet;

import java.util.ArrayList;

public class Organizer extends User {
    Wallet wallet;
    ArrayList<Event> events;

    public void AddEvent(double price, Room room, Category category) throws Exception {
        events.add(new Event(price, room, category));
    }

    public void DeleteEvent(Event event) throws Exception {
        if (!events.contains(event))
            throw new Exception("This event doesn't exist!");
        events.remove(event);
    }

    public void EditWalletBalance(double amount) throws Exception {
        this.wallet.EditBalance(amount);
    }
}
