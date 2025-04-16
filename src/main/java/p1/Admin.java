package p1;

import p3.User;

import java.util.Date;

public class Admin extends User {
    public Admin(String username, String password, Date dob, Gender gender) {

    }
    public static void AddCategory(String category) throws Exception {
        if(Category.categories.contains(category))
            throw new Exception("This Category already exists!");
        Category.categories.add(category);
    }

    public static void RemoveCategory(String category) throws Exception {
        if (!Category.categories.contains(category))
            throw new Exception("This Category doesn't exist!");
        Category.categories.remove(category);
    }
}
