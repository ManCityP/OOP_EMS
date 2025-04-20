package p3;

import p1.Admin;
import p2.Event;
import p2.Organizer;

import java.util.ArrayList;

public class Wallet {
    double balance; //TODO Make data field private
    int walletNumber;

    public Wallet(){}
    public Wallet(double balance,int walletNumber){
        this.balance = balance;
        this.walletNumber = walletNumber;
    }

    public static Wallet FindWallet(ArrayList<Wallet> wallets, int walletNumber){
        for (Wallet wallet : wallets)
            if (wallet.walletNumber == walletNumber)
                return wallet;

        return null;
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

    @Override
    public String toString(){
        return  "Wallet number: " + this.walletNumber +
                "\tBalance: " + this.balance;
    }
}
