package com.fhm.oop_ems;

import p1.*;
import p2.Event;
import p2.Organizer;
import p3.Attendee;
import p3.Gender;
import p3.User;

import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Day.Init();
        Database.Connect();

        Scanner scanner = new Scanner(System.in);

        User currentUser = null;
        while(true) {
            try {
                System.out.println("What would you like to do? ");
                if (currentUser != null) {
                    if (currentUser instanceof Admin)
                        currentUser = Admin.FindAdmin(Database.GetAdmins(), currentUser.GetUsername());
                    else if (currentUser instanceof Organizer)
                        currentUser = Organizer.FindOrganizer(Database.GetOrganizers(), currentUser.GetUsername());
                    else
                        currentUser = Attendee.FindAttendee(Database.GetAttendees(), currentUser.GetUsername());
                }
                String input;
                input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("create")) {
                    System.out.println("What would you like to create? ");
                    input = scanner.nextLine().trim();
                    if(input.equalsIgnoreCase("account")) {
                        if(currentUser != null)
                            throw new Exception("You are already logged in!");
                        else {
                            System.out.println("Account Type: ");
                            String type = scanner.nextLine().trim();
                            System.out.println("Email: ");
                            String email = scanner.nextLine().trim();
                            System.out.println("Username: ");
                            String username = scanner.nextLine().trim();
                            System.out.println("Password: ");
                            String password = scanner.nextLine().trim();
                            System.out.println("Date of Birth (dd/mm/yyyy): ");
                            String dobStr = scanner.nextLine().trim();
                            System.out.println("Gender: ");
                            String genderStr = scanner.nextLine().trim();
                            Gender gender = genderStr.equalsIgnoreCase("male")? Gender.MALE : Gender.FEMALE;
                            String balance;
                            switch (type.toLowerCase()) {
                                case "admin":
                                    System.out.println("Role: ");
                                    String role = scanner.nextLine().trim();
                                    System.out.println("Working Hours (Format is\t0>[06:15-17:00]/2>[09:00-21:34]/5>[13:30-18:30,22:30-23:30]\t) 0 for Saturday, 6 for Friday: ");
                                    String hoursStr = scanner.nextLine().trim();
                                    Admin.RegisterAdmin(username, email, password, new MyDate(dobStr), gender, role, new Hours(TimeRange.DecryptWorkingHours(hoursStr)));
                                    break;
                                case "organizer":
                                    System.out.println("Balance: ");
                                    balance = scanner.nextLine().trim();
                                    Organizer.RegisterOrganizer(username, email, password, new MyDate(dobStr), gender, Double.parseDouble(balance));
                                    break;
                                case "attendee":
                                    System.out.println("Balance: ");
                                    balance = scanner.nextLine().trim();
                                    System.out.println("Interests (Format is\tinterest1,interest2,interest3\t) : ");
                                    String interestStr = scanner.nextLine().trim();
                                    ArrayList<Category> interests = new ArrayList<>();
                                    String[] interest_arr = interestStr.split(",");
                                    interests.add(new Category());
                                    for(String interest : interest_arr)
                                        interests.add(new Category(interest.trim()));
                                    Attendee.RegisterAttendee(username, email, password, new MyDate(dobStr), gender, interests, Double.parseDouble(balance));
                                    break;
                                default:
                                    throw new Exception("Invalid Account Type!");
                            }
                        }
                    }
                    else if(input.equalsIgnoreCase("room")) {
                        if(currentUser == null)
                            throw new Exception("You are not logged in!");
                        else if(!(currentUser instanceof Admin))
                            throw new Exception("You must have admin access to create a room!");
                        else {
                            System.out.println("Available Hours (Format is\t0>[06:15-17:00]/2>[09:00-21:34]/5>[13:30-18:30,22:30-23:30]\t) 0 for Saturday, 6 for Friday: ");
                            String hoursStr = scanner.nextLine().trim();
                            System.out.println("Location: ");
                            String location = scanner.nextLine().trim();
                            System.out.println("Maximum Capacity: ");
                            String maximum = scanner.nextLine().trim();
                            ((Admin) currentUser).CreateRoom(new Hours(TimeRange.DecryptWorkingHours(hoursStr)), location, Integer.parseInt(maximum));
                        }
                    }
                    else if(input.equalsIgnoreCase("category")) {
                        if(currentUser == null)
                            throw new Exception("You are not logged in!");
                        else if(!(currentUser instanceof Admin))
                            throw new Exception("You must have admin access to create a category!");
                        else {
                            System.out.println("Category Name: ");
                            String category = scanner.nextLine().trim();
                            ((Admin) currentUser).AddCategory(category);
                        }
                    }
                    else if(input.equalsIgnoreCase("event")) {
                        if(currentUser == null)
                            throw new Exception("You are not logged in!");
                        else if(!(currentUser instanceof Organizer))
                            throw new Exception("You must have organizer access to create an event!");
                        else {
                            System.out.println("Event Title: ");
                            String eventTitle = scanner.nextLine().trim();
                            System.out.println("Price: ");
                            String price = scanner.nextLine().trim();
                            System.out.println("Room ID: ");
                            String roomID = scanner.nextLine().trim();
                            System.out.println("Category: ");
                            String category = scanner.nextLine().trim();
                            System.out.println("Date: ");
                            String date = scanner.nextLine().trim();
                            System.out.println("Time Range: ");
                            String timeRange = scanner.nextLine().trim();
                            String[] range = timeRange.split("-");
                            System.out.println("Maximum Number of Attendees: ");
                            String maximum = scanner.nextLine().trim();
                            ((Organizer) currentUser).CreateEvent(Double.parseDouble(price), Objects.requireNonNull(Room.FindRoom(Database.GetRooms(), Integer.parseInt(roomID))),
                                                                    eventTitle, new Category(category), new MyDate(date), new TimeRange(range[0].trim(), range[1].trim()), Integer.parseInt(maximum));
                        }
                    }
                }
                else if(input.equalsIgnoreCase("login")) {
                    if (currentUser != null)
                        throw new Exception("You are already logged in!");
                    System.out.print("Username or email: ");
                    String usermail = scanner.nextLine().trim();
                    System.out.print("Password: ");
                    String password = scanner.nextLine().trim();
                    currentUser = User.Login(usermail, password);
                }
                else if(input.equalsIgnoreCase("logout")) {
                    if(currentUser == null)
                        throw new Exception("You are not logged in!");
                    currentUser = null;
                }
                else if (input.equalsIgnoreCase("delete")) {
                    if(currentUser == null)
                        throw new Exception("You are not logged in!");
                    System.out.println("What would you like to delete? ");
                    input = scanner.nextLine().trim();
                    if(input.equalsIgnoreCase("room")) {
                        if(!(currentUser instanceof Admin))
                            throw new Exception("You must have admin access to delete a room!");
                        else {
                            System.out.println("Room ID: ");
                            String roomID = scanner.nextLine().trim();
                            ((Admin) currentUser).DeleteRoom(Objects.requireNonNull(Room.FindRoom(Database.GetRooms(), Integer.parseInt(roomID))));
                        }
                    }
                    else if(input.equalsIgnoreCase("category")) {
                        if(!(currentUser instanceof Admin))
                            throw new Exception("You must have admin access to delete a category!");
                        else {
                            System.out.println("Category Name: ");
                            String category = scanner.nextLine().trim();
                            ((Admin) currentUser).RemoveCategory(category);
                        }
                    }
                    else if(input.equalsIgnoreCase("event")) {
                        if(!(currentUser instanceof Organizer))
                            throw new Exception("You must have organizer access to delete an event!");
                        else {
                            System.out.println("Event ID: ");
                            String eventID = scanner.nextLine().trim();
                            Event event = Event.FindEvent(Database.GetEvents(), Integer.parseInt(eventID));
                            if (event.GetOrganizer().GetUsername().equals(currentUser.GetUsername())) {
                                ((Organizer) currentUser).CancelEvent(event);
                            }
                            else {
                                throw new Exception("This event is not yours!");
                            }
                        }
                    }
                }
                else if (input.equalsIgnoreCase("update")) {
                    if(currentUser == null)
                        throw new Exception("You are not logged in!");
                    System.out.println("What would you like to update? ");
                    input = scanner.nextLine().trim();
                    if(input.equalsIgnoreCase("room")) {
                        if(!(currentUser instanceof Admin))
                            throw new Exception("You must be an admin to edit room information!");
                        System.out.println("Room ID: ");
                        String roomID = scanner.nextLine().trim();
                        Room room = Objects.requireNonNull(Room.FindRoom(Database.GetRooms(), Integer.parseInt(roomID)));
                        System.out.println("What would you like to do? ");
                        input = scanner.nextLine().trim();
                        if (input.equalsIgnoreCase("addtime")) {
                            System.out.println("Day: ");
                            String day = scanner.nextLine().trim();
                            System.out.println("Time range: ");
                            String timeRange = scanner.nextLine().trim();
                            String[] range = timeRange.split("-");
                            room.AddAvailableHours(new TimeRange(range[0].trim(), range[1].trim()), Objects.requireNonNull(Day.Translate(Day.days.get(day))).toString());
                        }
                        else if (input.equalsIgnoreCase("removetime")) {
                            System.out.println("Day: ");
                            String day = scanner.nextLine().trim();
                            System.out.println("Time range: ");
                            String timeRange = scanner.nextLine().trim();
                            String[] range = timeRange.split("-");
                            room.RemoveAvailableHours(new TimeRange(range[0].trim(), range[1].trim()), Objects.requireNonNull(Day.Translate(Day.days.get(day))).toString());
                        }
                    }
                    else if(input.equalsIgnoreCase("event")) {
                        if(!(currentUser instanceof Organizer))
                            throw new Exception("You must be an organizer to edit event information!");
                        System.out.println("Event ID: ");
                        String eventID = scanner.nextLine().trim();
                        Event event = Objects.requireNonNull(Event.FindEvent(Database.GetEvents(), Integer.parseInt(eventID)));
                        System.out.println("What would you like to edit? ");
                        input = scanner.nextLine().trim();
                        if (input.equalsIgnoreCase("room")) {
                            System.out.println("New Room ID: ");
                            String roomID = scanner.nextLine().trim();
                            ((Organizer) currentUser).EditEventRoom(event, Integer.parseInt(roomID));
                        }
                        else if (input.equalsIgnoreCase("category")) {
                            System.out.println("New Category: ");
                            String category = scanner.nextLine().trim();
                            ((Organizer) currentUser).EditEventCategory(event, new Category(category));
                        }
                        if (input.equalsIgnoreCase("date")) {
                            System.out.println("New Date (dd/mm/yyyy): ");
                            String date = scanner.nextLine().trim();
                            ((Organizer) currentUser).EditEventDate(event, new MyDate(date));
                        }
                        else if (input.equalsIgnoreCase("time")) {
                            System.out.println("New Event time: ");
                            String time = scanner.nextLine().trim();
                            String[] range = time.split("-");
                            event.EditTimeRange(new TimeRange(range[0].trim(), range[1].trim()));
                        }
                        else if (input.equalsIgnoreCase("price")) {
                            System.out.println("Enter new price: ");
                            String price = scanner.nextLine().trim();
                            ((Organizer) currentUser).EditEventPrice(event, Double.parseDouble(price));
                        }
                    }
                    else if(input.equalsIgnoreCase("wallet")) {
                        if(currentUser instanceof Attendee) {
                            System.out.println("Deposit amount: ");
                            String deposit = scanner.nextLine().trim();
                            ((Attendee) currentUser).GetWallet().EditBalance(Double.parseDouble(deposit));
                        }
                        else if(currentUser instanceof Organizer) {
                            System.out.println("Deposit amount: ");
                            String deposit = scanner.nextLine().trim();
                            ((Organizer) currentUser).GetWallet().EditBalance(Double.parseDouble(deposit));
                        }
                        else {
                            throw new Exception("Your account does not contain a wallet!");
                        }
                    }
                }
                else if (input.equalsIgnoreCase("purchase")) {
                    if(currentUser == null)
                        throw new Exception("You are not logged in!");
                    else if(!(currentUser instanceof Attendee))
                        throw new Exception("You must have attendee access to purchase a ticket");
                    else {
                        System.out.println("Event ID: ");
                        String eventID = scanner.nextLine().trim();
                        System.out.println("Number of Tickets: ");
                        String numOfTickets = scanner.nextLine().trim();
                        ((Attendee) currentUser).PurchaseEvent(Objects.requireNonNull(Event.FindEvent(Database.GetEvents(), Integer.parseInt(eventID))), Integer.parseInt(numOfTickets));
                    }
                }
                else if(input.equalsIgnoreCase("search")) {
                    System.out.println("What would you like to search? ");
                    input = scanner.nextLine().trim();
                    if(input.equalsIgnoreCase("user")) {
                        System.out.println("Search by username: ");
                        input = scanner.nextLine().trim();
                        ArrayList<User> users = User.GetUsers();
                        for (User user : users) {
                            if (user.GetUsername().contains(input))
                                System.out.println(user);
                        }
                    }
                    else if(input.equalsIgnoreCase("event")) {
                        System.out.println("What would you like to search by: ");
                        input = scanner.nextLine().trim();
                        if (input.equalsIgnoreCase("title")) {
                            System.out.println("Search by title: ");
                            input = scanner.nextLine().trim();
                            ArrayList<Event> events = Database.GetEvents();
                            for(Event event : events)
                                if (event.GetEventTitle().contains(input))
                                    System.out.println(event);
                        }
                        else if(input.equalsIgnoreCase("category")) {
                            System.out.println("Search by category: ");
                            input = scanner.nextLine().trim();
                            ArrayList<Event> events = Database.GetEvents();
                            for (Event event : events)
                                if (event.GetCategory().toString().contains(input))
                                    System.out.println(event);
                        }
                        else if(input.equalsIgnoreCase("date")) {
                            System.out.println("Search by date (dd/mm/yyyy) : ");
                            input = scanner.nextLine().trim();
                            MyDate date = new MyDate(input);
                            ArrayList<Event> events = Database.GetEvents();
                            for (Event event : events)
                                if (event.GetDate().equals(date))
                                    System.out.println(event);
                        }
                    }
                    else if(input.equalsIgnoreCase("room")) {
                        System.out.println("Search by room number: ");
                        input = scanner.nextLine().trim();
                        ArrayList<Room> rooms = Database.GetRooms();
                        for (Room room : rooms)
                            if (room.GetID() == Integer.parseInt(input))
                                System.out.println(room);
                    }
                    else if(input.equalsIgnoreCase("category")) {
                        System.out.println("Search category: ");
                        input = scanner.nextLine().trim();
                        ArrayList<String> categories = Database.GetCategories();
                        for (String category : categories)
                            if (category.contains(input))
                                System.out.println(category);
                    }
                }
                else if(input.equalsIgnoreCase("print")) {
                    ArrayList<User> users = User.GetUsers();
                    ArrayList<Room> rooms = Database.GetRooms();
                    ArrayList<Event> events = Database.GetEvents();
                    ArrayList<String> categories = Database.GetCategories();
                    System.out.println("Users: ");
                    for (User user : users)
                        System.out.println(user);
                    System.out.println("\nRooms: ");
                    for (Room room : rooms)
                        System.out.println(room);
                    System.out.println("\nEvents: ");
                    for (Event event : events)
                        System.out.println(event);
                    System.out.println("\nCategories: ");
                    System.out.println(categories + "\n");

                }
                else if(input.equalsIgnoreCase("quit"))
                    break;
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Thank You for using this application!");
        Database.CloseConnection();
    }
}