public class Product {
    private String product_id;
    private String product_name;
    private double price;

    public Product(String product_id, String product_name, double price){
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
    }

    public Product() {
    }

    //getters
    public String getProduct_id(){
        return product_id;
    }
    public String getProduct_name(){
        return product_name;
    }
    public double getPrice(){
        return price;
    }

    //setters
    public void setProduct_id(String product_id){
        this.product_id = product_id;
    }
    public void setProduct_name(String product_name){
        this.product_name = product_name;
    }
    public void setPrice(double price){
        this.price = price;
    }

    public static Product getProductById(String product_id) {
        for (Product product : Main.all_products) {
            if (product.getProduct_id().equals(product_id)) {
                return product;
            }
        }
        return null;
    }
}
