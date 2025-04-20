package p3;

import p1.Admin;
import p1.Database;
import p2.Event;
import p2.Organizer;

import java.util.ArrayList;

public class Wallet {
    private double balance;                 //Almost there
    private final int walletNumber;

    public Wallet(double balance,int walletNumber) throws Exception {
        if(balance < 0)
            throw new Exception("Invalid Balance");
        if(walletNumber <= 0)
            throw new Exception("Invalid wallet number");
        this.balance = balance;
        this.walletNumber = walletNumber;
    }

    public  static void CreateWallet(String username, double balance){
        Database.Execute(String.format("INSERT INTO wallet (username, balance) VALUES ('%s', '%s')", username, balance));
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

    public double GetBalance(){
        return this.balance;
    }

    @Override
    public String toString(){
        return  "Wallet number: " + this.walletNumber +
                "\tBalance: " + this.balance;
    }
}
