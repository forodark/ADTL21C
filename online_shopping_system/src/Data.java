import java.io.*;

public class Data {
    private static final String SELLERS_FILE = "sellers.txt";
    private static final String CUSTOMERS_FILE = "customers.txt";

    public static void save() {
        try {
            PrintWriter sellersWriter = new PrintWriter(new FileWriter(SELLERS_FILE));
            for (Seller seller : Main.sellers) {
                sellersWriter.println(seller.toString());
            }
            sellersWriter.close();

            PrintWriter customersWriter = new PrintWriter(new FileWriter(CUSTOMERS_FILE));
            for (Customer customer : Main.customers) {
                customersWriter.println(customer.toString());
            }
            customersWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            // Load Sellers data
            BufferedReader sellersReader = new BufferedReader(new FileReader(SELLERS_FILE));
            String line;
            int sellerIndex = 0; // Track the index for adding sellers to the array
    
            while ((line = sellersReader.readLine()) != null) {

                String[] sellerSplit = line.split("//");
                
                String[] sellerDetails = sellerSplit[0].split(",,");

                String[] productData;
                Product[] products = new Product[0];
                
                if (sellerSplit.length >= 2) {
                    productData = sellerSplit[1].split(";;");
                    products = new Product[productData.length];

                    for (int i = 0; i < productData.length; i++) {
                        String[] productDetails = productData[i].split(",,");
                        String productId = productDetails[0];
                        String productName = productDetails[1];
                        double price = Double.parseDouble(productDetails[2]);
                        String description = "";
                        if (productDetails.length >= 4) {
                            description = productDetails[3];
                        }
                        products[i] = new Product(productId, productName, price, description);
                    }

                }

                // // Extract customer details
                String name = sellerDetails[0];
                String email = sellerDetails[1];
                String password = sellerDetails[2];
                double balance = Double.parseDouble(sellerDetails[3]);
                String sellerId = sellerDetails[4];
                String description = "";
                if (sellerDetails.length >= 6) {
                    description = sellerDetails[5];
                }
                // Create a new Customer object with extracted data
                Seller seller = new Seller(name, email, password, balance, sellerId, description, products);

                // Add the customer to Main.customers array
                Main.sellers[sellerIndex] = seller;
                sellerIndex++;
            }
            sellersReader.close();
    
            // Load Customers data
            BufferedReader customersReader = new BufferedReader(new FileReader(CUSTOMERS_FILE));
            int customerIndex = 0; // Track the index for adding customers to the array
    
            while ((line = customersReader.readLine()) != null) {
                // Split the line by semicolon to separate customer details

                String[] customerSplit = line.split("//");
                
                String[] customerDetails = customerSplit[0].split(",,");

                String[] productData;
                Product[] products = new Product[0];
                if (customerSplit.length >= 2) {
                    productData = customerSplit[1].split(";;");
                    products = new Product[productData.length];

                    for (int i = 0; i < productData.length; i++) {
                        String[] productDetails = productData[i].split(",,");
                        String productId = productDetails[0];
                        int productQuantity = Integer.parseInt(productDetails[1]);
                        products[i] = new Product(productId, productQuantity);
                    }


                }

                // // Extract customer details
                String name = customerDetails[0];
                String email = customerDetails[1];
                String password = customerDetails[2];
                double balance = Double.parseDouble(customerDetails[3]);
                String customerId = customerDetails[4];

                // Create a new Customer object with extracted data
                Customer customer = new Customer(name, email, password, balance, customerId, products);

                // Add the customer to Main.customers array
                Main.customers[customerIndex] = customer;
                customerIndex++;
            }
            customersReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static int countSellers() {
        int sellerCount = 0;
    
        try {
            BufferedReader sellersReader = new BufferedReader(new FileReader(SELLERS_FILE));
            String line;
    
            while ((line = sellersReader.readLine()) != null) {
                // Check if the line contains seller data
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                
                String[] sellerData = line.split(",,");
                if (sellerData.length >= 5) {
                    // Each line with valid seller data should have at least 5 elements
                    sellerCount++;
                }
            }
            
            sellersReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return sellerCount;
    }
    
    public static int countCustomers() {
        int customerCount = 0;
    
        try {
            BufferedReader customersReader = new BufferedReader(new FileReader(CUSTOMERS_FILE));
            String line;
    
            while ((line = customersReader.readLine()) != null) {
                // Check if the line contains customer data
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
    
                String[] customerData = line.split(",,");
                if (customerData.length >= 5) {
                    // Each line with valid customer data should have at least 5 elements
                    customerCount++;
                }
            }
    
            customersReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return customerCount;
    }
    

}
