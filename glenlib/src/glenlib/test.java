package glenlib;

public class test {
    public static void main(String[] args) {
        // Create your menu items
        MenuItem[] menuItems = {
            new MenuItem("Option 1", () -> {
                System.out.println("You selected Option 1.");
                // Add your custom function logic here
            }),
            new MenuItem("Test subtitle", () -> {
                // This is a subtitle, do nothing here
            }),
            new MenuItem("Option 2", () -> {
                System.out.println("You selected Option 2.");
                // Add your custom function logic here
            }),
        };

        // Show the menu
        menu.showMenu("Menu", menuItems);
    }
    
}
