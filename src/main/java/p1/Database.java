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

    public static ResultSet GetData(DataType type) {
        try {
            String sql = "SELECT * FROM " + type;
            return stat.executeQuery(sql);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

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
