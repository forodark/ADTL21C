import glenlib.*;

public class Main {
    public static Product[] all_products = new Product[] {
        new Product("P001", "Apple", 100.00),
        new Product("P002", "Banana", 200.00),
        new Product("P003", "Orange", 300.00)
    };

    public static Customer[] customers = new Customer[] {
        new Customer("Glen", "glen@example.com", "password1", "C001"),
        new Customer("Happy", "happy@example.com", "password2", "C002"),
        new Customer("Aki", "aki@example.com", "password3", "C003")
    };

    public static Seller[] sellers = new Seller[] {
        new Seller("Seller1", "seller1@example.com", "password1", "S001"),
        new Seller("Seller2", "seller2@example.com", "password2", "S002"),
        new Seller("Seller3", "seller3@example.com", "password3", "S003")
    };

    public static int logged_in = -1;

    public static final int INTERFACE_WIDTH = 63;

    public static void main(String[] args) {

        loginCustomer();
        


    }

    public static void loginCustomer() {
        logged_in = -1;
        int i = 0;
        while(true) {
            String input_email = In.getString("Enter your email (0 to return): ");
            for (; i < customers.length; i++) {
                if (customers[i].getEmail().equals(input_email)) {
                    logged_in = i;
                    break;
                }
            }
            if (logged_in == -1) {
                Style.printColor(Style.RED, "Invalid email.%n");
            }
            else {
                break;
            }
        }

        while(true) {
            String input_password = In.getString("Enter your password (0 to return): ");
            if (customers[i].getPassword().equals(input_password)) {
                break;
            }
            Style.printColor(Style.RED, "Invalid email.%n");
        }

        mainMenuCustomer();

    }

    public static void loginSeller() {

        String input_email = In.getString("Enter your email");
        for (int i = 0; i < customers.length; i++) {
            if (customers[i].getEmail().equals(input_email)) {
                logged_in = i;
                break;
            }
        }
    }

    public static void mainMenuCustomer() {
        Style.line(INTERFACE_WIDTH);
        
    }
    
}
