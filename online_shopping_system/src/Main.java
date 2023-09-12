/*
 * To do:
 * create accounts
 * add product
 * 
 */



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import glenlib.*;

public class Main {
    public static Product[] all_products = new Product[] {
        new Product("P001", "Apple", 100.00),
        new Product("P002", "Banana", 200.00),
        new Product("P003", "Orange", 300.00)
    };

    public static Customer[] customers = new Customer[] {
        new Customer("Glen", "glen", "1234", "C001"),
        new Customer("Happy", "happy@example.com", "password2", "C002"),
        new Customer("Aki", "aki@example.com", "password3", "C003")
    };

    public static Seller[] sellers = new Seller[] {
        new Seller("Seller1", "seller1@example.com", "password1", "S001"),
        new Seller("Seller2", "seller2@example.com", "password2", "S002"),
        new Seller("Seller3", "seller3@example.com", "password3", "S003")
    };

    public static int selected = -1;

    public static final int INTERFACE_WIDTH = 63;

    public static void main(String[] args) {

        mainMenu();


    }

    public static void mainMenu() {
        MenuItem[] main_menu = {
            new Option("Login as Customer", () -> loginCustomer()),
            new Option("Login as Seller", () -> loginSeller()),
            new Option("Exit", () -> Util.exit())
        };

        Menu.showMenu("Main Menu", main_menu);
    }

    public static void loginCustomer() {
        Util.clear();
        selected = -1;
        int i;

        while(true) {
            String input_email = In.getString("Enter your email (0 to return): ");

            if (input_email == "0") {
                return;
            }

            for (i = 0; i < customers.length; i++) {
                if (customers[i].getEmail().equals(input_email)) {
                    selected = i;
                    break;
                }
            }
            if (selected == -1) {
                Style.printColor(Style.RED, "Invalid email.%n");
            }
            else {
                break;
            }
        }

        while(true) {
            String input_password = In.getString("Enter your password (0 to return): ");
            
            if (input_password == "0") {
                return;
            }

            if (customers[selected].getPassword().equals(input_password)) {
                break;
            }
            Style.printColor(Style.RED, "Incorrect password.%n");
        }

        mainMenuCustomer();

    }

    public static void mainMenuCustomer() {
        Util.clear();
        Style.line(INTERFACE_WIDTH);
        Style.printf("Welcome, %s", customers[selected].getName());
        Style.print("");

        MenuItem[] main_menu = {
            new Option("Browse Sellers", () -> browseSellers()),
            new Option("View Products", () -> viewProducts()),
            new Option("View Cart", () -> viewCart()),
            new Option("Logout", () -> logout())
        };

        Menu.showMenu("", main_menu);
    }

    public static void browseSellers() {
        Util.clear();
        List<Seller> data = Arrays.asList(sellers);

        List<TableColumn<?>> columns = new ArrayList<>();
        columns.add(new TableColumn<>("Seller ID", data, "%12s", "getSeller_id"));
        columns.add(new TableColumn<>("Name", data, "%16s", "getName"));
        columns.add(new TableColumn<>("# Products", data, "%12f", "getProduct_count"));
        Table TEST = new Table(columns);

        TEST.printFull("Sample Table");
    }

    public static void viewProducts() {
        
    }

    public static void viewCart() {

    }

    public static void loginSeller() {

    }

    public static void logout() {
        selected = -1;
        mainMenu();
    }


    
}
