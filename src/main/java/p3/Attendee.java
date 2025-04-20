package p3;

import p1.Category;
import p1.Database;
import p1.MyDate;
import p1.Room;
import p2.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Attendee extends User {

   private ArrayList<Category> interests;
   private Wallet wallet;
   private Map<Integer, Integer> tickets = new HashMap<>();



    public static StringBuilder strConvert(ArrayList<Category> interests){
StringBuilder string= new StringBuilder();
    string.append('[');
        for (Category interest : interests){
            string.append("\""+interest+"\"");
            string.append(',');
        }
        if(string.length()>0){
        string.deleteCharAt(string.length()-1);}
        string.append(']');

return string;
    }
    public Attendee(String username, String email, String password, MyDate dob, Gender gender, ArrayList<Category> interests, Wallet wallet) throws Exception {
        super(username,email, password,dob, gender);
        this.interests = interests;
        this.wallet = wallet;
    }
    public void RegisterAttendee(String username, String email, String password, MyDate dob, Gender gender, ArrayList<Category> interests, double balance) throws Exception {
        {
        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            throw new Exception("Invalid Email format");
        if(email.length() > 128)
            throw new Exception("Email must be at most 128 characters");
        if(username.isEmpty() || username.length() > 32)
            throw new Exception("Username must be 1-32 characters");
        if(password.length() < 8 || password.length() > 32)
            throw new Exception("Password must be 8-32 characters long");
        Database.Execute(String.format("INSERT INTO user (username, email, password, birth_year, birth_month, birth_day, gender, type, interests) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s','%s')",
                username, email, password, dob.GetYear(), dob.GetMonth(), dob.GetDay(), gender, "Attendee", strConvert(interests)));
        Wallet.CreateWallet(username, balance);
    }
    }
public Attendee FindAttendee(ArrayList<Attendee> attendees, String username){
    for (Attendee attendee : attendees)
        if (attendee.username== username)
            return attendee;
    return null;
}

    public void PurchaseEvent(Event event, int numOfTickets) throws Exception{
        if(this.wallet.GetBalance() < (event.GetPrice())*numOfTickets)
            throw new Exception("Invalid balance");
        this.wallet.EditBalance(-(event.GetPrice()*numOfTickets));
        event.GetOrganizer().GetWallet().EditBalance(event.GetPrice()*numOfTickets);
        tickets.merge(event.GetID(), numOfTickets, Integer::sum);
    }

    public void JoinEvent(Event event){
if(tickets.containsKey(event.GetID())){
    //TODO ana msh 3aref a3mel eh
}
    }
    public void RefundTicket(Event event, int numOfTickets) throws Exception {
        if(this.tickets.get(event.GetID()) >= numOfTickets){
            if(!event.GetStatus()){
                this.tickets.remove(event.GetID());
                this.wallet.EditBalance(event.GetPrice()*numOfTickets);
                event.GetOrganizer().GetWallet().EditBalance(-event.GetPrice()*numOfTickets);
            }
        }
        else{
            throw new Exception("Invalid number of tickets");
        }
    }

    public Wallet GetWallet(){
        return this.wallet;
   }
    public ArrayList<Category> GetInterests(){
        return this.interests;
    }
    public Map<Integer, Integer> GetTickets() {
        return this.tickets;
    }
    @Override
    public String toString(){
        return  "User name: " + this.username +
                "\tEmail: " + this.email +
                "\tPassword: " + this.password +
                "\tDate of birth: " + this.dob +
                "\tGender: " + this.gender +
                "\tInterests: " + this.interests +
                "\tWallet: " + this.wallet;
    }
}