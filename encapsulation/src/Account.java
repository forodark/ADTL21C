public class Account {
    //private attributes
    private double balance;
    private String account_number;
    private String name;

    //public constructor
    public Account(String account_number, String name, double balance) {
        this.account_number = account_number;
        this.name = name;
        this.balance = balance; 
    }
    public Account() {}

    //public methods for accessing the data
    public double getBalance() {
        return balance;
    }
    public String getAccount_number() {
        return account_number;
    }
    public String getName() {
        return name;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }
    public void setName(String name) {
        this.name = name;
    }

    //method for retrieving an account from the account number (and source array)
    public static Account getAccByNum(String account_number, Account[] accounts) {
        for (Account account : accounts) {
            if (account.getAccount_number().equals(account_number)) {
                return account;
            }
        }
        return null;
    }

    

}
