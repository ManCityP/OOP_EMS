package p2;

import p1.Category;
import p1.Room;
import p3.Wallet;

import java.util.ArrayList;

public class Event {
    Organizer organizer; //TODO Make data field private
    int ID;
    Room room;
    Category category;
    double price;

    public Event(){}
    public Event(double price, Room room, Category category) throws Exception{
        if(price < 0)
            throw new Exception("Invalid ticket price");
        this.price = price;
        if (room == null) //TODO add invalid time range
            throw new Exception(" Invalid Room");
        this.room = room;
        this.category = category;
    }

    public static Event FindEvent(ArrayList<Event> events, int ID){
        for (Event event : events)
            if (event.ID == ID)
                return event;

        return null;
    }

    public void EditEventPrice(double price) throws Exception{
        if(price < 0)
            throw new Exception("Invalid ticket price");
        this.price = price;
    }

    public void EditEventRoom(Room room) throws Exception {
        if (room == null) //TODO add invalid time range
            throw new Exception(" Invalid Room");
        this.room = room;
    }

    public void EditEventCategory(Category category) throws Exception {
        if (!Category.GetCategories().contains(category))
            throw new Exception("This Category doesn't exist!");
        this.category = category;
    }

    public double GetPrice(){
        return this.price;
    }

    //TODO delete method (CRUD)

    @Override
    public String toString(){
        return  "Organizer: " + this.organizer.GetUsername() +
                "\tEvent ID: " + this.ID +
                "\tRoom: " + this.room +
                "\tCategory: " + this.category +
                "\tPrice: " + this.price;
    }
}
