import java.util.Arrays;

public class Customer extends User {
    private String customer_id;
    private Product cart[];
    private int product_count;

    public Customer(String name, String email, String password, Double balance, String customer_id) {
        super(name, email, password, balance);
        this.customer_id = customer_id;
        this.cart = new Product[0];
        this.product_count = calculateProduct_count();
    }

    public Customer(String name, String email, String password, Double balance, String customer_id, Product[] cart) {
        super(name, email, password, balance);
        this.customer_id = customer_id;
        this.cart = cart;
        this.product_count = calculateProduct_count();
    }
    
    //getters 
    public String getCustomer_id() {
        return customer_id;
    }

    public Product[] getCart() {
        return cart;
    }

    public int getProduct_count() {
        return product_count;
    }

    //setters
    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public void setProduct_count(int product_count) {
        this.product_count = product_count;
    }

    public void setCart(Product[] cart) {
        this.cart = cart;
    }

    //add to cart
    public void addToCart(String product_id, int quantity) {
        Boolean exists = false;
        for (int i = 0; i < cart.length; i++) {
            if (cart[i].getProduct_id().equals(product_id)) {
                cart[i].setQuantity(cart[i].getQuantity() + quantity);
                exists = true;
                return;
            }
        }
        if (!exists) {
            cart = Arrays.copyOf(cart, cart.length + 1);
            cart[cart.length - 1] = Product.getProductById(product_id);
            cart[cart.length - 1].setQuantity(cart[cart.length - 1].getQuantity() + quantity);
        }

    }

    public void removeFromCart(String product_id) {
        int i = 0;
        for (; i < cart.length; i++) {
            if (cart[i].getProduct_id().equals(product_id)) {
                break;
            }
        }
        for (; i < cart.length - 1; i++) {
            cart[i] = cart[i+1];
        }
        cart = Arrays.copyOf(cart, cart.length - 1);
    }

    public int calculateProduct_count() {
        int count = 0;
        if (cart.length == 0) {
            return 0;
        }

        if (cart[0] == null) {
            return 0;
        }
        
        for (int i = 0; i < cart.length; i++) {
            count += cart[i].getQuantity();
        }
        return count;
    }

    public double getTotal() {
        double total = 0;
        
        for (int i = 0; i < cart.length; i++) {
            total += cart[i].getSubtotal();
        }
        return total;
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        // Serialize seller data as a single string
        // Format: name,email,password,balance,code;product1,product2,...
        // Example: Seller1,seller1@example.com,password1,1000.0,S001;P001:Apple:100.0,P002:Banana:200.0,P003:Orange:300.0
        builder.append(getName()).append(",,").append(getEmail()).append(",,").append(getPassword()).append(",,").append(getBalance()).append(",,").append(getCustomer_id());
        builder.append("//");
        for (Product product : getCart()) {
            builder.append(product.getProduct_id()).append(",,").append(product.getQuantity()).append(";;");
        }
        return builder.toString();
    }
}
