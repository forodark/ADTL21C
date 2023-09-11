public class Main {
    public static void main(String[] args) {
        Account[] accounts = new Account[3];

        //account data management using constructor
        accounts[0] = new Account("0001", "Glen Bautista", 0.0);

        //or using public methods
        accounts[1] = new Account();
        accounts[1].setAccount_number("0002");
        accounts[1].setName("Happy");
        accounts[1].setBalance(1500.0);

        //or both
        accounts[2] = new Account("0003", "Aki", 0.0);
        accounts[2].setBalance(2000.0);

        //print account data
        for (int i = 0; i < accounts.length; i++) {
                accounts[i].printInfo();
        }
    }
}
