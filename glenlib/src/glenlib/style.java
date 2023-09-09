package glenlib;


public class style {

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void printColor(String color, String text) {
        System.out.print(color + text + RESET);
    }
    public static void color(String color) {
        System.out.print(color);
    }
    public static void color() {
        System.out.print(RESET);
    }

    public static void line(int width) {
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public static void line() {
        line(31);
    }

    public static void printCentered(String text, int width) {
        if (width <= text.length()) {
            System.out.println(text);
        } else {
            int total_padding = width - text.length();
            int left_padding = total_padding / 2;
            int right_padding = total_padding - left_padding;

            StringBuilder centeredText = new StringBuilder();
            for (int i = 0; i < left_padding; i++) {
                centeredText.append(" ");
            }
            centeredText.append(text);
            for (int i = 0; i < right_padding; i++) {
                centeredText.append(" ");
            }

            System.out.print(centeredText.toString());
        }
    }

}

