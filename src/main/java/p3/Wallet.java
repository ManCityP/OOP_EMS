package p3;

import p1.Database;
import java.util.ArrayList;

public class Wallet {
    private double balance;                 //Almost there
    private final int WALLET_NUMBER;

    public Wallet(double balance,int walletNumber) throws Exception {
        if(balance < 0)
            throw new Exception("Invalid Balance");
        if(walletNumber <= 0)
            throw new Exception("Invalid wallet number");
        this.balance = balance;
        this.WALLET_NUMBER = walletNumber;
    }

    public  static void CreateWallet(String username, double balance) throws Exception {
        Database.Execute(String.format("INSERT INTO wallet (username, balance) VALUES ('%s', '%s')", username, balance));
    }

    public static Wallet FindWallet(ArrayList<Wallet> wallets, int walletNumber){
        for (Wallet wallet : wallets)
            if (wallet.WALLET_NUMBER == walletNumber)
                return wallet;

        return null;
    }

    public void EditBalance(double amount) throws Exception {
        if (amount < 0)
            if (Math.abs(amount) > this.balance)
                throw new Exception("No available balance");
        this.balance += amount;
        Database.Execute(String.format("UPDATE wallet SET balance = '%s' WHERE (id = '%s')", this.balance, this.WALLET_NUMBER));
    }

    public double GetBalance(){
        return this.balance;
    }
    public int GetWalletNumber(){
        return this.WALLET_NUMBER;
    }
    @Override
    public String toString(){
        return  "Wallet number: " + this.WALLET_NUMBER +
                "\tBalance: " + this.balance;
    }
}
