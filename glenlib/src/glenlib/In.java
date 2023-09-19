package glenlib;


import java.util.Scanner;

public class In {
    private static final Scanner scanner = new Scanner(System.in);

    public static final String CHAR_LIB = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ ";
    public static final int STR_MIN = 0;
    public static final int STR_MAX = 256;

    public static final int INT_MIN = -2147483648;
    public static final int INT_MAX = 2147483647;

    public static final int FLOAT_MIN = -2147483648;
    public static final int FLOAT_MAX = 2147483647;

    public static final int DOUBLE_MIN = -2147483648;
    public static final int DOUBLE_MAX = 2147483647;

    public static void waitEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // Function for integer input
    public static int getInt(String prompt) {
        return getInt(prompt, INT_MIN, INT_MAX);
    }

    public static int getInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                Util.invalid("Invalid input. Input must not be empty.%n");
            } else {
                try {
                    int temp = Integer.parseInt(input);
                    if (temp < min || temp > max) {
                        Util.invalid("Invalid input. Input length must be between " + min + " and " + max + ".%n");
                        continue;
                    }
                    return temp;
                } catch (NumberFormatException e) {
                    Util.invalid("Invalid input. Input must be an integer.%n");
                }
            }
        }
    }

    // Function for float input
    public static float getFloat(String prompt) {
        return getFloat(prompt, FLOAT_MIN, FLOAT_MAX);
    }

    public static float getFloat(String prompt, float min, float max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                Util.invalid("Invalid input. Input must not be empty.%n");
            } else {
                try {
                    float temp = Float.parseFloat(input);
                    if (temp < min || temp > max) {
                        Util.invalid("Invalid input. Input length must be between " + min + " and " + max + ".%n");
                        continue;
                    }
                    return temp;
                } catch (NumberFormatException e) {
                    Util.invalid("Invalid input. Please enter a valid float.%n");
                }
            }
        }
    }

    // Function for double input
    public static double getDouble(String prompt) {
        return getDouble(prompt, DOUBLE_MIN, DOUBLE_MAX);
    }

    public static double getDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                Util.invalid("Invalid input. Input must not be empty.%n");
            } else {
                try {
                    double temp = Double.parseDouble(input);
                    if (temp < min || temp > max) {
                        Util.invalid("Invalid input. Input length must be between " + min + " and " + max + ".%n");
                        continue;
                    }
                    return temp;
                } catch (NumberFormatException e) {
                    Util.invalid("Invalid input. Please enter a valid double.%n");
                }
            }
        }
    }

    // Function for character input
    public static char getChar(String prompt) {
        return getChar(prompt, CHAR_LIB);
    }

    public static char getChar(String prompt, String accepted) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                Util.invalid("Invalid input. Input must not be empty.%n");
            } else if (input.length() != 1) {
                Util.invalid("Invalid input. Input must be a single character.%n");
            } else {
                char temp = input.charAt(0);
                if (accepted.indexOf(temp) == -1) {
                    Util.invalid("Invalid input. Input cannot be '" + temp + "'.%n");
                    continue;
                }
                return temp;
            }
        }
    }

    public static String getString(String prompt) {
        return getString(prompt, CHAR_LIB, STR_MIN, STR_MAX);
    }
    public static String getString(String prompt, String accepted) {
        return getString(prompt, accepted, STR_MIN, STR_MAX);
    }
    public static String getString(String prompt, int min_length, int max_length) {
        return getString(prompt, CHAR_LIB, min_length, max_length);
    }

    // Function for string input
    public static String getString(String prompt, String accepted, int min_length, int max_length) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                Util.invalid("Invalid input. Input must not be empty.%n");
            } else if (input.length() < min_length || input.length() > max_length) {
                Util.invalid("Invalid input. Input must be between " + min_length + " and " + max_length + " characters.%n");
            } else {
                boolean invalid = false;
                StringBuilder invalid_chars = new StringBuilder();

                for (char c : input.toCharArray()) {
                    if (accepted.indexOf(c) == -1) {
                        invalid = true;
                        if (invalid_chars.indexOf(String.valueOf(c)) == -1) {
                            invalid_chars.append(c);
                        }
                    }
                }

                if (!invalid) {
                    return input;
                } else {
                    Util.invalid("Invalid input. Input cannot contain '" + invalid_chars.toString() + "'.%n");
                }
            }
        }
    }

    public static boolean getBool(String prompt) {
        return getBool(prompt, '1', '0');
    }
    // Function for boolean input
    public static boolean getBool(String prompt, char true_choice, char false_choice) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.length() == 1) {
                char ch = input.charAt(0);
                if (ch == true_choice || ch == 'y' || ch == 'Y' || ch == '1' || ch == 't' || ch == 'T') {
                    return true;
                } else if (ch == false_choice || ch == 'n' || ch == 'N' || ch == '0' || ch == 'f' || ch == 'F') {
                    return false;
                }
            }

            Util.invalid("Invalid input. Input must be '" + true_choice + "' or '" + false_choice + "'.%n");
        }
    }

}
