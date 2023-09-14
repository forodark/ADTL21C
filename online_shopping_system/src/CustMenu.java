import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import glenlib.*;

public class CustMenu {

    public static int selected_account = -1;
    public static int selected_seller = -1;
    public static int selected_product = -1;

    public static void login() {
        Util.clear();
        selected_account = -1;
        int i;

            Style.printTitle(Main.INTERFACE_WIDTH, "Customer Login");

        while(true) {
            String input_email = In.getString("Enter your email (0 to return): ");

            if (input_email.equals("0")) {
                Menu.dontWait();
                return;
            }

            for (i = 0; i < Main.customers.length; i++) {
                if (Main.customers[i].getEmail().equals(input_email)) {
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

            if (Main.customers[selected_account].getPassword().equals(input_password)) {
                break;
            }
            Style.printColor(Style.RED, "Incorrect password.%n");
        }
        Main.account_type = 1;
        mainMenu();

    }

    public static void mainMenu() {
        MenuItem[] main_menu = {
            new Option("Browse Sellers", () -> browseSellers()),
            new Option("View All Products", () -> viewProducts()),
            new Option("Search Products", () -> searchProducts()),
            new Option("View Wallet", () -> viewWallet()),
            new Custom("/hide", () -> {Style.println("[5] View Cart (" + Main.customers[selected_account].getProduct_count() + ")");}),
            new Option("/hide", () -> viewCart())
        };

        Menu.showMenu("Welcome, " + Main.customers[selected_account].getName(), main_menu, Main.INTERFACE_WIDTH);
    }

    public static void browseSellers() {
        while(true) {
            List<Seller> data = Arrays.asList(Main.sellers);

            List<TableColumn<?>> columns = new ArrayList<>();
            columns.add(new TableColumn<>("Seller ID", data, "%15s", "getSeller_id"));
            columns.add(new TableColumn<>("Name", data, "%35s", "getName"));
            columns.add(new TableColumn<>("# Products", data, "%15d", "getProduct_count"));
            Table table_seller = new Table(columns);

            table_seller.printFull("List of Sellers");
            
            String choice = In.getString("Enter Seller ID (0 to return): ");

            if (choice.equals("0")) {
                Menu.dontWait();
                return;
            }


            for (int i = 0; i < Main.sellers.length; i++) {
                if (Main.sellers[i].getSeller_id().equals(choice)) {
                    selected_seller = i;
                    break;
                }
            }
            if (selected_seller == -1) {
                Style.printColor(Style.RED, "Invalid selection.%n");
                In.waitEnter();
            }
            else {
                viewStore();
            }

        }
    }

    public static void viewStore() {
        while(true) {
            List<Product> data = Arrays.asList(Main.sellers[selected_seller].getProducts());

            List<TableColumn<?>> columns = new ArrayList<>();
            columns.add(new TableColumn<>("Product ID", data, "%15s", "getProduct_id"));
            columns.add(new TableColumn<>("Product Name", data, "%35s", "getProduct_name"));
            columns.add(new TableColumn<>("Price", data, "%15.2lf", "getPrice"));
            Table table_store_products = new Table(columns);

            table_store_products.printFull(Main.sellers[selected_seller].getName(), Main.sellers[selected_seller].getDescription());

            String choice = In.getString("Enter Product ID (0 to return): ");

            if (choice.equals("0")) {
                selected_seller = -1;
                Menu.dontWait();
                return;
            }

            for (int i = 0; i < Main.sellers[selected_seller].getProducts().length; i++) {
                if (Main.sellers[selected_seller].getProducts()[i].getProduct_id().equals(choice)) {
                    selected_product = i;
                    break;
                }
            }
            if (selected_product == -1) {
                Style.printColor(Style.RED, "Invalid selection.%n");
                In.waitEnter();
            }
            else {
                viewProduct(Main.sellers[selected_seller].getProducts()[selected_product]);
            }
        }


    }

    public static void viewProduct(Product product) {
        while(true) {
            Util.clear();
            Style.printTitle(Main.INTERFACE_WIDTH, "Product Details");
            Style.println("Product ID: " + product.getProduct_id());
            Style.println("Product Name: " + product.getProduct_name());
            Style.println("Price: " + Str.setPrecision(product.getPrice(), 2));
            Style.println("Seller: " + product.getSeller_name());
            Style.line(Main.INTERFACE_WIDTH);
            if (!product.getDescription().isEmpty()) {
                Style.println("Description:\n" + product.getDescription());
                Style.line(Main.INTERFACE_WIDTH);
            }

            if (Main.account_type == 0) {
                In.waitEnter();
                return;
            }
            Style.println("[1] Add to Cart   [0] Return");
            Style.line(Main.INTERFACE_WIDTH);
            int choice = In.getInt("Enter choice: ");
            switch(choice) {
                case 1:

                    int quantity = In.getInt("Enter quantity: ", 0, 99);
                    if (quantity == 0) {
                        Menu.dontWait();
                        break;
                    }
                    Main.customers[selected_account].addToCart(product.getProduct_id(), quantity);
                    Main.customers[selected_account].setProduct_count(Main.customers[selected_account].getProduct_count() + quantity);
                    Style.line(Main.INTERFACE_WIDTH);
                    Style.printColor(Style.GREEN, "Successfully Added %d of %s to cart.%n", quantity, product.getProduct_name());
                    In.waitEnter();
                    break;
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


    public static void viewProducts() {

        while(true) {
            List<Product> data = Arrays.asList(Product.getAllProducts());

            List<TableColumn<?>> columns = new ArrayList<>();
            columns.add(new TableColumn<>("Product ID", data, "%12s", "getProduct_id"));
            columns.add(new TableColumn<>("Product Name", data, "%32s", "getProduct_name"));
            columns.add(new TableColumn<>("Price", data, "%14.2lf", "getPrice"));
            columns.add(new TableColumn<>("Seller", data, "%16s", "getSeller_name"));
            Table table_all_products = new Table(columns);

            table_all_products.printFull("All Products");

            String choice = In.getString("Enter Product ID (0 to return): ");

            if (choice.equals("0")) {
                Menu.dontWait();
                return;
            }

            for (int i = 0; i < Product.getAllProducts().length; i++) {
                if (Product.getAllProducts()[i].getProduct_id().equals(choice)) {
                    selected_product = i;
                    break;
                }
            }
            if (selected_product == -1) {
                Style.printColor(Style.RED, "Invalid selection.%n");
                In.waitEnter();
            }
            else {
                viewProduct(Product.getAllProducts()[selected_product]);
            }
        }
    }

    public static void viewCart() {
        while(true) {
            List<Product> data = Arrays.asList(Main.customers[selected_account].getCart());

            List<TableColumn<?>> columns = new ArrayList<>();
            columns.add(new TableColumn<>("Product ID", data, "%12s", "getProduct_id"));
            columns.add(new TableColumn<>("Product Name", data, "%24s", "getProduct_name"));
            columns.add(new TableColumn<>("Price/x", data, "%13.2lf", "getPrice"));
            columns.add(new TableColumn<>("Quantity", data, "%10d", "getQuantity"));
            columns.add(new TableColumn<>("Subtotal", data, "%14.2lf", "getSubtotal"));
            Table table_cart = new Table(columns);

            table_cart.printFull("My Cart (" + Main.customers[selected_account].getProduct_count() + ")");

            if (Main.customers[selected_account].getProduct_count() == 0) {
                Style.printColor(Style.RED, "Cart is empty. Add some items to your cart!%n");
                Style.line(Main.INTERFACE_WIDTH);
            }

            Style.println("[1] Check out [2] Remove Product [0] Return | Total: " + Str.setPrecision(Main.customers[selected_account].getTotal(), 2));
            Style.line(Main.INTERFACE_WIDTH);
            int choice = In.getInt("Enter choice: ");
            switch(choice) {
                case 1: {
                    if (Main.customers[selected_account].getProduct_count() == 0) {
                        Util.invalid("Error: Cart is empty.%n", Main.INTERFACE_WIDTH);
                        continue;
                    }
                    checkout();
                    break;
                }
                case 2:
                    if (Main.customers[selected_account].getProduct_count() == 0) {
                        Util.invalid("Error: Cart is empty.%n", Main.INTERFACE_WIDTH);
                        continue;
                    }
                    removeProductFromCart();
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

    public static void removeProductFromCart() {
        String choice = In.getString("Enter Product ID (0 to cancel): ");

        if (choice.equals("0")) {
            selected_product = -1;
            return;
        }

        for (int i = 0; i < Main.customers[selected_account].getCart().length; i++) {
            if (Main.customers[selected_account].getCart()[i].getProduct_id().equals(choice)) {
                selected_product = i;
                break;
            }
        }
        if (selected_product == -1) {
            Style.printColor(Style.RED, "Invalid selection.%n");
            In.waitEnter();
        }
        else {
            int quantity_to_remove = In.getInt("Enter quantity to remove (0 to cancel): ", 0, Main.customers[selected_account].getCart()[selected_product].getQuantity());

            if (choice.equals("0")) {
                selected_product = -1;
                return;
            }

            Style.printColor(Style.GREEN, "Successfully removed %d of %s from cart.%n", quantity_to_remove, Main.customers[selected_account].getCart()[selected_product].getProduct_name());

            if (Main.customers[selected_account].getCart()[selected_product].getQuantity() == quantity_to_remove) {
                Main.customers[selected_account].removeFromCart(Main.customers[selected_account].getCart()[selected_product].getProduct_id());
            }
            else {
                Main.customers[selected_account].getCart()[selected_product].setQuantity(Main.customers[selected_account].getCart()[selected_product].getQuantity() - quantity_to_remove);
            }

            Main.customers[selected_account].setProduct_count(Main.customers[selected_account].getProduct_count() - quantity_to_remove);
            Style.line(Main.INTERFACE_WIDTH);

            selected_product = -1;
            In.waitEnter();
        }
    }

    public static void viewWallet() {
        while(true) {
            Util.clear();
            Style.printTitle(Main.INTERFACE_WIDTH, "My Wallet");
            Style.println("Account Name: " + Main.customers[selected_account].getName());
            Style.println("Balance: " + Str.setPrecision(Main.customers[selected_account].getBalance(),2));
            Style.line(Main.INTERFACE_WIDTH);
            Style.println("[1] Cash in   [2] Cash out   [0] Return");
            Style.line(Main.INTERFACE_WIDTH);
            int choice = In.getInt("Enter choice: ");
            switch(choice) {
                case 1:
                    double cash_in = In.getDouble("Enter amount to cash in: ");
                    Main.customers[selected_account].setBalance(Main.customers[selected_account].getBalance() + cash_in);
                    Style.printColor(Style.GREEN, "Successfully cashed in %.2f. New balance: %.2f.%n", cash_in, Main.customers[selected_account].getBalance());
                    In.waitEnter();
                    break;
                case 2:
                    double cash_out = In.getDouble("Enter amount to cash out: ");
                    if (Main.customers[selected_account].getBalance() < cash_out) {
                        Style.printColor(Style.RED, "Insufficient balance.%n");
                        In.waitEnter();
                        continue;
                    }
                    Main.customers[selected_account].setBalance(Main.customers[selected_account].getBalance() - cash_out);
                    Style.printColor(Style.GREEN, "Successfully cashed out %.2f. New balance: %.2f.%n", cash_out, Main.customers[selected_account].getBalance());
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

    public static void checkout() {
        while(true) {
            List<Product> data = Arrays.asList(Main.customers[selected_account].getCart());

            List<TableColumn<?>> columns = new ArrayList<>();
            columns.add(new TableColumn<>("Product ID", data, "%12s", "getProduct_id"));
            columns.add(new TableColumn<>("Product Name", data, "%24s", "getProduct_name"));
            columns.add(new TableColumn<>("Price/x", data, "%13.2lf", "getPrice"));
            columns.add(new TableColumn<>("Quantity", data, "%10d", "getQuantity"));
            columns.add(new TableColumn<>("Subtotal", data, "%14.2lf", "getSubtotal"));
            Table table_checkout = new Table(columns);

            table_checkout.printFull("Checkout");



            Style.println("[1] Confirm Purchase [0] Return             | Total: " + Str.setPrecision(Main.customers[selected_account].getTotal(), 2));
            Style.line(Main.INTERFACE_WIDTH);
            if (Main.customers[selected_account].getBalance() >= Main.customers[selected_account].getTotal()) {
                Style.println("Available balance: " + Style.color(Style.GREEN, Str.setPrecision(Main.customers[selected_account].getBalance(), 2)));
            }
            else {
                Style.println("Available balance: " + Style.color(Style.RED, Str.setPrecision(Main.customers[selected_account].getBalance(), 2)));
            }

            int choice = In.getInt("Enter choice: ");
            switch(choice) {
                case 1:
                    makePurchase();
                    break;
                case 0:
                    Menu.dontWait();
                    return;
                default:
                    Util.invalid(Util.INVALID, Main.INTERFACE_WIDTH);
                    continue;
            }
            if (Main.customers[selected_account].getProduct_count() == 0) {
                break;
            }
        }

    }

    public static void makePurchase() {
        if (Main.customers[selected_account].getBalance() < Main.customers[selected_account].getTotal()) {
            Style.printColor(Style.RED, "Insufficient balance.%n");
            In.waitEnter();
            return;
        }
        Main.customers[selected_account].setBalance(Main.customers[selected_account].getBalance() - Main.customers[selected_account].getTotal());
        
        for (int i = 0; i < Main.customers[selected_account].getCart().length; i++) {
            Product.getSellerFromProduct(Main.customers[selected_account].getCart()[i].getProduct_id()).setBalance(
                Product.getSellerFromProduct(Main.customers[selected_account].getCart()[i].getProduct_id()).getBalance() +
                Main.customers[selected_account].getCart()[i].getSubtotal()
            );
        }

        Style.printColor(Style.GREEN, "Successfully made purchase of " + Str.setPrecision(Main.customers[selected_account].getTotal(), 2) + ". New balance is " + Str.setPrecision(Main.customers[selected_account].getBalance(), 2) + ".%n");

        Main.customers[selected_account].setCart(new Product[0]);
        Main.customers[selected_account].setProduct_count(0);
        In.waitEnter();
    }

    public static void searchProducts() {
        Util.clear();
        Style.printTitle(Main.INTERFACE_WIDTH, "Search Products");
        String query = In.getString("Enter query: ");

        Product[] results = new Product[0];
        for (int i = 0; i < Product.getAllProducts().length; i++) {
            String productName = Product.getAllProducts()[i].getProduct_name().toLowerCase(); // Convert product name to lowercase
            String sellerName = Product.getAllProducts()[i].getSeller_name().toLowerCase(); // Convert seller name to lowercase
            
            int index_product = productName.indexOf(query);
            int index_seller = sellerName.indexOf(query);
            
            if (index_product != -1 || index_seller != -1) {
                results = Arrays.copyOf(results, results.length + 1);
                results[results.length - 1] = Product.getAllProducts()[i];
            }
        }
        if (results.length == 0) {
            Style.printColor(Style.RED, "No results found.%n");
            return;
        }

        while (true) {
            List<Product> data = Arrays.asList(results);

            List<TableColumn<?>> columns = new ArrayList<>();
                columns.add(new TableColumn<>("Product ID", data, "%12s", "getProduct_id"));
                columns.add(new TableColumn<>("Product Name", data, "%32s", "getProduct_name"));
                columns.add(new TableColumn<>("Price", data, "%14.2lf", "getPrice"));
                columns.add(new TableColumn<>("Seller", data, "%16s", "getSeller_name"));
            Table table_query = new Table(columns);

            table_query.printFull("Search Results");

        
            String choice = In.getString("Enter Product ID (0 to return): ");

            if (choice.equals("0")) {
                Menu.dontWait();
                return;
            }

            for (int i = 0; i < results.length; i++) {
                if (results[i].getProduct_id().equals(choice)) {
                    selected_product = i;
                    break;
                }
            }
            if (selected_product == -1) {
                Style.printColor(Style.RED, "Invalid selection.%n");
                In.waitEnter();
            }
            else {
                viewProduct(results[selected_product]);
            }
        }
    }
}
