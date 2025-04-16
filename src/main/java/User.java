import java.util.Date;

abstract public class User {

    String username="Default";
    String password="Default";
    Date dob = new Date(0);
    Wallet wallet;
    Gender gender = Gender.MALE;   ;

  //  static int numberOfUsers= 0;

User(){

   // numberOfUsers++;
}

User(String username, String password, Date dob, Wallet wallet, Gender gender){
this.username=username;
this.password=password;
this.dob=dob;
this.wallet=wallet;
this.gender=gender;

// numberOfUsers++;

}



}

enum Gender {
    MALE,
    FEMALE
}