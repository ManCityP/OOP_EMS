package p2;

import p1.Category;
import p1.Room;
import p3.Wallet;

import java.util.ArrayList;

public class Event {
    private final Organizer organizer; //TODO Make data field private
    private final int ID;
    private Room room;
    private Category category;
    private double price;

    public Event(Organizer organizer, int ID, double price, Room room, Category category) throws Exception{
        if (organizer == null)
            throw new Exception(" Invalid organizer");
        if(ID < 0)
            throw new Exception("Invalid ID");
        if(price < 0)
            throw new Exception("Invalid ticket price");
        if (room == null)
            throw new Exception("Invalid room");
        if (category == null)
            throw new Exception("Invalid category");

        this.organizer = organizer;
        this.ID = ID;
        this.price = price;
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
        if (room == null)
            throw new Exception(" Invalid Room");
        this.room = room;
    }

    public void EditEventCategory(Category category) throws Exception {
        if (category == null)
            throw new Exception("Invalid Category");
        this.category = category;
    }

    public double GetPrice(){
        return this.price;
    }
    public int GetID(){
        return this.ID;
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
