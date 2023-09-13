public class User {
    private String name;
    private String email;
    private String password;
    private double balance;

    //constructor
    public User(String name, String email, String password, double balance){
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }



    //getters
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public double getBalance(){
        return balance;
    }
    
    //setters
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }


}
