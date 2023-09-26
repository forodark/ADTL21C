import glenlib.Style;
import glenlib.In;
import glenlib.Util;

public class Main {
    public static void main(String[] args) throws Exception {
        banking();
    }

    static Account[] accounts = {
        new Account("232323", "Perry tail", 2000),
    };

    public static void banking() {
        Util.clear();

        Style.println(Style.color(Style.GREEN, "Account No.: ") + accounts[0].getAccount_number());
        Style.println(Style.color(Style.GREEN, "Name: ") + accounts[0].getName());
        Style.println(Style.color(Style.GREEN, "Balance: ") + accounts[0].getBalance());

        double amount = In.getDouble("Withrawal? ");
        if (amount > accounts[0].getBalance()) {
            Style.println(Style.color(Style.RED, "Withdrawal failed. Insufficient funds."));
        } else {
            accounts[0].withdraw(amount);
            Style.println(Style.color(Style.GREEN, "Withdrawal successful."));
            Style.println(Style.color(Style.GREEN, "New balance: Php" + accounts[0].getBalance()));
        }

    }

}
