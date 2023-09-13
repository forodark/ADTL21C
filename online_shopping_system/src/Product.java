public class Product {
    private String product_id;
    private String product_name;
    private double price;
    private String description;
    private int quantity;


    public Product(String product_id, String product_name, double price){
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.description = "";
    }

    public Product(String product_id, String product_name, double price, String description){
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
    }

    public Product(String product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.product_name = getProductById(product_id).getProduct_name();
        this.price = getProductById(product_id).getPrice();
        this.description = getProductById(product_id).getDescription();
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
    public String getDescription(){
        return description;
    }
    public int getQuantity(){
        return quantity;
    }
    public String getSeller_name() {
        return getSellerFromProduct(product_id).getName();
    }
    public double getSubtotal() {
        return price * quantity;
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
    public void setDescription(String description){
        this.description = description;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public static Product[] getAllProducts() {
        int total_products = 0;

        for (int i = 0; i < Main.sellers.length; i++) {
            total_products += Main.sellers[i].getProduct_count();
        }

        Product[] all_products = new Product[total_products];

        int all_products_index = 0;
        for (int i = 0; i < Main.sellers.length; i++) {
            for (int j = 0; j < Main.sellers[i].getProduct_count(); j++, all_products_index++) {
                all_products[all_products_index] = Main.sellers[i].getProducts()[j];
            }
        }

        return all_products;
    }

    public static Product getProductById(String product_id) {
        for (Product product : getAllProducts()) {
            if (product.getProduct_id().equals(product_id)) {
                return product;
            }
        }
        return null;
    }

    public static Seller getSellerFromProduct(String product_id) {
        for (Seller seller : Main.sellers) {
            for (Product product : seller.getProducts()) {
                if (product.getProduct_id().equals(product_id)) {
                    return seller;
                }
            }
        }
        return null;
    }

    public static String getAvailableId() {
        String id = "";
        for (int i = 1; i < 1000; i++) {
            String to_check = String.format("P%03d", i);
            int match = 0;
            
            for (int j = 0; j < getAllProducts().length; j++) {
                if (getAllProducts()[j].getProduct_id().equals(to_check)) {
                    match = 1;
                    break;
                }
            }
            if (match == 0) {
                id = to_check;
                break;
            }
        }

        return id;
    }

}
