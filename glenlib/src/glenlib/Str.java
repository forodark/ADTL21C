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
            // Handle the case where the string is all whitespace
            return "";
        }

        return str.substring(start, end + 1);
    }

    public static String trimZeros(String numStr) {
        if (numStr == null || numStr.isEmpty()) {
            return numStr;
        }

        int decimalPointPos = numStr.indexOf('.');
        
        if (decimalPointPos != -1) {
            int lastNonZeroDigit = numStr.length() - 1;

            // Find the last non-zero digit after the decimal point.
            while (lastNonZeroDigit > decimalPointPos && numStr.charAt(lastNonZeroDigit) == '0') {
                lastNonZeroDigit--;
            }

            if (lastNonZeroDigit == decimalPointPos) {
                // If there are no non-zero digits after the decimal point, remove it too.
                return numStr.substring(0, decimalPointPos);
            } else {
                // Remove trailing zeros after the decimal point.
                return numStr.substring(0, lastNonZeroDigit + 1);
            }
        }

        // If there's no decimal point, no action is needed.
        return numStr;
    }

    public static String truncate(String inputString, int width) {
        if (width == 0) {
            return inputString;
        }

        String inputBuffer = trimZeros(inputString);

        if (inputBuffer.length() > width) {
            String truncated = inputBuffer.substring(0, width - 2) + "..";
            return truncated;
        } else if (inputBuffer.length() < width) {
            String truncated = inputBuffer;
            int spacesToAdd = width - inputBuffer.length();
            for (int i = 0; i < spacesToAdd; i++) {
                truncated += " ";
            }
            return truncated;
        }

        return inputBuffer;
    }
    
    public static String formatString(Object value) {
        return formatString(value, 0, -1);
    }

    public static String formatString(Object value, int width) {
        return formatString(value, width, -1);
    }

    public static String formatString(Object value, int width, int precision) {
        StringBuilder formatted = new StringBuilder();

        if (value != null) {
            // Check if the value is a number (Integer, Double, Float, etc.)
            if (value instanceof Number) {
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                
                if (precision >= 0 && (value instanceof Float || value instanceof Double)) {
                    numberFormat.setMaximumFractionDigits(precision);
                    numberFormat.setMinimumFractionDigits(precision);
                }
                
                formatted.append(numberFormat.format(value));
            } else {
                // For non-numeric types, simply convert to a string
                formatted.append(value.toString());
            }
        } else {
            formatted.append("null"); // Handle null values
        }

        return truncate(formatted.toString(), width);
    }


    public static int extractNumber(String str) {
        int result = 0;
        boolean foundDigit = false;
    
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                result = result * 10 + (c - '0');
                foundDigit = true;
            } else if (c == '.' && !foundDigit) {
                return 0;
            } else if (foundDigit && c == '.') {
                break;
            }
        }
    
        return result;
    }
    
    

    
    
    
    

    public static int extractDecimal(String str) {
        int result = 0;
        int decimalPos = -1;

        // Find the position of the decimal point
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.') {
                decimalPos = i;
                break;
            }
        }

        if (decimalPos == -1) {
            // No decimal point found, return 0
            return 0;
        }

        // Extract the digits after the decimal point
        for (int i = decimalPos + 1; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                result = result * 10 + (str.charAt(i) - '0');
            } else {
                // If a non-digit character is encountered, stop extracting
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
}
