package p3;

import p1.Category;

import java.util.ArrayList;
import java.util.Date;

public class Attendee extends User {
 // static int numberOfAttendees;
    // String address;
    ArrayList<Category> interests = new ArrayList<>();
    Wallet wallet;









    Attendee(){
        super();
        //  numberOfAttendees++;
    }
                                                                                 // String Address excluded in constructor
                                                                                  // String address
Attendee(String username, String password, Date dob, Gender gender, ArrayList<Category> interests, Wallet wallet) {
super(username,password,dob, gender);
this.interests = interests;
//this.address=address;
    //  numberOfAttendees++;

}


}
