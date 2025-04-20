package p3;

import p1.Database;
import p1.MyDate;

import java.util.ArrayList;
import java.util.Date;

public abstract class User {

    protected String username;
    protected String email;
    protected String password;
    protected MyDate dob;
    protected Gender gender = Gender.MALE;

    protected User(String username, String email, String password, MyDate dob, Gender gender) throws Exception {

        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            throw new Exception("Invalid Email format");
        if(email.length() > 128)
            throw new Exception("Email must be at most 128 characters");
        if(username.isEmpty() || username.length() > 32)
            throw new Exception("Username must be 1-32 characters");
        if(password.length() < 8 || password.length() > 32)
            throw new Exception("Password must be 8-32 characters long");
        //TODO if we want strict password rules.

        this.username=username;
        this.email = email;
        this.password=password;
        this.dob=dob;
        this.gender=gender;
    }

    public static User Login(String usermail, String password) throws Exception {
        ArrayList<User> users = new ArrayList<>();
        users.addAll(Database.GetAdmins());
        users.addAll(Database.GetOrganizers());
        users.addAll(Database.GetAttendees());
        for (User user : users) {
            if((usermail.equals(user.username) || usermail.equals(user.email)) && password.equals(user.password))
                return user;
        }
        throw new Exception("Invalid Login Details!");
    }

    public String GetUsername(){
        return this.username;
    }
}

