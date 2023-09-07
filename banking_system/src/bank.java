import java.io.*;
import java.util.*;
import glenlib.cosmetic;

public class bank {
    private static final String ACCOUNTS_FILE = "bin/accounts.txt";
    private static Map<String, Account> accounts = new HashMap<>();
    private static String currentUser = null;

    public static void main(String[] args) {
        loadAccountsFromFile();
        showWelcomeMessage();
        login();
    }

    private static void loadAccountsFromFile() {
        try (Scanner fileScanner = new Scanner(new File(ACCOUNTS_FILE))) {
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(",");
                if (parts.length == 4) {
                    String accountNumber = parts[0];
                    String password = parts[1];
                    String name = parts[2];
                    double balance = Double.parseDouble(parts[3]);
                    accounts.put(accountNumber, new Account(accountNumber, password, name, balance));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void showWelcomeMessage() {
        cosmetic.printColor(cosmetic.RED, "Welcome to Glen's Banking System!");
    }

    private static void login() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter your account number: ");
            String accountNumber = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (accounts.containsKey(accountNumber)) {
                Account account = accounts.get(accountNumber);
                if (account.getPassword().equals(password)) {
                    currentUser = accountNumber;
                    System.out.println("Welcome, " + account.getName() + "!");
                    showMenu();
                } else {
                    System.out.println("Incorrect password. Please try again.");
                    login();
                }
            } else {
                System.out.println("Account not found. Please check your account number.");
                login();
            }
        }
        finally {
            scanner.close();
        }
    }

    private static void showMenu() {
        System.out.println("Select an option:");
        System.out.println("1. Balance Inquiry");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer Funds");
        System.out.println("5. Logout");

        Scanner scanner = new Scanner(System.in);
        try {
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transferFunds();
                    break;
                case 5:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    showMenu();
            }
        }
        finally {
            scanner.close();
        }
    }

    private static void checkBalance() {
        Account account = accounts.get(currentUser);
        System.out.println("Your balance is: $" + account.getBalance());
        continueTransaction();
    }

    private static void withdraw() {
        Account account = accounts.get(currentUser);
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the amount to withdraw: $");
            double amount = scanner.nextDouble();

            if (amount <= account.getBalance()) {
                account.setBalance(account.getBalance() - amount);
                System.out.println("Withdrawal successful. Your new balance is: $" + account.getBalance());
            } else {
                System.out.println("Insufficient funds. Please try a lower amount.");
            }
            continueTransaction();
        }
        finally {
            scanner.close();
        }
    }

    private static void deposit() {
        Account account = accounts.get(currentUser);
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the amount to deposit: $");
            double amount = scanner.nextDouble();

            if (amount > 0) {
                account.setBalance(account.getBalance() + amount);
                System.out.println("Deposit successful. Your new balance is: $" + account.getBalance());
            } else {
                System.out.println("Invalid amount. Please enter a positive value.");
            }
            continueTransaction();
        }
        finally {
            scanner.close();
        }
    }

    private static void transferFunds() {
        Account sender = accounts.get(currentUser);
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the recipient's account number: ");
            String recipientAccountNumber = scanner.nextLine();

            if (accounts.containsKey(recipientAccountNumber)) {
                System.out.print("Enter the amount to transfer: $");
                double amount = scanner.nextDouble();

                if (amount > 0 && amount <= sender.getBalance()) {
                    Account recipient = accounts.get(recipientAccountNumber);
                    sender.setBalance(sender.getBalance() - amount);
                    recipient.setBalance(recipient.getBalance() + amount);
                    System.out.println("Transfer successful.");
                } else {
                    System.out.println("Invalid amount or insufficient funds.");
                }
            } else {
                System.out.println("Recipient account not found. Please check the account number.");
            }
            continueTransaction();
        }
        finally {
            scanner.close();
        }
    }

    private static void logout() {
        saveAccountsToFile();
        currentUser = null;
        System.out.println("Logout successful. Thank you for using our banking system!");
    }

    private static void saveAccountsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account account : accounts.values()) {
                writer.println(account.getAccountNumber() + "," + account.getPassword() + "," +
                        account.getName() + "," + account.getBalance());
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void continueTransaction() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Do you want to perform another transaction? (yes/no): ");
            String choice = scanner.nextLine().toLowerCase();
            if (choice.equals("yes")) {
                showMenu();
            } else if (choice.equals("no")) {
                logout();
            } else {
                System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
                continueTransaction();
            }
        }
        finally {
            
        }
    }
}

class Account {
    private String accountNumber;
    private String password;
    private String name;
    private double balance;

    public Account(String accountNumber, String password, String name, double balance) {
        this.accountNumber = accountNumber;
        this.password = password;
        this.name = name;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
