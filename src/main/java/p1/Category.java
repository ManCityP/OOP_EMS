package p1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Category {
    //static ArrayList<String> categories = new ArrayList<>();

    String category;

    public Category(String category) throws Exception {
        if (!GetCategories().contains(category))
            throw new Exception("This Category doesn't exist!");
        this.category = category;
    }

    @Override
    public String toString() {
        return this.category;
    }

    public static ArrayList<String> GetCategories() throws SQLException {
        ResultSet rs = Database.GetData(DataType.CATEGORY.toString());
        ArrayList<String> categories = new ArrayList<>();
        while(rs.next()) {
            categories.add(rs.getString(DataType.NAME.toString()));
        }
        return categories;
    }

    static void AddCategory(String category) {
        String sql = "INSERT INTO category (name) VALUES ('" + category + "')";
        Database.Execute(sql);
    }
    static void RemoveCategory(String category) {
        String sql = "DELETE FROM category WHERE name = '" + category + "'";
        Database.Execute(sql);
    }
    static void RemoveAll() {
        String sql = "DELETE FROM category";
        Database.Execute(sql);
    }
}