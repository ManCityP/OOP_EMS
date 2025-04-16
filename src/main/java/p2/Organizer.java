package p2;

import p3.Wallet;

public class Organizer {
    Wallet wallet;

    public void EditWalletBalance(double amount){
        this.wallet.EditBalance(amount);
    }
}
