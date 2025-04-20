package p3;

import p1.Category;
import p1.MyDate;
import p2.Event;

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

    public void PurchaseEvent(Event event, int numOfTickets) throws Exception{
        if(this.wallet.GetBalance() < (event.GetPrice())*numOfTickets)
            throw new Exception("Invalid balance");
        this.wallet.EditBalance(-1*(event.GetPrice()*numOfTickets));
        event.GetOrganizer().GetWallet().EditBalance(event.GetPrice()*numOfTickets);
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