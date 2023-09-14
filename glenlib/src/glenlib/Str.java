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

    public static String trimZeros(String numStr) {
        if (numStr == null || numStr.isEmpty()) {
            return numStr;
        }

        int decimalPointPos = numStr.indexOf('.');
        
        if (decimalPointPos != -1) {
            int lastNonZeroDigit = numStr.length() - 1;

            while (lastNonZeroDigit > decimalPointPos && numStr.charAt(lastNonZeroDigit) == '0') {
                lastNonZeroDigit--;
            }

            if (lastNonZeroDigit == decimalPointPos) {
                return numStr.substring(0, decimalPointPos);
            } else {
                return numStr.substring(0, lastNonZeroDigit + 1);
            }
        }

        return numStr;
    }

    public static String truncate(String inputString, int width) {
        if (width == 0) {
            return inputString;
        }

        String inputBuffer = inputString;
        
        // String inputBuffer = trimZeros(inputString);


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

    public static String setPrecision(Object value, int precision) {
        return formatString(value, 0, precision);
    }


    public static String formatString(Object value, int width, int precision) {
        StringBuilder formatted = new StringBuilder();
        
        if (value != null) {
        
            if (value instanceof Number && precision != -1) {
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                
                // Set precision for all numbers
                numberFormat.setMaximumFractionDigits(precision);
                numberFormat.setMinimumFractionDigits(precision);
                
                // Set grouping separator (e.g., comma in 1,000)
                numberFormat.setGroupingUsed(true);
                
                formatted.append(numberFormat.format(value));
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

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.') {
                decimalPos = i;
                break;
            }
        }

        if (decimalPos == -1) {
            return 0;
        }

        for (int i = decimalPos + 1; i < str.length(); i++) {
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
        int lineLength = 0;
        String[] words = input.split(" ");

        for (String word : words) {
            // Check if adding the word to the current line exceeds the width
            if (lineLength + word.length() + 1 > width) {
                result.append("\n");
                lineLength = 0;
            }

            // Append the word with a space
            if (lineLength > 0) {
                result.append(" ");
                lineLength++;
            }
            result.append(word);
            lineLength += word.length();
        }

        return result.toString();
    }
}
