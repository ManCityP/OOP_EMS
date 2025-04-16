package p2;

import p1.Category;
import p1.Room;

public class Event {
    double price;
    String ID;
    Room room;
    Category category;

    Event(){}

    Event(String ID,double price, Room room, Category category){
        this.ID = ID;
        this.price = price;
        this.room = room;
        this.category = category;
    }

  /*Event createEvent(double price,String ID, Room room, Category category){
          return new Event(ID,price,room,category);
    }*/

    void DisplayEvent(){
        System.out.println(" Event ID: " + this.ID);
        System.out.println(" Event price: " + this.price);
        System.out.println(" Event room number: " + this.room); //room.roomNumber
        System.out.println(" Event category: " + this.category); // same same
    }

    void EditEventID(String ID){
        this.ID = ID;
    }

    void EditEventPrice(double price){
        this.price = price;
    }

    void EditEventRoom(Room room){
        this.room = room;
    }

    void EditEventCategory(Category category){
        this.category = category;
    }

/*    void deleteEvent(){
        this = null;
    }*/
}
