/*
 * To do:
 * create accounts //
 * add product / remove products //
 * query products //
 * check out //
 * sorting?
 * total price//
 * seller menu //
 * change all names of items sellers customers tables menus
 * preview of item count in cart//
 * remove from cart//
 * shipping fee?
 * vouchers?
 * make interface wider maybe//
 * store data in txt file //
 * add .00 to all prices and values //
 * transac history?
 * glencrypt?
 * store description //
 * money paid gets transferred to seller //
 */

import java.util.Arrays;

import glenlib.*;

public class Main {

    public static Seller[] sellers = new Seller[Data.countSellers()];
    public static Customer[] customers = new Customer[Data.countCustomers()];

    public static int account_type = -1; // 0 for seller, 1 for customer

    public static final int INTERFACE_WIDTH = 79;

    public static void main(String[] args) {
        Data.load();
        mainMenu();
        Data.save();
        Util.exit("Thanks for shopping!", INTERFACE_WIDTH);
    }

    public static void mainMenu() {
        MenuItem[] main_menu = {
            new Option("Login as Seller", () -> SellMenu.login()),
            new Option("Login as Customer", () -> CustMenu.login()),
            new Option("Register", () -> createAccount()),
        };
        Menu.showMenu("Main Menu", main_menu, INTERFACE_WIDTH);
    }


    public static void createAccount() {
        while(true) {
            Util.clear();
            Style.printTitle(INTERFACE_WIDTH, "Register an Account");
            int type = In.getInt("Are you a [1] Seller or a [2] Customer? (0 to return): ", 0 , 2);

            switch(type) {
                case 1:
                    createAccount_Seller();
                    break;
                case 2:
                    createAccount_Customer();
                    break;
                case 0:
                    Menu.dontWait();
                    return;
                default:
                    Util.invalid("Invalid selection\n", INTERFACE_WIDTH);
                    continue;
            }
            break;
        }
    }

    public static void createAccount_Seller() {

        
        String name = In.getString("Enter name: ");
        String email = In.getString("Enter email: ");
        String password = In.getString("Enter password: ");
        double balance = 0.0;

        String seller_id = String.format("S%03d", sellers.length+1);


        sellers = Arrays.copyOf(sellers, sellers.length + 1);
        sellers[sellers.length - 1] = new Seller(name, email, password, balance, seller_id);

        Style.printColor(Style.GREEN, "Successfully created account.%n");
    }

    public static void createAccount_Customer() {
        String name = In.getString("Enter name: ");
        String email = In.getString("Enter email: ");
        String password = In.getString("Enter password: ");
        double balance = 0.0;

        String customer_id = String.format("C%03d", customers.length+1);


        customers = Arrays.copyOf(customers, customers.length + 1);
        customers[customers.length - 1] = new Customer(name, email, password, balance, customer_id);

        Style.printColor(Style.GREEN, "Successfully created account.%n");
    }


    public static void logout() {
        SellMenu.selected_account = -1;
        CustMenu.selected_account = -1;
        account_type = -1;
        mainMenu();
    }

    

    
}
