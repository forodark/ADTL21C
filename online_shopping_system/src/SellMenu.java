import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import glenlib.*;

public class SellMenu {

    public static int selected_account = -1;
    public static int selected_product = -1;


    public static void login() {
        Util.clear();
        selected_account = -1;
        int i;

            Style.printTitle(Main.INTERFACE_WIDTH, "Seller Login");

        while(true) {
            String input_email = In.getString("Enter your email (0 to return): ");

            if (input_email.equals("0")) {
                Menu.dontWait();
                return;
            }

            for (i = 0; i < Main.sellers.length; i++) {
                if (Main.sellers[i].getEmail().equals(input_email)) {
                    selected_account = i;
                    break;
                }
            }
            if (selected_account == -1) {
                Style.printColor(Style.RED, "Invalid email.%n");
            }
            else {
                break;
            }
        }

        while(true) {
            String input_password = In.getString("Enter your password (0 to return): ");
            
            if (input_password.equals("0")) {
                Menu.dontWait();
                return;
            }

            if (Main.sellers[selected_account].getPassword().equals(input_password)) {
                break;
            }
            Style.printColor(Style.RED, "Incorrect password.%n");
        }
        Main.account_type = 0;
        mainMenu();

    }   

    public static void mainMenu() {
        MenuItem[] main_menu = {
            new Option("Browse Sellers", () -> CustMenu.browseSellers()),
            new Option("View All Products", () -> CustMenu.viewProducts()),
            new Option("Search Products", () -> CustMenu.searchProducts()),
            new Option("View Wallet", () -> viewWallet()),
            new Option("Edit Store", () -> editStore())
        };

        Menu.showMenu("Welcome, " + Main.sellers[selected_account].getName(), main_menu, Main.INTERFACE_WIDTH);
    }

    public static void viewWallet() {
        while(true) {
            Util.clear();
            Style.printTitle(Main.INTERFACE_WIDTH, "My Wallet");
            Style.println("Account Name: " + Main.sellers[selected_account].getName());
            Style.println("Balance: " + Str.setPrecision(Main.sellers[selected_account].getBalance(),2));
            Style.line(Main.INTERFACE_WIDTH);
            Style.println("[1] Cash in   [2] Cash out   [0] Return");
            Style.line(Main.INTERFACE_WIDTH);
            int choice = In.getInt("Enter choice: ");
            switch(choice) {
                case 1:
                    double cash_in = In.getDouble("Enter amount to cash in: ");
                    Main.sellers[selected_account].setBalance(Main.sellers[selected_account].getBalance() + cash_in);
                    Style.printColor(Style.GREEN, "Successfully cashed in %.2f. New balance: %.2f.%n", cash_in, Main.sellers[selected_account].getBalance());
                    In.waitEnter();
                    break;
                case 2:
                    double cash_out = In.getDouble("Enter amount to cash out: ");
                    if (Main.customers[selected_account].getBalance() < cash_out) {
                        Style.printColor(Style.RED, "Insufficient balance.%n");
                        In.waitEnter();
                        continue;
                    }
                    Main.sellers[selected_account].setBalance(Main.sellers[selected_account].getBalance() - cash_out);
                    Style.printColor(Style.GREEN, "Successfully cashed out %.2f. New balance: %.2f.%n", cash_out, Main.sellers[selected_account].getBalance());
                    In.waitEnter();
                    break;
                case 0:
                    Menu.dontWait();
                    return;
                default:
                    Util.invalid(Util.INVALID, Main.INTERFACE_WIDTH);
                    continue;
            }
        }
    }

    public static void editStore() {
        while(true) {
            Util.clear();
            Style.printTitle(Main.INTERFACE_WIDTH, "Store Editor");
            Style.println("Account Name: " + Main.sellers[selected_account].getName());
            Style.println("No. of Products: " + Main.sellers[selected_account].getProduct_count());
            Style.println("Balance: " + Str.setPrecision(Main.sellers[selected_account].getBalance(),2));
            Style.line(Main.INTERFACE_WIDTH);
            Style.println("[1] Preview Store   [2] Edit Products   [3] New Product");
            Style.println("[4] Edit Description   [0] Return");
            Style.line(Main.INTERFACE_WIDTH);
            int choice = In.getInt("Enter choice: ");
            switch(choice) {
                case 1:
                    CustMenu.selected_seller = selected_account;
                    CustMenu.viewStore();
                    CustMenu.selected_seller = -1;
                    break;
                case 2:
                    editProducts();
                    break;
                case 3:
                    createProduct();
                    break;
                case 4:
                    String description = In.getString("Enter new description:\n > ");
                    Main.sellers[selected_account].setDescription(description);
                    Style.printColor(Style.GREEN, "Successfully updated description.%n");
                    break;
                case 0:
                    Menu.dontWait();
                    return;
                default:
                    Util.invalid(Util.INVALID, Main.INTERFACE_WIDTH);
                    continue;    

            }
        }   
    }

    public static void editProducts() {
        while(true) {
            List<Product> data = Arrays.asList(Main.sellers[selected_account].getProducts());

            List<TableColumn<?>> columns = new ArrayList<>();
            columns.add(new TableColumn<>("Product ID", data, "%15s", "getProduct_id"));
            columns.add(new TableColumn<>("Product Name", data, "%35s", "getProduct_name"));
            columns.add(new TableColumn<>("Price", data, "%15.2lf", "getPrice"));
            Table table_edit_products = new Table(columns);

            table_edit_products.printFull(Main.sellers[selected_account].getName() + "'s Store - Edit Products");

            String choice = In.getString("Enter Product ID (0 to return): ");

            if (choice.equals("0")) {
                Menu.dontWait();
                return;
            }

            for (int i = 0; i < Main.sellers[selected_account].getProducts().length; i++) {
                if (Main.sellers[selected_account].getProducts()[i].getProduct_id().equals(choice)) {
                    selected_product = i;
                    break;
                }
            }
            if (selected_product == -1) {
                Style.printColor(Style.RED, "Invalid selection.%n");
                In.waitEnter();
            }
            else {
                editProduct(Main.sellers[selected_account].getProducts()[selected_product]);
            }
        }
    }

    public static void editProduct(Product product) {
        while(true) {
            Util.clear();
            Style.printTitle(Main.INTERFACE_WIDTH, "Editing Product: " + Main.sellers[selected_account].getProducts()[selected_product].getProduct_name());
            Style.println("Product ID: " + product.getProduct_id());
            Style.println("Product Name: " + product.getProduct_name());
            Style.println("Price: " + Str.setPrecision(product.getPrice(), 2));
            Style.println("Seller: " + product.getSeller_name());
            Style.line(Main.INTERFACE_WIDTH);
            if (!product.getDescription().isEmpty()) {
                Style.println("Description:\n" + product.getDescription());
                Style.line(Main.INTERFACE_WIDTH);
            }
            Style.println("[1] Edit Product Name  [2] Edit Price");
            Style.println("[3] Edit Store Description  [4] Remove Product  [0] Return");
            Style.line(Main.INTERFACE_WIDTH);
            int choice = In.getInt("Enter choice: ");
            switch(choice) {
                case 1:
                    String name = In.getString("Enter new product name:\n > ");
                    product.setProduct_name(name);
                    Style.printColor(Style.GREEN, "Successfully updated product name.%n");
                    In.waitEnter();
                    break;
                case 2:
                    Double price = In.getDouble("Enter new price:\n > ");
                    product.setPrice(price);
                    Style.printColor(Style.GREEN, "Successfully updated price.%n");
                    In.waitEnter();
                    break;
                case 3:
                    String description = In.getString("Enter new description:\n > ");
                    product.setDescription(description);
                    Style.printColor(Style.GREEN, "Successfully updated description.%n");
                    In.waitEnter();
                    break;
                case 4:
                    Main.sellers[selected_account].removeProduct(product.getProduct_id());
                    Style.printColor(Style.GREEN, "Successfully removed product.%n");
                    In.waitEnter();
                    selected_product = -1;
                    return;
                case 0:
                    selected_product = -1;
                    Menu.dontWait();
                    return;
                default:
                    Util.invalid(Util.INVALID, Main.INTERFACE_WIDTH);
                    continue;
            }
            
        }

    }

    public static void createProduct() {
        Util.clear();
        Style.printTitle(Main.INTERFACE_WIDTH, "Create Product");
        String id = Product.getAvailableId();
        String name = In.getString("Enter product name:\n > ");
        Double price = In.getDouble("Enter price:\n > ");
        String description = In.getString("Enter description:\n > ");
        Main.sellers[selected_account].addProduct(new Product(id, name, price, description));

        Style.printColor(Style.GREEN, "Successfully added product.%n");
        In.waitEnter();
    }


}
