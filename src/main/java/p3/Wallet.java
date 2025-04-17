package p3;

import p2.Event;
import p2.Organizer;

public class Wallet {
    double balance;
    int walletNumber;

    Wallet(){}
    Wallet(double balance,int walletNumber){
        this.balance = balance;
        this.walletNumber = walletNumber;
    }

    public void EditBalance(double amount) throws Exception {
        if (amount < 0)
            if (Math.abs(amount) > this.balance)
                throw new Exception("No available balance");
        this.balance += amount;
    }

    public void PurchaseEvent(Event event, Organizer organizer) throws Exception{
        if(this.balance < event.GetPrice())
            throw new Exception("Invalid balance");
        else{
            this.balance -= event.GetPrice();
            organizer.EditWalletBalance(event.GetPrice());
        }
    }
}