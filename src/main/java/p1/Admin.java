package p1;

import p3.Gender;
import p3.User;

import java.util.Date;

public class Admin extends User {

    String role;
    Hours workingHours;

    public Admin(String username, String email, String password, MyDate dob, Gender gender, String role, Hours workingHours) {
        super(username, email, password, dob, gender);
        this.role = role;
        this.workingHours = workingHours;
        Database.Execute(String.format("INSERT INTO user (username, email, password, birth_year, birth_month, birth_day, gender, type, time_range, role) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                username, email, password, dob.GetYear(), dob.GetMonth(), dob.GetDay(), gender, "Admin", TimeRange.EncryptTimeRange(workingHours.map), role));
    }
    public static void AddCategory(String category) throws Exception {
        Category.AddCategory(category);
    }

    public static void RemoveCategory(String category) throws Exception {
        Category.RemoveCategory(category);
    }

    public static void RemoveAllCategory(String category) throws Exception {
        Category.RemoveAll();
    }
}