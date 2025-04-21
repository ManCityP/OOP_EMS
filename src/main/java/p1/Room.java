package p1;

import p2.Event;
import p2.Organizer;

import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Room {
    //static int numberOfRooms;
    private int id;
    private Event currentEvent;
    // private Map<Day, ArrayList<TimeRange>> availableHours = new LinkedHashMap<>();
    private Hours availableHours;
    private Hours reservedHours;
    private final String LOCATION;

    public Room(int id, Event currentEvent, Hours availableHours, Hours reservedHours, String LOCATION) {
        this.id = id;
        this.currentEvent = currentEvent;
        this.availableHours = availableHours;
        this.reservedHours = reservedHours;
        this.LOCATION = LOCATION;
        //numberOfRooms++;
    }

    ;

    public void UpdateEvent(Event event) { //change the current event
        this.currentEvent = event;
    }

    public static Room FindRoom(ArrayList<Room> rooms, int id) {
        for (Room room : rooms)
            if (room.id == id)
                return room;
        return null;
    }

    public void ReserveEvent(Event event) throws Exception {
        Day eventDay = MyDate.GetDayOfTheWeek(event.GetDate());
        ArrayList<TimeRange> dayRange = GetAvailableHours().map.get(eventDay);
//SAM7OONI YA REGALAAAAAAA
        if (!GetReservedHours().Contains(event.GetTimeRange(), eventDay.toString())) {
            if (GetAvailableHours().Contains(event.GetTimeRange(), eventDay.toString())) {
                GetReservedHours().map.get(eventDay).add(event.GetTimeRange());
            } else {
                throw new Exception("Room not available at this time");
            }
        } else {
            throw new Exception("Room already reserved at this time");
        }
    }

    public void AddAvailableHours(TimeRange timeRange, String day) throws Exception {

        GetAvailableHours().AddTime(day, timeRange);
    }

    public void RemoveAvailableHours(TimeRange timeRange, String day) throws Exception {
        GetAvailableHours().RemoveTime(day, timeRange);
    }


    public void SetAvailableHours(Hours newAvailableHours) {
        this.availableHours = newAvailableHours;
    }

    public int GetID() {
        return this.id;
    }

    public Event GetCurrentEvent() {
        return this.currentEvent;
    }

    public Hours GetAvailableHours() {
        return this.availableHours;
    }

    public Hours GetReservedHours() {
        return this.reservedHours;
    }


    @Override
    public String toString() {
        return "Room ID: " + this.id +
                "\tCurrent Event: " + this.currentEvent +
                "\tAvailable Hours: " + this.availableHours +
                "\tReserved Hours: " + this.reservedHours +
                "\tLocation: " + this.LOCATION;

    }
}
