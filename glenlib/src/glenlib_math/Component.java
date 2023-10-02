package glenlib_math;

import java.util.Arrays;

import glenlib.Str;
import glenlib.Style;

public class Component {
    private Object[] content;

    public Component(Object[] content) {
        this.content = content;
    }

    public Object[] getContent() {
        return content;
    }

    public String toString() {
        String component = "";

        if(content.length == 1) {
            if (content[0] instanceof Term)
                component += (((Term) content[0]).toString());
            else if (content[0] instanceof Expression)
                component += ((Expression) content[0]).toString();
        } else {
            for(int i = 0; i < content.length; i++) {
                if (content[i] instanceof Term) {
                    if (this instanceof Add) {
                        if (((Term) content[i]).getCoefficient() >= 0 || i == 0) {
                            component += (((Term) content[i]).toString());
                        }
                        else {
                            component += (((Term) content[i]).toString().substring(1, ((Term) content[i]).toString().length()));
                        }
                        if (i != content.length - 1) {
                            if (content[i+1] instanceof Term && ((Term) content[i+1]).getCoefficient() >= 0) {
                                
                                component += (" + ");
                            } else if (content[i+1] instanceof Expression && !(((Expression) content[i+1]).getNegative())) {
                                
                                component += (" + ");
                            } else {
                                
                                component += (" - ");
                            }

                        }

                    }
                    else if (this instanceof Multiply) {
                        component += (((Term) content[i]).toString());
                    }
                    
                }
                else if (content[i] instanceof Expression) {
                    String expr_string = ((Expression) content[i]).toString();
                    if (!((Expression) content[i]).getNegative()) {
                        expr_string = "(" + expr_string + ")";
                    }

                    if (this instanceof Add) {
                        if (!((Expression) content[i]).getNegative() || i == 0) {
                            component += expr_string;
                        }
                        else {
                            component += (expr_string.substring(1, ((Expression) content[i]).toString().length()));
                        }
                        if (i != content.length - 1) {
                            if (content[i+1] instanceof Term && ((Term) content[i+1]).getCoefficient() >= 0) {
                                
                                component += (" + ");
                            } else if (content[i+1] instanceof Expression && !(((Expression) content[i+1]).getNegative())) {
                                
                                component += (" + ");
                            } else {
                                
                                component += (" - ");
                            }

                        }
                    }
                    else if (this instanceof Multiply) {
                        component += expr_string;
                    }
                }

            }
        }
        return component;
    }

    public void print() {
        Style.print(toString());
    }

    // TODO: add handling for divide
    public static Component parse(String input) {
        Object[] buffer;

        String[] analyzed = analyze(trimGroupings(input));
        Style.println("Analyzed: " + analyzed[0]);

        if(analyzed[0].contains("*") || analyzed[0].contains(")(")) {
            buffer = new Object[Str.countSubstr(analyzed[0], "*") + Str.countSubstr(analyzed[0], ")(") + 1];

            String[] parts = analyzed[0].split("(?=(\\()|\\))");

            // Adjust the result to keep the parenthesis with the content
            for (int i = 0; i < parts.length - 1; i++) {
                if (parts[i].endsWith("(")) {
                    parts[i] += parts[i + 1];
                    parts[i + 1] = "";
                }
            }
            parts = Arrays.stream(parts)
                        .filter(s -> !s.isEmpty())
                        .toArray(String[]::new);

            for (int i = 0 ; i < parts.length; i++) {

                if (parts[i].contains("()") && !parts[i].equals("()")) {
                    parts = Arrays.copyOf(parts, parts.length + 1);
                    buffer = Arrays.copyOf(buffer, buffer.length + 1);
                    parts[parts.length - 1] = parts[i].substring(2, parts[i].length());
                    parts[i] = ("()");
                }
            }


            int expr_count = 1;
            for(int i = 0; i < parts.length; i++) {
                if(parts[i].equals("()")) {
                    buffer[i] = new Expression(analyzed[expr_count]);
                    expr_count++;
                }
                else {
                    buffer[i] = new Term(parts[i]);
                }
            }
            return new Multiply(buffer);

        }
        else if(analyzed[0].contains("+") || analyzed[0].contains("-")) {
            buffer = new Object[Str.countSubstr(analyzed[0], "+") + Str.countSubstr(analyzed[0], "-") + 1];
            String[] parts = analyzed[0].split("[\\+\\-]");

            char[] operations = getOperations(analyzed[0]);
            int expr_count = 1;
            // Style.println(parts.length);
            for(int i = 0; i < parts.length; i++) {
                // Style.println(operations[i]);
                if(parts[i].equals("()")) {
                    if (operations[i] == '+') {
                        buffer[i] = new Expression(analyzed[expr_count]);
                        expr_count++;
                    }
                    else if (operations[i] == '-') {
                        buffer[i] = new Expression("-" + analyzed[expr_count]);
                        expr_count++;
                    }

                }
                else {
                    if (operations[i] == '+') {
                        buffer[i] = new Term(parts[i]);
                    }
                    else if (operations[i] == '-') {
                        // Style.printColor(Style.GREEN, "-" + parts[i]);
                        buffer[i] = new Term("-" + parts[i]);
                    }

                }
            }
            return new Add(buffer);
        }
        else {
            buffer = new Object[1];
            buffer[0] = new Term(analyzed[0]);
            return new Add(buffer);
        }
    }

    // TODO: add handling for nested groupings like (2x + (3x + 4z)) or multiple operations like (2x + 3y)(3z) + 4x
    public static String[] analyze(String input) {
        int grouping_count = getGroupingCount(input);
    
        String[] buffer = new String[grouping_count + 1];
        int opening_indexes[] = new int[grouping_count];
        int closing_indexes[] = new int[grouping_count];

        int j = 0;
        int open_count = 0;
        int close_count = 0;

        // Iterating through all parentheses pairs
        for (int i = 0; i < grouping_count; i++) {

            //(2x+3y)+(4z+3x)-6y
            for(; j < input.length(); j++) {
                // Style.println(j + ": " + open_count + " " + close_count + " " + input.charAt(j));
                if (input.charAt(j) == '(') {
                    // Style.println("found (");
                    open_count++;
                    if (open_count == close_count+1) {
                        opening_indexes[i] = j;
                        // Style.println("Found opening index " + i + ": " + opening_indexes[i]);
                    }
                }
                if (input.charAt(j) == ')') {
                    // Style.println("found )");
                    close_count++;
                    if (open_count == close_count) {
                        closing_indexes[i] = j;
                        // Style.println("Found closing index " + i + ": " + closing_indexes[i]);
                        break;
                    }
                }
            }

            // Style.println("Opening index "+ (i+1) + ": " + opening_indexes[i]);
            // Style.println("Closing index "+ (i+1) + ": " + closing_indexes[i]);
    
            String insideParentheses = input.substring(opening_indexes[i] + 1, closing_indexes[i]);
            buffer[i+1] = insideParentheses;
            // Style.println("Inside length: " + insideParentheses.length());
            j -= (insideParentheses.length()-1);
            // Style.println("Inside parentheses " + (i+1) + ": " + insideParentheses);
    
            input = input.substring(0, opening_indexes[i]+1) + input.substring(closing_indexes[i]);  // Adjusting the substring
        }
        buffer[0] = input;
    
        return buffer;
    }
    

    public static char[] getOperations(String input) {
        int length = input.length();
        int count = 0;
    
        // Counting the number of + and - signs
        for (int i = 0; i < length; i++) {
            char currentChar = input.charAt(i);
            if (i == 0 && currentChar != '-') {
                count++;
            }
            if (currentChar == '+' || currentChar == '-') {
                count++;
            }
        }
    
        // Creating the array with the right size
        char[] operations = new char[count];
    
        // Assigning the signs to the array
        int arrayIndex = 0;
        for (int i = 0; i < length; i++) {
            char currentChar = input.charAt(i);


            if (i == 0 && currentChar != '-') {
                operations[arrayIndex++] = '+';
            } else if (currentChar == '+' || currentChar == '-') {
                operations[arrayIndex++] = currentChar;
            }
        }
    
        return operations;
    }
    
    public static int getGroupingCount(String input) {
        int grouping_count = 0;
        int open_count = 0;
        int close_count = 0;
        int nested_count = 0;
        for(int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                open_count++;
            }
            if (input.charAt(i) == ')') {
                close_count++;
                if (open_count != close_count) {
                    nested_count++;
                }
                else {
                    grouping_count++;
                }
            }
        }
        // Style.println("Grouping count: " + grouping_count);
        // Style.println("Nested count: " + nested_count);

        return grouping_count;
    }
    
    // trims redundant groupings
    public static String trimGroupings(String input) {

        String buffer = input;
        while (true) {
            if(buffer.toCharArray()[0] != '(' || buffer.toCharArray()[buffer.length()-1] != ')') {
                return buffer;   
            }

            int depth = 1;

            for(int i = 1; i < buffer.length(); i++) {

                if (i == buffer.length() - 1) {
                    
                    buffer = buffer.substring(1, buffer.length()-1);
                    break;
                }

                if (buffer.charAt(i) == '(') {
                    depth++;
                }
                if (buffer.charAt(i) == ')') {
                    depth--;
                }
                
                if (depth == 0) {
                    return buffer;
                }
            }
        }

    }
    
    

    
    
    
    
    
    

}
