package p2;

import p1.Category;
import p1.Room;

public class Event {
    double price;
    String ID;
    Room room;
    Category category;

    Event(){}

    public Event(String ID,double price, Room room, Category category){
        this.ID = ID;
        this.price = price;
        this.room = room;
        this.category = category;
    }

  /*Event createEvent(double price,String ID, Room room, Category category){
          return new Event(ID,price,room,category);
    }*/

    public void DisplayEvent(){
        System.out.println(" Event ID: " + this.ID);
        System.out.println(" Event price: " + this.price);
        System.out.println(" Event room number: " + this.room); //room.roomNumber
        System.out.println(" Event category: " + this.category); // same same
    }

    public void EditEventID(String ID){
        this.ID = ID;
    }

    public void EditEventPrice(double price){
        this.price = price;
    }

    public void EditEventRoom(Room room){
        this.room = room;
    }

    public void EditEventCategory(Category category){
        this.category = category;
    }

    public double GetPrice(){return this.price;
    }
//    void deleteEvent(){
//        this = null;
//    }
}
