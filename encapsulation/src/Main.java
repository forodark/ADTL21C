import glenlib.*;

public class Main {
    static Account[] accounts = new Account[] {
        new Account("0001", "Glen Bautista", 1000.0),
        new Account("0002", "Happy", 1500.0),
        new Account("0003", "Aki", 2000.0)
    };
    
    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu() {
        MenuItem[] main_menu = new MenuItem[] {
            new Option("List accounts", () -> listAccounts()), //list accounts to demonstrate getters
            new Option("Edit account", () -> editAccount()) //edit details to demonstrate setters
        };
        Menu.showMenu("Main Menu", main_menu, 44);
    }

    public static void listAccounts() {
        new Tbl<Account>()
        .Array(accounts)
        .Col("Acc No.", "%10s", "getAccount_number")
        .Col("Name", "%20s", "getName")
        .Col("Balancee", "%12.2f", "getBalance")
        .Title("Accounts")
        .build();
    }

    public static void editAccount() {
        Util.clear();
        Style.printTitle(44, "Edit Account");
        Account account;
        if ((account = Account.getAccByNum(In.getString("Enter account number: "), accounts)) == null) {
            Style.printColor(Style.RED, "Account not found."); return;
        }
        MenuItem[] edit_menu = new MenuItem[] {
            new Option("Change Name", () -> {
                account.setName(In.getString("Enter Name: "));
                Style.printColor(Style.GREEN, "Successfully updated name.%n");
            }),
            new Option("Withdraw", () -> {
                double amount = In.getDouble("Enter Amount: ");
                if (account.getBalance() < amount) {
                    Style.printColor(Style.RED, "Insufficient funds.%n"); return;
                } 
                account.setBalance(account.getBalance() - amount);
                Style.printColor(Style.GREEN, "Successfully withdrawn %.2lf.%n", amount);
            }),
            new Option("Deposit", () -> {
                double amount = In.getDouble("Enter Amount: ");
                account.setBalance(amount);
                Style.printColor(Style.GREEN, "Successfully deposited %.2lf.%n", amount);
            })
        };
        Menu.showMenu("Editing Account - " + account.getAccount_number(), edit_menu, 44);
    }

}

