package glenlib;

import java.util.Scanner;

// Define a functional interface for menu functions
interface MenuFunction {
    void execute();
}

// Define a menu item class
class MenuItem {
    String text;
    MenuFunction function;

    public MenuItem(String text, MenuFunction function) {
        this.text = text;
        this.function = function;
    }
}

// Create a Menu class for handling menu interactions
public class menu {
    public static void showMenu(String title, MenuItem[] menuItems) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            clearScreen();
            System.out.println(title + "\n");

            for (int i = 0; i < menuItems.length; i++) {
                System.out.println("[" + (i + 1) + "] " + menuItems[i].text);
            }

            System.out.println("[0] Return\n");
            System.out.print("Enter Choice: ");
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            } catch (Exception e) {
                choice = -1; // Invalid choice
            }

            if (choice == 0) {
                break;
            } else if (choice > 0 && choice <= menuItems.length) {
                // Execute the selected function
                menuItems[choice - 1].function.execute();
                waitEnter();
            } else {
                System.out.println("Invalid choice. Please select a valid option.");
                waitEnter();
            }
        }
    }

    public static void clearScreen() {
        // You can implement clearing the screen here, depending on your OS
        // For simplicity, let's just print some newlines
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static void waitEnter() {
        System.out.print("\nPress Enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
