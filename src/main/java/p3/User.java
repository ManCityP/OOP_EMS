package p3;

import p1.MyDate;

import java.util.Date;

public abstract class User {

    protected String username;
    protected String email;
    protected String password;
    protected MyDate dob;
    protected Gender gender = Gender.MALE;

    //  static int numberOfUsers= 0;

    void SetUsername(String NewName){

    }

    protected User() {
        // numberOfUsers++;
    }

    protected User(String username, String email, String password, MyDate dob, Gender gender){
        this.username=username;
        this.email = email;
        this.password=password;
        this.dob=dob;
        this.gender=gender;

        // numberOfUsers++;
    }
}

