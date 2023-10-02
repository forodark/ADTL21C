package glenlib_math;

import java.util.ArrayList;
import java.util.List;

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

    // TODO: add handling for subtract and divide, make sure signs are handled by all objects esp. Term
    public static Component parse(String input) {
        Object[] buffer;

        String[] analyzed = analyze(input);

        if(analyzed[0].contains("+") || analyzed[0].contains("-")) {
            buffer = new Object[Str.countSubstr(analyzed[0], "+") + Str.countSubstr(analyzed[0], "-") + 1];
            String[] parts = analyzed[0].split("[\\+\\-]");

            char[] operations = getOperations(analyzed[0]);
            int expr_count = 1;
            // Style.println(parts.length);
            for(int i = 0; i < parts.length; i++) {
                Style.println(operations[i]);
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
            buffer = new Object[Str.countSubstr(analyzed[0], ")(") + 1];
        }

    
        return null;
    }

    // TODO: add handling for nested groupings like (2x + (3x + 4z)) or multiple operations like (2x + 3y)(3z) + 4x
    public static String[] analyze(String input) {
        int grouping_count = Str.countSubstr(input, "(");
    
        String[] buffer = new String[grouping_count + 1];
        int opening_indexes[] = new int[grouping_count];
        int closing_indexes[] = new int[grouping_count];
    
        // Iterating through all parentheses pairs
        for (int i = 0; i < grouping_count; i++) {
            if (i == 0) {
                opening_indexes[i] = input.indexOf('(');
            } else {
                opening_indexes[i] = input.indexOf('(', opening_indexes[i-1]+1);  // Fixing the index
    
            }
            closing_indexes[i] = input.indexOf(')', opening_indexes[i]);
    
            String insideParentheses = input.substring(opening_indexes[i] + 1, closing_indexes[i]);
            buffer[i+1] = insideParentheses;
    
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
    
    
    
    
    

    
    
    
    
    
    

}
