package p3;

import p1.Category;
import p1.MyDate;

import java.util.ArrayList;
import java.util.Date;

public class Attendee extends User {

    ArrayList<Category> interests;
    Wallet wallet;

    public Attendee(String username, String email, String password, MyDate dob, Gender gender, ArrayList<Category> interests, Wallet wallet) throws Exception {
        super(username,email, password,dob, gender);
        this.interests = interests;
        this.wallet = wallet;
    }

    @Override
    public String toString(){
        return  "User name: " + this.username +
                "\tEmail: " + this.email +
                "\tDate of birth: " + this.dob +
                "\tGender: " + this.gender +

                "\tPassword: " + this.password +
                "\tWallet: " + this.wallet;
    }
}