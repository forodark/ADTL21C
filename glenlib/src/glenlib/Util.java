package glenlib;

import java.util.concurrent.TimeUnit;

public class Util {

    //Defaults
    public static final String INVALID = "Invalid choice.\n";
    public static final String EXIT = "Exiting Program...";
     public static final int invalid_clear = 5;
    private static int default_width = 31;

    public static void setWidth(int width) {
        default_width = width;
    }

    public final static void clear() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void clear(int num_lines) {
        for (int i = 0; i < num_lines; i++) {
            System.out.print("\u001b[2K");
            if (i < num_lines - 1) {
                System.out.print("\u001b[1A"); 
            }
        }
        System.out.flush(); 
    }

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public static void invalid() {
        invalid(INVALID, default_width);
    }

    public static void invalid(String message) {
        invalid(message, default_width);
    }

    public static void invalid(String message, int width) {
        Style.printColor(Style.RED, message);
        Style.line(width);
        In.waitEnter();
        clear(invalid_clear);
    }

    public static void exit() {
        exit(EXIT, default_width);
    }

    public static void exit(String message) {
        exit(message, default_width);
    }

    public static void exit(String message, int width) {
        clear();
        Style.line(width);
        System.out.println(message);
        Style.line(width);
        System.exit(0);
    }




}