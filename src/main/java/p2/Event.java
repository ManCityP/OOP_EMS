package p2;

import p1.Category;
import p1.MyDate;
import p1.Room;
import p1.TimeRange;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private final Organizer organizer;               //Almost there
    private final int ID;
    private int roomID;
    private Category category;
    private double price;
    private MyDate date;
    private TimeRange timeRange;

    private Status status;

    public Event(Organizer organizer, int ID, double price, Room room, Category category, MyDate date, TimeRange timeRange) throws Exception{
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
        if (date == null)
            throw new Exception("Invalid date");
        if (timeRange == null)
            throw new Exception("Invalid timerange");
        this.organizer = organizer;
        this.ID = ID;
        this.price = price;
        this.room = room;
        this.category = category;
        this.date = date;
        this.timeRange = timeRange;

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.of(date.GetYear(), date.GetMonth(), date.GetDay(), (int) Math.floor(timeRange.GetStart()),
                (int) Math.floor( (timeRange.GetStart() - Math.floor(timeRange.GetStart())) * 60 ),
                (int) Math.floor( (timeRange.GetStart() - Math.floor(timeRange.GetStart()) * 60) - Math.floor((timeRange.GetStart() - Math.floor(timeRange.GetStart())) * 60) ));

        LocalDateTime endTime = LocalDateTime.of(date.GetYear(), date.GetMonth(), date.GetDay(), (int) Math.floor(timeRange.GetEnd()),
                (int) Math.floor( (timeRange.GetEnd() - Math.floor(timeRange.GetEnd())) * 60 ),
                (int) Math.floor( (timeRange.GetEnd() - Math.floor(timeRange.GetEnd()) * 60) - Math.floor((timeRange.GetEnd() - Math.floor(timeRange.GetEnd())) * 60) ));

        if (currentTime.compareTo(startTime) < 0)
            this.status = Status.UPCOMING;
        else {
            if (currentTime.compareTo(endTime) > 0)
                this.status = Status.OVER;
            else
                this.status = Status.ONGOING;
        }
    }

    public static Event FindEvent(ArrayList<Event> events, int ID){
        for (Event event : events)
            if (event.ID == ID)
                return event;

        return null;
    }

    public void EditPrice(double price) throws Exception{
        if(price < 0)
            throw new Exception("Invalid ticket price");
        this.price = price;
    }

    public void EditRoom(Room room) throws Exception {
        if (room == null)
            throw new Exception(" Invalid Room");
        this.room = room;
    }

    public int GetRoomID() {return this.roomID;}

    public void EditCategory(Category category) throws Exception {
        if (category == null)
            throw new Exception("Invalid Category");
        this.category = category;
    }
    public Status GetStatus() {
        return this.status;
    }
    public double GetPrice(){
        return this.price;
    }
    public int GetID(){
        return this.ID;
    }
    public Organizer GetOrganizer() {
        return this.organizer;
    }
    public TimeRange getTimeRange(){
        return this.timeRange;
    }
    public MyDate getDate(){
        return this.date;
    }
    @Override
    public String toString(){
        return  "Organizer: " + this.organizer.GetUsername() +
                "\tEvent ID: " + this.ID +
                "\tRoom: " + this.room +
                "\tCategory: " + this.category +
                "\tPrice: " + this.price;
    }
}
