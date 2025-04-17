package p2;

import p3.User;
import p3.Wallet;

public class Organizer extends User {
    Wallet wallet;

    public void EditWalletBalance(double amount){
        this.wallet.EditBalance(amount);
    }
}
