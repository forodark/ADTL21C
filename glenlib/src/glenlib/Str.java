package glenlib;


import java.io.StringWriter;
import java.text.NumberFormat;

public class Str {
    public static String convertString(Object value) {
        StringWriter sw = new StringWriter();
        sw.append(String.valueOf(value));
        return sw.toString();
    }

    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        
        int start = 0;
        int end = str.length() - 1;

        while (start <= end && Character.isWhitespace(str.charAt(start))) {
            start++;
        }

        while (end >= start && Character.isWhitespace(str.charAt(end))) {
            end--;
        }

        if (start > end) {
            return "";
        }

        return str.substring(start, end + 1);
    }

    public static String trimZeros(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        int decimal_pos = input.indexOf('.');
        
        if (decimal_pos != -1) {
            int non_zero_digit_pos = input.length() - 1;

            while (non_zero_digit_pos > decimal_pos && input.charAt(non_zero_digit_pos) == '0') {
                non_zero_digit_pos--;
            }

            if (non_zero_digit_pos == decimal_pos) {
                return input.substring(0, decimal_pos);
            } else {
                return input.substring(0, non_zero_digit_pos + 1);
            }
        }

        return input;
    }

    public static String truncate(String input, int width) {
        if (width == 0) {
            return input;
        }

        String input_buffer = input;

        if (input_buffer.length() > width) {
            String truncated = input_buffer.substring(0, width - 2) + "..";
            return truncated;
        } else if (input_buffer.length() < width) {
            String truncated = input_buffer;
            int spaces = width - input_buffer.length();
            for (int i = 0; i < spaces; i++) {
                truncated += " ";
            }
            return truncated;
        }

        return input_buffer;
    }
    
    public static String formatString(Object value) {
        return formatString(value, 0, -1);
    }

    public static String formatString(Object value, int width) {
        return formatString(value, width, -1);
    }

    public static String setPrecision(Object value, int precision) {
        return formatString(value, 0, precision);
    }


    public static String formatString(Object value, int width, int precision) {
        StringBuilder formatted = new StringBuilder();
        
        if (value != null) {
        
            if (value instanceof Number && precision != -1) {
                NumberFormat number_format = NumberFormat.getNumberInstance();
                
                // Set precision for all numbers
                number_format.setMaximumFractionDigits(precision);
                number_format.setMinimumFractionDigits(precision);
                
                // Set grouping separator (e.g., comma in 1,000)
                number_format.setGroupingUsed(true);
                
                formatted.append(number_format.format(value));
            } else {
                formatted.append(value.toString());
            }
        } 
        else {
            formatted.append("N/A");
        }

        return truncate(formatted.toString(), width);
    }


    public static int extractNumber(String str) {
        int result = 0;
        boolean found_digit = false;
    
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                result = result * 10 + (c - '0');
                found_digit = true;
            } else if (c == '.' && !found_digit) {
                return 0;
            } else if (found_digit && c == '.') {
                break;
            }
        }
    
        return result;
    }
    

    public static int extractDecimal(String str) {
        int result = 0;
        int decimal_pos = -1;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.') {
                decimal_pos = i;
                break;
            }
        }

        if (decimal_pos == -1) {
            return -1;
        }

        for (int i = decimal_pos + 1; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                result = result * 10 + (str.charAt(i) - '0');
            } else {
                break;
            }
        }

        return result;
    }

    // Sample: isNumericChar('a'); | Result: 0
    public static boolean isNumericChar(char ch) {
        return Character.isDigit(ch) || ch == '.' || ch == '-' || ch == '+';
    }

    // Sample: isEmpty(""); | Result: 1
    public static boolean isEmpty(String input) {
        for (char ch : input.toCharArray()) {
            if (!Character.isWhitespace(ch)) {
                return false;
            }
        }
        return true;
    }

    public static String paragraph(String input, int width) {
        if (input == null || input.isEmpty() || width <= 0) {
            return input;
        }

        StringBuilder result = new StringBuilder();
        int line_length = 0;
        String[] words = input.split(" ");

        for (String word : words) {
            // Check if adding the word to the current line exceeds the width
            if (line_length + word.length() + 1 > width) {
                result.append("\n");
                line_length = 0;
            }

            // Append the word with a space
            if (line_length > 0) {
                result.append(" ");
                line_length++;
            }
            result.append(word);
            line_length += word.length();
        }

        return result.toString();
    }

    public static String getFileExtension(String filename) {
        // Find the position of the last dot in the filename
        int dot_position = filename.lastIndexOf(".");
        
        // Check if a dot was found and it's not the first or last character
        if (dot_position != -1 && dot_position != 0 && dot_position != filename.length() - 1) {
            // Extract and return the substring after the last dot
            return filename.substring(dot_position + 1);
        } else {
            // No valid extension found
            return "";
        }
    }

    public static String getFileName(String filename) {
        // Find the position of the last dot in the filename
        int dot_position = filename.lastIndexOf(".");
        
        // Check if a dot was found and it's not the first or last character
        if (dot_position != -1 && dot_position != 0 && dot_position != filename.length() - 1) {
            // Extract and return the substring before the last dot
            return filename.substring(0, dot_position);
        } else {
            // No valid extension found, return the entire filename
            return filename;
        }
    }

    public static int countSubstr(String input, String substr) {
        int count = 0;
        int index = 0;
    
        while ((index = input.indexOf(substr, index)) != -1) {
            count++;
            index += substr.length();
        }
    
        return count;
    }
    
    public static int findNthOccurrence(String input, String target, int occurrence) {
        int index = -1;
    
        for (int i = 0; i < occurrence; i++) {
            // Start the search from the index right after the last occurrence
            index = input.indexOf(target, index + 1);
    
            if (index == -1) {
                // Couldn't find the Nth occurrence
                return -1;
            }
        }
    
        // Return the index of the Nth occurrence
        return index;
    }

    public static int findNthOccurrences(String input, String[] targets, int occurrence) {
        int index = -1;
    
        for (int i = 0; i < occurrence; i++) {
            // Start the search from the index right after the last occurrence
            for (String target : targets) {
                index = input.indexOf(target, index + 1);
    
                if (index != -1) {
                    // If found, break the loop
                    break;
                }
            }
    
            if (index == -1) {
                // Couldn't find the Nth occurrence
                return -1;
            }
        }
    
        // Return the index of the Nth occurrence
        return index;
    }
    
    public static String removeSpaces(String input) {
        if (input == null) {
            return null; // Can't remove spaces from nothing, right?
        }
        
        // Time to work the magic!
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            
            // Abracadabra! No space? Into the result it goes!
            if (currentChar != ' ') {
                result.append(currentChar);
            }
        }
        
        // Ta-da! The string with spaces vanished.
        return result.toString();
    }
    
    public static String insertChar(String str, char c, int index) {
        StringBuilder result = new StringBuilder(str);
        result.insert(index, c);
        return result.toString();
    }

    public static boolean containsAny(String input, String targets) {
        for (char c : input.toCharArray()) {
            if (targets.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }
}
