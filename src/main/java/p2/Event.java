package p2;

import p1.Category;
import p1.Room;

public class Event {
    Organizer organizer;
    String ID;
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
