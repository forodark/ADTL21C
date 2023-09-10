package glenlib;


import java.util.Scanner;

public class In {
    private static final Scanner scanner = new Scanner(System.in);

    public static final String CHAR_LIB = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ ";
    public static final int STR_MIN = 0;
    public static final int STR_MAX = 256;

    public static void waitEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // Wait for Enter key press
    }

    // Function for integer input
    public static int getInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                Style.printColor(Style.RED, "Invalid input. Input must not be empty.%n");
                Style.line();
            } else {
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    Style.printColor(Style.RED, "Invalid input. Input must be an integer.%n");
                    Style.line();
                }
            }
        }
    }

    // Function for float input
    public static float getFloat(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                Style.printColor(Style.RED, "Invalid input. Input must not be empty.%n");
                Style.line();
            } else {
                try {
                    return Float.parseFloat(input);
                } catch (NumberFormatException e) {
                    Style.printColor(Style.RED, "Invalid input. Please enter a valid float.%n");
                    Style.line();
                }
            }
        }
    }

    // Function for double input
    public static double getDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                Style.printColor(Style.RED, "Invalid input. Input must not be empty.%n");
                Style.line();
            } else {
                try {
                    return Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    Style.printColor(Style.RED, "Invalid input. Please enter a valid double.%n");
                    Style.line();
                }
            }
        }
    }

    // Function for character input
    public static char getChar(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                Style.printColor(Style.RED, "Invalid input. Input must not be empty.%n");
                Style.line();
            } else if (input.length() != 1) {
                Style.printColor(Style.RED, "Invalid input. Input must be a single character.%n");
                Style.line();
            } else {
                return input.charAt(0);
            }
        }
    }

    public static String getString(String prompt) {
        return getString(prompt, CHAR_LIB, STR_MIN, STR_MAX);
    }
    public static String getString(String prompt, String accepted) {
        return getString(prompt, accepted, STR_MIN, STR_MAX);
    }
    public static String getString(String prompt, int minLength, int maxLength) {
        return getString(prompt, CHAR_LIB, minLength, maxLength);
    }

    // Function for string input
    public static String getString(String prompt, String accepted, int minLength, int maxLength) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                Style.printColor(Style.RED, "Invalid input. Input must not be empty.%n");
                Style.line();
            } else if (input.length() < minLength || input.length() > maxLength) {
                Style.printColor(Style.RED, "Invalid input. Input must be between %d and %d characters.%n", minLength, maxLength);
                Style.line();
            } else {
                boolean invalid = false;
                StringBuilder invalidChars = new StringBuilder();

                for (char c : input.toCharArray()) {
                    if (accepted.indexOf(c) == -1) {
                        invalid = true;
                        if (invalidChars.indexOf(String.valueOf(c)) == -1) {
                            invalidChars.append(c);
                        }
                    }
                }

                if (!invalid) {
                    return input;
                } else {
                    Style.printColor(Style.RED, "Invalid input. Input cannot contain '%s'.%n", invalidChars.toString());
                    Style.line();
                }
            }
        }
    }

    public static boolean getBool(String prompt) {
        return getBool(prompt, '1', '0');
    }
    // Function for boolean input
    public static boolean getBool(String prompt, char trueChoice, char falseChoice) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.length() == 1) {
                char ch = input.charAt(0);
                if (ch == trueChoice || ch == 'y' || ch == 'Y' || ch == '1' || ch == 't' || ch == 'T') {
                    return true;
                } else if (ch == falseChoice || ch == 'n' || ch == 'N' || ch == '0' || ch == 'f' || ch == 'F') {
                    return false;
                }
            }

            Style.printColor(Style.RED, "Invalid input. Input must be '%c' or '%c'.%n", trueChoice, falseChoice);
            Style.line();
        }
    }

}
