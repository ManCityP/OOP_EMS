package p2;

import p1.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private final Organizer organizer;               //Almost there
    private final String eventTitle;
    private final int ID;
    private final int roomID;
    private Category category;
    private int maxNumOfAttendees;
    private double price;
    private MyDate date;
    private TimeRange timeRange;
    private final Status status;

    public Event(Organizer organizer,String eventTitle, int ID,int maxNumOfAttendees , double price, int roomID, Category category, MyDate date, TimeRange timeRange) throws Exception{
        //TODO put all these validations in the CreateEvent() in Organizer and remove them from here.
        /*if (organizer == null)
            throw new Exception("Invalid organizer");
        if (eventTitle == null)
            throw new Exception("Invalid Event title");
        if(price < 0)
            throw new Exception("Invalid ticket price");
        if (roomID < 0)
            throw new Exception("Invalid room ID");
        if (maxNumOfAttendees < 0 || maxNumOfAttendees > Room.FindRoom(Database.GetRooms(), roomID).GetMaxCapacity())
            throw new Exception("Invalid number of attendees");
        if (category == null)
            throw new Exception("Invalid category");
        if (date == null)
            throw new Exception("Invalid date");
        if (timeRange == null)
            throw new Exception("Invalid timerange");*/

        this.organizer = organizer;
        this.eventTitle = eventTitle;
        this.ID = ID;
        this.maxNumOfAttendees = maxNumOfAttendees;
        this.price = price;
        this.roomID = roomID;
        this.category = category;
        this.date = date;
        this.timeRange = timeRange;

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.of(date.GetYear(), date.GetMonth(), date.GetDay(), (int) Math.floor(timeRange.GetStart()),
                (int) Math.floor( (timeRange.GetStart() - Math.floor(timeRange.GetStart())) * 60 ),
                (int) Math.floor( ((timeRange.GetStart() - Math.floor(timeRange.GetStart())) * 60) - Math.floor((timeRange.GetStart() - Math.floor(timeRange.GetStart())) * 60) ));

        LocalDateTime endTime = LocalDateTime.of(date.GetYear(), date.GetMonth(), date.GetDay(), (int) Math.floor(timeRange.GetEnd()),
                (int) Math.floor( (timeRange.GetEnd() - Math.floor(timeRange.GetEnd())) * 60 ),
                (int) Math.floor( ((timeRange.GetEnd() - Math.floor(timeRange.GetEnd())) * 60) - Math.floor((timeRange.GetEnd() - Math.floor(timeRange.GetEnd())) * 60) ));

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
        Database.Execute(String.format("UPDATE event SET price = '%s' WHERE (id = '%s')", this.price, this.ID));
    }

    public void ChangeRoom(int roomID) throws Exception {
        for (Room room : Database.GetRooms())
            if (room.GetID() == roomID) {
                room.ReserveEvent(this);
                Database.Execute(String.format("UPDATE event SET room_id = '%s' WHERE (id = '%s')", this.roomID, this.ID));
            }
        throw new Exception(" Invalid Room");
    }

    public void EditCategory(Category category) throws Exception {
        if (category == null)
            throw new Exception("Invalid Category");
        this.category = category;
        Database.Execute(String.format("UPDATE event SET category = '%s' WHERE (id = '%s')", this.category, this.ID));
    }

    public void EditDate(MyDate date) throws Exception {
        if (date == null)
            throw new Exception("Invalid date");
        MyDate temp = this.date;
        this.date = date;
        try {
            Room.FindRoom(Database.GetRooms(), this.roomID).ReserveEvent(this);
            Database.Execute(String.format("UPDATE event SET date = '%s' WHERE (id = '%s')", this.date, this.ID));
        }catch (Exception ex){
            this.date = temp;
        }
    }

    public void EditTimeRange(TimeRange timeRange) throws Exception {
        if(timeRange == null)
            throw new Exception("Invalid date");
        TimeRange temp = this.timeRange;
        this.timeRange = timeRange;
        try {
            Room.FindRoom(Database.GetRooms(), this.roomID).ReserveEvent(this);
            Database.Execute(String.format("UPDATE event SET time_range = '%s' WHERE (id = '%s')", this.timeRange, this.ID));
        }
        catch (Exception ex) {
            this.timeRange = temp;
        }
    }

    public void EditMaxNumOfAttendees(int maxNumOfAttendees) throws Exception {
        if (maxNumOfAttendees > Room.FindRoom(Database.GetRooms(), this.roomID).GetMaxCapacity() || maxNumOfAttendees <= 0 || maxNumOfAttendees < this.organizer.GetTicketsSold().get(this.ID))
            throw new Exception("invalid number of attendees");

        this.maxNumOfAttendees = maxNumOfAttendees;
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
    public TimeRange GetTimeRange(){
        return this.timeRange;
    }
    public MyDate GetDate(){
        return this.date;
    }
    public int GetRoomID() {
        return this.roomID;
    }
    public String GetEventTitle(){
        return this.eventTitle;
    }
    public Category GetCategory() {
        return this.category;
    }
    public int GetMaxNumOfAttendees() {
        return maxNumOfAttendees;
    }

    @Override
    public String toString(){
        return  "Organizer: " + this.organizer.GetUsername() +
                "\tEvent title: " + this.eventTitle +
                "\tEvent ID: " + this.ID +
                "\tRoom ID: " + this.roomID +
                "\tCategory: " + this.category +
                "\tEvent date: " + this.date +
                "\tEvent time range: " + this.timeRange +
                "\tPrice: " + this.price +
                "\tStatus: " + this.status;
    }
}
