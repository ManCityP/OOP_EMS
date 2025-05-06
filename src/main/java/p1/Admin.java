package p1;

import p2.Event;
import p3.Gender;
import p3.User;
import java.util.ArrayList;

public class Admin extends User {

    private String role;
    private Hours workingHours;

    public Admin(String username, String email, String password, MyDate dob, Gender gender, String dateCreated, String role, Hours workingHours) throws Exception {
        super(username, email, password, dob, gender, dateCreated);
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

    public void CreateRoom(Hours availableHours, String location, int maximum) throws Exception {
        if(maximum <= 0)
            throw new Exception("Maximum Capacity must be more than 0");
        Database.Execute(String.format("INSERT INTO room (time_range, location, maximum) VALUES ('%s', '%s', '%s')", TimeRange.EncryptWorkingHours(availableHours.map), location, maximum));
        System.out.println("Room added successfully");
    }

    public void DeleteRoom(Room room) throws Exception {
        Database.Execute(String.format("DELETE FROM room WHERE id = '%s'", room.GetID()));
        System.out.println("Room deleted successfully");
    }

    public void AddCategory(String category) throws Exception {
        if (category.isEmpty())
            throw new Exception("Category name cannot be empty");
        Category.AddCategory(category);
    }

    public void RemoveCategory(String category) throws Exception {
        Category.RemoveCategory(category);
    }

    public String GetRole() {return this.role;}
    public Hours GetWorkingHours() {return this.workingHours;}

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
