package glenlib;

import java.util.concurrent.TimeUnit;

public class Util {

    //Defaults
    public static final String INVALID = "Invalid choice.\n";
    public static final String EXIT = "Exiting Program...";

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


    public static void invalid() {
        Style.printColor(Style.RED, "Invalid choice.\n");
        Style.line();
        In.waitEnter();
    }

    public static void invalid(String message, int width) {
        Style.printColor(Style.RED, message);
        Style.line(width);
        In.waitEnter();
    }

    public static void exit() {
        clear();
        Style.line();
        System.out.println("Exiting Program...");
        Style.line();
        System.exit(0);
    }

    public static void exit(String message, int width) {
        clear();
        Style.line(width);
        System.out.println(message);
        Style.line(width);
        System.exit(0);
    }




}