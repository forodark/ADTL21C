import java.util.Arrays;

public class Customer extends User {
    private String customer_id;
    private Product cart[];

    public Customer(String name, String email, String password, String customer_id) {
        super(name, email, password);
        this.customer_id = customer_id;
        this.cart = new Product[0];
    }


    //setters
    public String getCustomer_id() {
        return customer_id;
    }

    //add to cart
    public void addToCart(String product_id) {
        cart = Arrays.copyOf(cart, cart.length + 1);
        cart[cart.length - 1] = Product.getProductById(product_id);
    }

    public void removeFromCart(String product_id) {
        for (int i = 0; i < cart.length; i++) {
            if (cart[i].getProduct_id().equals(product_id)) {
                cart = Arrays.copyOf(cart, cart.length - 1);
            }
        }
    }

    public void printCart() {
        for (Product product : cart) {
            System.out.println(product.getProduct_id() + " " + product.getProduct_name() + " " + product.getPrice());
        }
    }

}
