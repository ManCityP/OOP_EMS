package p1;

import p2.Event;
import p2.Organizer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Room {
    //static int numberOfRooms;
   private int id;
   private Event currentEvent;
   private Map<Day, ArrayList<TimeRange>> availableHours = new LinkedHashMap<>();
   private Hours reservedHours;

    public Room(){}
    public Room(int id, Event currentEvent, Map<Day, ArrayList<TimeRange>> availableHours, Hours reservedHours ){
        this.id = id;
        this.currentEvent = currentEvent;
        this.availableHours = availableHours;
        this.reservedHours = reservedHours;
        //numberOfRooms++;
    };

public void UpdateEvent(Event event){ //change the current event
    this.currentEvent = event;
}
public Room FindRoom(ArrayList<Room> rooms, int id){
    for (Room room : rooms)
        if (room.id== id)
            return room;
    return null;
}
public void ReserveEvent(Event event){

}

    public int GetID(){
        return this.id;
    }
    public Event GetCurrentEvent(){
        return this.currentEvent;
    }
    public Map<Day, ArrayList<TimeRange>> GetAvailableHours(){
        return this.availableHours;
    }
    public Hours GetReservedHours(){
        return this.reservedHours;
    }
}
