package p3;

import java.util.Date;

public abstract class User {

    String username;
    String email;
    String password;
    Date dob = new Date(0);
    Gender gender = Gender.MALE;

    //  static int numberOfUsers= 0;

    void SetUsername(String NewName){

    }

    protected User() {
        // numberOfUsers++;
    }

    protected User(String username, String password, Date dob, Gender gender){
        this.username=username;
        this.password=password;
        this.dob=dob;
        this.gender=gender;

        // numberOfUsers++;
    }
}

