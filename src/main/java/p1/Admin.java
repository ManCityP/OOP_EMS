package p1;

import p3.Gender;
import p3.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Admin extends User {

    String role; //TODO Make data field private
    Hours workingHours;

    public Admin(String username, String email, String password, MyDate dob, Gender gender, String role, Hours workingHours) throws Exception {
        super(username, email, password, dob, gender);
        this.role = role;
        this.workingHours = workingHours;
    }

    public static void RegisterAdmin(String username, String email, String password, MyDate dob, Gender gender, String role, Hours workingHours) throws Exception {
        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            throw new Exception("Invalid Email format");
        if(email.length() > 128)
            throw new Exception("Email must be at most 128 characters");
        if(username.isEmpty() || username.length() > 32)
            throw new Exception("Username must be 1-32 characters");
        if(password.length() < 8 || password.length() > 32)
            throw new Exception("Password must be 8-32 characters long");
        Database.Execute(String.format("INSERT INTO user (username, email, password, birth_year, birth_month, birth_day, gender, type, time_range, role) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                username, email, password, dob.GetYear(), dob.GetMonth(), dob.GetDay(), gender, "Admin", TimeRange.EncryptWorkingHours(workingHours.map), role));
    }

    public static Admin FindAdmin(ArrayList<Admin> admins, String username){
        for (Admin admin : admins)
            if (admin.username.equals(username))
                return admin;

        return null;
    }

    public void AddCategory(String category) throws Exception {
        Category.AddCategory(category);
    }

    public void RemoveCategory(String category) throws Exception {
        Category.RemoveCategory(category);
    }

    public void RemoveAllCategory(String category) throws Exception {
        Category.RemoveAll();
    }

    @Override
    public String toString(){
        return  "User name: " + this.username +
                "\tRole: " + this.role +
                "\tDate of birth: " + this.dob +
                "\tGender: " + this.gender +
                "\tEmail: " + this.email +
                "\tPassword: " + this.password +
                "\tWorking Hours: " + this.workingHours;
    }
}
