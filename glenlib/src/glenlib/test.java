package glenlib;

public class test {
    public static void main(String[] args) {
        // Create your menu items
        MenuItem[] menuItems = {
            new MenuItem("Option 1", () -> function1()),
            new MenuItem("Option 2", () -> function2()),
        };

        // Show the menu
        menu.showMenu("Menu", menuItems);
    }
    
    public static void function1() {
        System.out.println("You selected Option 1.");
    }

    public static void function2() {
        System.out.println("You selected Option 2.");
    }

}
