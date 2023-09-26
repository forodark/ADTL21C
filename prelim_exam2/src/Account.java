public class Account {
    private String account_number;
    private String name;
    private double balance;

    public Account(String account_number, String name, double balance) {
        this.account_number = account_number;
        this.name = name;
        this.balance = balance;
    }

    public String getAccount_number() {
        return account_number;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }
}
