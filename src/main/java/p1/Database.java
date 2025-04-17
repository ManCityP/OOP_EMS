package p1;

import java.sql.*;

public abstract class Database {
    private static final String URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_Learning MYSQL";
    private static final String USER = "freedb_mancity";
    private static final String PASSWORD = "n7mVp5@bM2xvJ@X";
    private static Connection con = null;
    private static Statement stat = null;

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
    * ResultSet resultSet = Database.GetData(DataType.USER.toString() + "WHERE" + DataType.USERNAME.toString() + " = " + username);
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
}
