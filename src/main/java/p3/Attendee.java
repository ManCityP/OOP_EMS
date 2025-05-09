package p3;

import p1.Category;
import p1.Database;
import p1.MyDate;
import p1.Room;
import p2.Event;
import p2.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Attendee extends User {

    private ArrayList<Category> interests;
    private Wallet wallet;
    private Map<Integer, Integer> tickets = new HashMap<>();


    public static StringBuilder strConvert(ArrayList<Category> interests) {
        StringBuilder string = new StringBuilder();
        string.append('[');
        for (Category interest : interests) {
            string.append("\"" + interest + "\"");
            string.append(',');
        }
        if (!string.isEmpty()) {
            string.deleteCharAt(string.length() - 1);
        }
        string.append(']');

        return string;
    }
    public StringBuilder guiConcat(){
        StringBuilder string = new StringBuilder();

        for (Category interest : GetInterests()) {
            string.append(interest);
            string.append(", ");
        }
        if (string.length() > 0) {
            string.deleteCharAt(string.length() - 1);
            string.deleteCharAt(string.length() - 1);

        }
        string.append('.');

        return string;

    }

    public Attendee(String username, String email, String password, MyDate dob, Gender gender, String dateCreated, ArrayList<Category> interests, Wallet wallet, Map<Integer, Integer> tickets) throws Exception {
        super(username, email, password, dob, gender, dateCreated);
        this.interests = interests;
        this.wallet = wallet;
        this.tickets = tickets;
    }

    public static void RegisterAttendee(String username, String email, String password, MyDate dob, Gender gender, ArrayList<Category> interests, double balance) throws Exception {
        {
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
                throw new Exception("Invalid Email format");
            if (email.length() > 128)
                throw new Exception("Email must be at most 128 characters");
            if (username.isEmpty() || username.length() > 32)
                throw new Exception("Username must be 1-32 characters");
            if (password.length() < 8 || password.length() > 32)
                throw new Exception("Password must be 8-32 characters long");
            if(interests == null)
                interests = new ArrayList<>();
            interests.add(new Category());
            Database.Execute(String.format("INSERT INTO user (username, email, password, birth_year, birth_month, birth_day, gender, type, interests, tickets) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s','%s', '%s')",
                    username, email, password, dob.GetYear(), dob.GetMonth(), dob.GetDay(), gender, "Attendee", strConvert(interests), ""));
            Wallet.CreateWallet(username, balance);
        }
    }

    public static Attendee FindAttendee(ArrayList<Attendee> attendees, String username) {
        for (Attendee attendee : attendees)
            if (attendee.username.equals(username))
                return attendee;
        return null;
    }

    public void PurchaseEvent(Event event, int numOfTickets) throws Exception {
        event.GetOrganizer().GetTicketsSold().putIfAbsent(event.GetID(), 0);
        if (event.GetStatus() == Status.UPCOMING) {
            if (event.GetOrganizer().GetTicketsSold().get(event.GetID()) + numOfTickets > event.GetMaxNumOfAttendees()) {
                throw new Exception("Number of tickets is over the event threshold");
            } else {
                if (this.wallet.GetBalance() < (event.GetPrice()) * numOfTickets)
                    throw new Exception("Invalid balance");
                this.wallet.EditBalance(-(event.GetPrice() * numOfTickets));
                event.GetOrganizer().GetWallet().EditBalance(event.GetPrice() * numOfTickets);
                tickets.merge(event.GetID(), numOfTickets, Integer::sum);
                Database.Execute(String.format("UPDATE user SET tickets = '%s' WHERE (username = '%s')", Database.EncryptTickets(this.tickets), this.username));
                event.GetOrganizer().GetTicketsSold().merge(event.GetID(), numOfTickets, Integer::sum);
                Database.Execute(String.format("UPDATE user SET tickets = '%s' WHERE (username = '%s')", Database.EncryptTickets(event.GetOrganizer().GetTicketsSold()), event.GetOrganizer().GetUsername()));
            }
        } else {
            throw new Exception("Purchase time has expired");
        }
    }

    public void RefundTicket(Event event, int numOfTickets) throws Exception {
        if (this.tickets.containsKey(event.GetID())) {
            if (this.tickets.get(event.GetID()) >= numOfTickets) {
                if (event.GetStatus() != Status.OVER) {
                    this.tickets.put(event.GetID(), this.tickets.get(event.GetID()) - numOfTickets);
                    if (this.tickets.get(event.GetID()) <= 0)
                        this.tickets.remove(event.GetID());
                    event.GetOrganizer().GetTicketsSold().put(event.GetID(), event.GetOrganizer().GetTicketsSold().get(event.GetID()) - numOfTickets);
                    if (event.GetOrganizer().GetTicketsSold().get(event.GetID()) <= 0)
                        event.GetOrganizer().GetTicketsSold().remove(event.GetID());
                    this.wallet.EditBalance(event.GetPrice() * numOfTickets);
                    event.GetOrganizer().GetWallet().EditBalance(-event.GetPrice() * numOfTickets);
                    Database.Execute(String.format("UPDATE user SET tickets = '%s' WHERE (username = '%s')", Database.EncryptTickets(this.tickets), this.username));
                    Database.Execute(String.format("UPDATE user SET tickets = '%s' WHERE (username = '%s')", Database.EncryptTickets(event.GetOrganizer().GetTicketsSold()), event.GetOrganizer().GetUsername()));

                } else {
                    throw new Exception("The Refund Time has expired!");
                }
            } else {
                throw new Exception("Invalid number of tickets");
            }
        } else {
            throw new Exception("No ticket(s) for this event.");
        }
    }

    public Wallet GetWallet() {
        return this.wallet;
    }

    public ArrayList<Category> GetInterests() {
        return this.interests;
    }

    public Map<Integer, Integer> GetTickets() {
        return this.tickets;
    }

    @Override
    public String toString() {
        return "User name: " + this.username +
                "\tEmail: " + this.email +
                "\tPassword: " + this.password +
                "\tDate of birth: " + this.dob +
                "\tGender: " + this.gender +
                "\tTickets: " + this.tickets +
                "\tInterests: " + this.interests +
                "\tWallet: " + this.wallet;
    }
}