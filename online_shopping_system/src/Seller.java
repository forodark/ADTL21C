import java.util.Arrays;

public class Seller extends User {
    private String seller_id;
    private Product products[];
    private int product_count;
    private String description;

    public Seller(String name, String email, String password, Double balance, String seller_id){
        super(name, email, password, balance);
        this.seller_id = seller_id;
        this.products = new Product[0];
        this.product_count = products.length;
        this.description = "";
    }

    public Seller(String name, String email, String password, Double balance, String seller_id, Product[] products){
        super(name, email, password, balance);
        this.seller_id = seller_id;
        this.products = products;
        this.product_count = products.length;
        this.description = "";
    }

    public Seller(String name, String email, String password, Double balance, String seller_id, String description, Product[] products){
        super(name, email, password, balance);
        this.seller_id = seller_id;
        this.products = products;
        this.product_count = products.length;
        this.description = description;
    }

    //getters
    public String getSeller_id(){
        return seller_id;
    }

    public Product[] getProducts(){
        return products;
    }

    public int getProduct_count(){
        return product_count;
    }
    public String getDescription(){
        return description;
    }

    //setters
    public void setSeller_id(String seller_id){
        this.seller_id = seller_id;
    }
    public void setProduct_count(int product_count){
        this.product_count = product_count;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void addProduct(Product product) {
        products = Arrays.copyOf(products, products.length + 1);
        products[products.length - 1] = product;
        setProduct_count(getProduct_count() + 1);
    }

    public void removeProduct(String product_id) {
        int i = 0;
        for (; i < products.length; i++) {
            if (products[i].getProduct_id().equals(product_id)) {
                break;
            }
        }
        for (; i < products.length - 1; i++) {
            products[i] = products[i+1];
        }
        products = Arrays.copyOf(products, products.length - 1);
        setProduct_count(getProduct_count() - 1);
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        // Serialize seller data as a single string
        // Format: name,email,password,balance,code;product1,product2,...
        // Example: Seller1,seller1@example.com,password1,1000.0,S001;P001:Apple:100.0,P002:Banana:200.0,P003:Orange:300.0
        builder.append(getName()).append(",,").append(getEmail()).append(",,").append(getPassword()).append(",,").append(getBalance()).append(",,").append(getSeller_id()).append(",,").append(getDescription());
        builder.append("//");
        for (Product product : getProducts()) {
            builder.append(product.getProduct_id()).append(",,").append(product.getProduct_name()).append(",,").append(product.getPrice()).append(",,").append(product.getDescription()).append(";;");
        }
        return builder.toString();
    }
}
