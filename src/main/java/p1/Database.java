package p1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import p2.Event;
import p2.Organizer;
import p2.Status;
import p3.Attendee;
import p3.Gender;
import p3.Wallet;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Database {
    private static final String URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_Learning MYSQL";
    private static final String USER = "freedb_mancity";
    private static final String PASSWORD = "n7mVp5@bM2xvJ@X";
    private static Connection con = null;
    private static Statement stat = null;
    private static Gson gson = new Gson();
    public static void Connect() {
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Successfully connected to the database!");
            stat = con.createStatement();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void CloseConnection() {
        try {
            if (stat != null)
                stat.close();
            if (con != null)
                con.close();
            System.out.println("Successfully disconnected from database!");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static StringBuilder EncryptTickets(Map<Integer, Integer> tickets) {
        StringBuilder ticketString = new StringBuilder();
        for(Map.Entry<Integer, Integer> entry : tickets.entrySet()) {
            ticketString.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }
        if (!ticketString.isEmpty())
            ticketString.deleteCharAt(ticketString.length() - 1);
        return ticketString;
    }

    public static Map<Integer, Integer> DecryptTickets(String tickets) {
        Map<Integer, Integer> ticketsMap = new HashMap<>();
        String[] split = tickets.split(",");
        for (String ticket : split) {
            int id = Integer.parseInt(ticket.split(":")[0]);
            int numOfTickets = Integer.parseInt(ticket.split(":")[1]);
        }
        return ticketsMap;
    }

    /* How to use this function:
    *
    * This function will return a ResultSet which contains the data of all entries to a specific database
    *
    * Examples of usage:
    *
    * ResultSet resultSet = Database.GetData(DataType.USER.toString());
    * while(resultSet.next()) {  to loop through the results
    *      String username = resultSet.getString(DataType.USERNAME.toString()); gets the username of an entry
    * }
    *
    * If you want the data of a user with a specific username:
    * String username = "Ahmed Hesham";
    * ResultSet resultSet = Database.GetData(DataType.USER.toString() + " WHERE " + DataType.USERNAME.toString() + " = " + username);
    * while(resultSet.next()) {
    *       String password = resultSet.GetString(DataType.PASSWORD.toString()); gets the password of the user with the above username
    * }
    *
    * */
    public static ResultSet GetData(String type) {
        try {
            String sql = "SELECT * FROM " + type;
            return stat.executeQuery(sql);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Everything in the format matters a lot (including uppercase and lowercase and any special symbols)

    /* Inserting a user:
    // "INSERT INTO user (username, email, password, birth_year, birth_month, birth_day, gender, type) VALUES ('Ahmed Hesham', 'ahmed@gmail.com', '24P0029', '2006', '12', '14', 'Male', 'Organizer')"
    // gender must be either 'Male' or 'Female' / type must be 'Attendee' or 'Organizer' or 'Admin'
    // If the type is Attendee the format will be the following:
    // "INSERT INTO user (username, email, password, birth_year, birth_month, birth_day, gender, type, interests) VALUES ('Ahmed Hesham', 'ahmed@gmail.com', '24P0029', '2006', '12', '14', 'Male', 'Organizer', 'interest1,interest2')"
    // If the type is Admin the format will be the following:
    // "INSERT INTO user (username, email, password, birth_year, birth_month, birth_day, gender, type, time_range, role) VALUES ('Ahmed Hesham', 'hesham@gmail.co', '24p0029', '2006', '12', '14', 'Male', 'Admin', 'test', 'Random Role')"
    // Time range must be in the format in the TimeRange class (use the EncryptTimeRange function).
    */

    /* Inserting a room:
    // "INSERT INTO room (time_range) VALUES ('Test Time')"
    // time_range is the available hours for the room
    */

    /* Inserting an event:
    // "INSERT INTO event (price, category, room_id, day, time_range) VALUES ('200', 'Entertainment', '4', 'Thursday', 'Test Range')"
    // day must be one of the weekdays first letter uppercase the rest lowercase.
    */

    /* Inserting a wallet:
    // "INSERT INTO wallet (username, balance) VALUES ('mohy', '2400')"
    // username should be the username of the user associated with the wallet
    */

    /* Updating a value:
    // "Update wallet SET balance = '3000' WHERE username = 'mohy'"
    // This updates the wallet with a given username
    */

    //Call this function with one of the above format.
    public static void Execute(String sql) {
        try {
            PreparedStatement insertStatement = con.prepareStatement(sql);
            insertStatement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Admin> GetAdmins() throws Exception {
        ResultSet rs = GetData(DataType.USER.toString() + " WHERE " + DataType.TYPE + " = 'Admin'");
        ArrayList<Admin> admins = new ArrayList<>();
        while(rs.next()) {
            admins.add(new Admin(rs.getString(DataType.USERNAME.toString()), rs.getString(DataType.EMAIL.toString()), rs.getString(DataType.PASSWORD.toString()),
                            new MyDate(rs.getInt(DataType.BIRTH_DAY.toString()), rs.getInt(DataType.BIRTH_MONTH.toString()), rs.getInt(DataType.BIRTH_YEAR.toString())),
                                rs.getString(DataType.GENDER.toString()).equals("Male")? Gender.MALE : Gender.FEMALE, rs.getString(DataType.ROLE.toString()),
                                    new Hours(TimeRange.DecryptWorkingHours(rs.getString(DataType.TIME_RANGE.toString())))));
        }
        return admins;
    }
    public static ArrayList<Organizer> GetOrganizers() throws Exception {
        ResultSet rs = GetData(DataType.USER.toString() + " WHERE " + DataType.TYPE + " = 'Organizer'");
        ArrayList<Organizer> organizers = new ArrayList<>();
        while(rs.next()) {
            ResultSet resultSet = GetData(DataType.WALLET.toString() + " WHERE " + DataType.USERNAME + " = " + rs.getString(DataType.USERNAME.toString()));
            while(resultSet.next()) {
                organizers.add(new Organizer(rs.getString(DataType.USERNAME.toString()), rs.getString(DataType.EMAIL.toString()), rs.getString(DataType.PASSWORD.toString()),
                        new MyDate(rs.getInt(DataType.BIRTH_DAY.toString()), rs.getInt(DataType.BIRTH_MONTH.toString()), rs.getInt(DataType.BIRTH_YEAR.toString())),
                            rs.getString(DataType.GENDER.toString()).equals("Male")? Gender.MALE : Gender.FEMALE,
                                new Wallet(resultSet.getDouble(DataType.BALANCE.toString()), resultSet.getInt(DataType.ID.toString()))));
            }
        }
        return organizers;
    }
    public static ArrayList<Attendee> GetAttendees() throws Exception {
        ResultSet rs = GetData(DataType.USER.toString() + " WHERE " + DataType.TYPE + " = 'Attendee'");
        ArrayList<Attendee> attendees = new ArrayList<>();
        while(rs.next()) {
            ResultSet resultSet = GetData(DataType.WALLET.toString() + " WHERE " + DataType.USERNAME + " = " + rs.getString(DataType.USERNAME.toString()));
            ArrayList<Category> interests_array = new ArrayList<>();
            String json = resultSet.getString(DataType.INTERESTS.toString());
            List<String> interests = gson.fromJson(json, new TypeToken<List<String>>() {}.getType());
            for(String interest : interests)    interests_array.add(new Category(interest));
            while(resultSet.next()) {
                attendees.add(new Attendee(rs.getString(DataType.USERNAME.toString()), rs.getString(DataType.EMAIL.toString()), rs.getString(DataType.PASSWORD.toString()),
                                new MyDate(rs.getInt(DataType.BIRTH_DAY.toString()), rs.getInt(DataType.BIRTH_MONTH.toString()), rs.getInt(DataType.BIRTH_YEAR.toString())),
                                    rs.getString(DataType.GENDER.toString()).equals("Male")? Gender.MALE : Gender.FEMALE, interests_array,
                                        new Wallet(resultSet.getDouble(DataType.BALANCE.toString()), resultSet.getInt(DataType.ID.toString()))));
            }
        }
        return attendees;
    }
    public static ArrayList<Event> GetEvents() throws Exception {
        ResultSet rs = GetData(DataType.EVENT.toString());
        ArrayList<Event> events = new ArrayList<>();
        while(rs.next()) {
            events.add(new Event(Organizer.FindOrganizer(Database.GetOrganizers(), rs.getString(DataType.USERNAME.toString())), rs.getInt(DataType.ID.toString()),
                        rs.getDouble(DataType.PRICE.toString()), rs.getInt(DataType.ROOM_ID.toString()), new Category(rs.getString(DataType.CATEGORY.toString())),
                            new MyDate(rs.getString(DataType.DATE.toString())),
                                new TimeRange(rs.getString(DataType.TIME_RANGE.toString().split("-")[0]), rs.getString(DataType.TIME_RANGE.toString().split("-")[1]))));
        }
        return events;
    }
    public static ArrayList<Room> GetRooms() throws Exception {
        ResultSet rs = GetData(DataType.ROOM.toString());
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Event> events = GetEvents();
        while(rs.next()) {
            Event event = null;
            for (Event e : events)
                if (e.GetRoomID() == rs.getInt(DataType.ID.toString()) && e.GetStatus() == Status.ONGOING)
                    event = e;
            rooms.add(new Room(rs.getInt(DataType.ID.toString()), event, new Hours(TimeRange.DecryptWorkingHours(rs.getString(DataType.TIME_RANGE.toString()))), ));
        }
        return rooms;
    }
}
