package glenlib;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class util {

    public final static void clear() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                // For Windows, use "cls" command
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix-like systems, use "clear" command
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

// Using a utility function
    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }


    public static void waitEnter() {
        System.out.print("Press Enter to continue...");
        System.out.flush();
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // Wait for Enter key press
        scanner.close();
    }

    public static void invalid() {
        style.printColor(style.RED, "Invalid choice.\n");
        style.line();
        waitEnter();
    }

    public static void invalid(String message, int width) {
        style.printColor(style.RED, message);
        style.line(width);
        waitEnter();
    }

    public static void exit() {
        clear();
        style.line();
        System.out.println("Exiting Program...");
        style.line();
        System.exit(0);
    }

    public static void exit(String message, int width) {
        clear();
        style.line(width);
        System.out.println(message);
        style.line(width);
        System.exit(0);
    }



}