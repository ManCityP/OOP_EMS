package p2;

import p1.Category;
import p1.Room;

public class Event {
    double price;
    String ID;
    Room room;
    Category category;

    Event(){}

    public Event(double price, Room room, Category category) throws Exception{
        if(price < 0)
            throw new Exception("Invalid ticket price");
        this.price = price;
        if (room == null) //TODO add invalid time range
            throw new Exception(" Invalid Room");
        this.room = room;
        if (!Category.GetCategory().contains(category))
            throw new Exception("This Category doesn't exist!");
        this.category = category;
    }

    public void DisplayEvent(){
        System.out.println(" Event ID: " + this.ID);
        System.out.println(" Event price: " + this.price);
        System.out.println(" Event room number: " + this.room); //room.roomNumber
        System.out.println(" Event category: " + this.category); // same same
    }

//    public void EditEventID(String ID){
//        this.ID = ID;
//    }

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
        if (!Category.GetCategory().contains(category))
            throw new Exception("This Category doesn't exist!");
        this.category = category;
    }

    public double GetPrice(){
        return this.price;
    }

    //TODO delete method (CRUD)
}
