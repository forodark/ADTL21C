import java.util.Arrays;

public class Seller extends User {
    private String seller_id;
    private Product products[];
    private int product_count;

    public Seller(String name, String email, String password, String seller_id){
        super(name, email, password);
        this.seller_id = seller_id;
        this.products = new Product[0];
        this.product_count = products.length;
    }

    //getters
    public String getSeller_id(){
        return seller_id;
    }
    public int getProduct_count(){
        return product_count;
    }
    
    public void addProduct(String product_id) {
        products = Arrays.copyOf(products, products.length + 1);
        products[products.length - 1] = Product.getProductById(product_id);
        product_count += 1;
    }

    public void removeProduct(String product_id) {
        for (int i = 0; i < products.length; i++) {
            if (products[i].getProduct_id().equals(product_id)) {
                products = Arrays.copyOf(products, products.length - 1);
            }
        }
        product_count -= 1;
    }

    public void printproducts() {
        for (Product product : products) {
            System.out.println(product.getProduct_id() + " " + product.getProduct_name() + " " + product.getPrice());
        }
    }

}
