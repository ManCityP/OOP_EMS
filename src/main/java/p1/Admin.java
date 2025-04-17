package p1;

import p3.Gender;
import p3.User;

import java.util.Date;

public class Admin extends User {

    String role;

    public Admin(String username, String password, Date dob, Gender gender, String role) {
        super(username, password, dob, gender);
        this.role = role;
    }
    public static void AddCategory(String category) throws Exception {
        /*if(Category.categories.contains(category))
            throw new Exception("This Category already exists!");*/
        Category.AddCategory(category);
    }

    public static void RemoveCategory(String category) throws Exception {
        /*if (!Category.categories.contains(category))
            throw new Exception("This Category doesn't exist!");*/
        Category.RemoveCategory(category);
    }
}