package glenlib_math;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import glenlib.Str;
import glenlib.Style;
import glenlib.Charrays;

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
            if (content[0] instanceof Term) {
                Style.println(((Term) content[0]).getCoefficient());
                if (((Term) content[0]).getCoefficient() < 0) {
                    component += "-";
                }
                component += (((Term) content[0]).toString());
            }
            else if (content[0] instanceof Expression) {
                if (((Expression) content[0]).getNegative()) {
                    component += "-";
                }
                component += ((Expression) content[0]).toString();
            }
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
                        if (i != content.length - 1) {
                            if (content[i+1] instanceof Term && !((Term) content[i+1]).getInverted()) {
                                component += ("*");
                            } else if (content[i+1] instanceof Expression && !((Expression) content[i+1]).getInverted()) {
                                component += ("*");
                            } else {
                                component += ("/");
                            }
                            
                        }
                    }
                    
                }
                else if (content[i] instanceof Expression) {
                    String expr_string = ((Expression) content[i]).toString();
                    Style.printColor(Style.YELLOW, "EXPR STRING for content[" + i + "]: " + expr_string + "\n");
                    Style.printColor(Style.YELLOW, "IS NEGATIVE?: " + ((Expression) content[i]).getNegative() + "\n");

                    if (this instanceof Add) {
                        if (((Expression) content[i]).getNegative() && i == 0) {
                            component += "-(" + expr_string + ")";
                        }
                        else {
                            component += "(" + expr_string + ")";
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
                    if (!((Expression) content[i]).getNegative()) {
                        expr_string = "(" + expr_string + ")";
                    }
                    else {
                        expr_string = "-(" + expr_string + ")";
                    }
                        component += expr_string;
                        if (i != content.length - 1) {
                            if (content[i+1] instanceof Term && !((Term) content[i+1]).getInverted()) {
                                component += ("*");
                            } else if (content[i+1] instanceof Expression && !((Expression) content[i+1]).getInverted()) {
                                Style.println("Inverted: " + ((Expression) content[i+1]).getInverted());
                                component += ("*");
                            } else {
                                component += ("/");
                            }
                            
                        }
                    }
                }

            }
        }
        return component;
    }

    public void print() {
        Style.print(toString());
    }

    public static Component parse(String input) {
        Object[] buffer;

        input = appendMultiply(input);
        input = trimGroupings(input);

        String[] analyzed = analyze(input);

        for (int k = 0; k < analyzed.length; k++) {
            Style.println("Analyzed: " + analyzed[k]);
        }

        Style.println("Has Exponent: " + hasExponent(analyzed[0]));

        Boolean has_multi = (analyzed[0].contains("*") || analyzed[0].contains("/"));
        Boolean has_add = (analyzed[0].contains("+") || hasSubtract(analyzed[0]));
        Boolean has_expo = hasExponent(analyzed[0]);

        // if (has_expo && !has_add && !has_multi) {
        //     analyzed = analyze(regroup(input, analyzed[0]));
        // }

        //multiple types of operations
        if((has_add && has_multi) || (has_multi && has_expo) || (has_expo && has_add)) {
            Style.println("TEST");
            analyzed = analyze(regroup(input, analyzed[0]));
            for(int j = 0; j < analyzed.length; j++) {
                Style.printColor(Style.YELLOW, "Analyzed: " + analyzed[j] + "\n");
            }
            
        }

        

        if(analyzed[0].contains("*") || analyzed[0].contains("/")) {
            buffer = new Object[Str.countSubstr(analyzed[0], "*") + Str.countSubstr(analyzed[0], ")(") + Str.countSubstr(analyzed[0], "/") + 1];

            String[] parts = analyzed[0].split("[\\*\\/]");
            char[] operations = getMultiplyOperations(analyzed[0]);

            Style.println(Style.color(Style.GREEN, operations.length + ""));
            for (char operation : operations) {
                Style.println(Style.color(Style.GREEN, operation + ""));
            }

            for (String part : parts) {
                Style.println(Style.color(Style.BLUE, part + "\n"));
            }


            // old code from previous handling of terms next to expressions
            // Adjust the result to keep the parenthesis with the content
            // for (int i = 0; i < parts.length - 1; i++) {
            //     if (parts[i].endsWith("(")) {
            //         parts[i] += parts[i + 1];
            //         parts[i + 1] = "";
            //     }
            // }
            // parts = Arrays.stream(parts)
            // .filter(s -> !s.isEmpty())
            // .filter(s -> !s.equals("*"))
            // .toArray(String[]::new);

            // for (int i = 0 ; i < parts.length; i++) {

            //     if (parts[i].contains("()") && !parts[i].equals("()")) {

            //         String excess = parts[i].substring(2, parts[i].length());

            //         if (!excess.contains("/") && !excess.contains("*")) {
            //             parts = Arrays.copyOf(parts, parts.length + 1);
            //             buffer = Arrays.copyOf(buffer, buffer.length + 1);
            //             parts[parts.length - 1] = excess;
            //         }
            //         parts[i] = ("()");

            //     }
            // }

            // for (String part : parts) {
            //     Style.printColor(Style.RED, part + "\n");
            // }


            int expr_count = 1;
            for(int i = 0; i < parts.length; i++) {
                Boolean negative = false;
                if(parts[i].equals("-()")) {
                    negative = true;
                }

                if(parts[i].equals("()") || parts[i].equals("-()")) {

                    if (operations[i] == '*') {
                        Style.println(Style.color(Style.BLUE, "Multiplying"));
                        buffer[i] = new Expression(analyzed[expr_count], negative);
                        Style.println("ADDED EXPR: " + analyzed[expr_count]);
                        expr_count++;
                    }
                    else if (operations[i] == '/') {
                        Style.println(Style.color(Style.BLUE, "Dividing"));
                        buffer[i] = new Expression(analyzed[expr_count], negative, true);
                        Style.println("Inverted: " + ((Expression) buffer[i]).getInverted());
                        expr_count++;
                    }

                }
                else {
                    if (operations[i] == '*') {
                        buffer[i] = new Term(parts[i]);
                    }
                    else if (operations[i] == '/') {
                        // Style.printColor(Style.GREEN, "-" + parts[i]);
                        buffer[i] = new Term(true, parts[i]);
                    }

                }
            }
            return new Multiply(buffer);

        }
        else if(analyzed[0].contains("+") || hasSubtract(analyzed[0])) {
            buffer = new Object[Str.countSubstr(analyzed[0], "+") + Str.countSubstr(analyzed[0], "-") + 1];
            String[] parts;
            char[] operations = getAddOperations(analyzed[0]);

            if (operations[0] == '-') {
                parts = analyzed[0].substring(1).split("[\\+\\-]");
            }
            else {
                parts = analyzed[0].split("[\\+\\-]");
            }


            for (char operation : operations) {
                Style.println(Style.color(Style.PURPLE, operation + ""));
            }


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
                        buffer[i] = new Expression(analyzed[expr_count], true);
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
        else if (has_expo) {
            input = regroupExponents(input);


            buffer = new Object[1];
            buffer[0] = new Expression(input);
            return new Multiply(buffer);
        }
        else {
            buffer = new Object[1];
            buffer[0] = new Term(analyzed[0]);
            return new Add(buffer);
        }
    }

    // TODO: add handling for multiple operations like (2x + 3y)(3z) + 4x
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
    

    public static char[] getAddOperations(String input) {
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

    public static char[] getMultiplyOperations(String input) {
        int length = input.length();
        int count = 1;
    
        // Counting the number of + and - signs
        for (int i = 0; i < length; i++) {
            if (i < input.length()-1 && (input.charAt(i) == ')' && input.charAt(i+1) == '(')) {
                count++;
            }
            else if (input.charAt(i) == '*' || input.charAt(i) == '/') {
                count++;
            }

        }
    
        // Creating the array with the right size
        char[] operations = new char[count];
    
        // Assigning the signs to the array
        int arrayIndex = 0;
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                operations[arrayIndex++] = '*';
            }
            if (i < input.length()-1 && input.charAt(i) == ')' && input.charAt(i+1) == '(') {
                operations[arrayIndex++] = '*';
            }
            if (input.charAt(i) == '*' || input.charAt(i) == '/') {
                operations[arrayIndex++] = input.charAt(i);
            }
        }
    
        return operations;
    }

    public static int getExponentCount(String input) {
        int length = input.length();
        int count = 1;
    
        int index = 0;
        // Counting the number of + and - signs
        while(true) {
            index = Expression.getMainOperation(input, '^', true, index);
            if (index == -1) {
                break;
            }
            count++;
            Style.println("COUNT: " + count);
            index++;
        }
        return count;
    
    }

    public static String regroupExponents(String input) {
        int exponent_count = getExponentCount(input);
        Style.println("EXPONENT COUNT: " + exponent_count);
        for(; exponent_count > 2; exponent_count--) {
            int new_open = 0;
            for (int i = 0; i < exponent_count-2; i++) {
                new_open = Expression.getMainOperation(input, '^', true, new_open);
            }
            
            
            input = Str.insertChar(input, ')', input.length());
            input = Str.insertChar(input, '(', new_open+1);

            Style.printColor(Style.YELLOW, input + "\n");

        }
        return input;
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
            if(buffer.length() == 0) {
                return buffer;
            }
            if(buffer.toCharArray()[0] != '(' || buffer.toCharArray()[buffer.length()-1] != ')') {
                return buffer;   
            }
            // if (analyze(buffer)[0] == "()/()") {
            //     return buffer;
            // }

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

    
    
    //TODO: bug here incorrect grouping somehow
    public static String regroup(String input, String analyzed) {

        char[] operations = getOperations(input, analyzed);

        // String[] buffer = new String[multiply_group_count+1];
        // Style.println("MULTIPLY GROUP: " + multiply_group_count);
        // int buffer_index = 1;



        // * main regrouping happens here
        for (int cycle = 0; cycle < 2; cycle++) {
            int new_open = 0;
            int new_close = 0;
            Boolean found_add = false;


            if (cycle == 0 && !hasExponent(analyzed)) {
                Style.println("SKIPPING CYCLE 0: ");
                cycle++;
            }

            else if (cycle == 1) {
                String[] new_analyzed = analyze(input);
                Style.println("CYCLE 1 ANALYZED: " + new_analyzed[0]);
                if(!(new_analyzed[0].contains("*") || new_analyzed[0].contains("/"))
                || !(new_analyzed[0].contains("+") || hasSubtract(new_analyzed[0]))) {
                    Style.println("SKIPPING CYCLE 1: ");
                    break;
                }
                operations = getOperations(input, new_analyzed[0]);
            }



            

            for(int i = 0; i < operations.length; i++) {
                Style.println("CURRENTLY CHECKING: " + i);

                if (found_add == false && (operations[i] == '+' || operations[i] == '-')) {
                    found_add = true;
                    Style.printColor(Style.RED, "FOUND FIRST ADD: " + "\n");
                    if (i < operations.length-2 && operations[i+1] == '+') {
                        Style.println("Previous OPEN For i: " + i + " New open: " + new_open);
                        new_open = Expression.getMainOperation(input, '+', true, new_open)+1;
                        Style.println("Changed OPEN For i: " + i + " New open: " + new_open);
                    }
                    else if (i < operations.length-2 && operations[i+1] == '-') {
                        new_open = Expression.getMainOperation(input, '-', true, new_open)+1;
                    }
                    if (i == 0) {
                        i++;
                    }

                }
                else if (i < operations.length-1 && (operations[i] == '+' || operations[i] == '-') && (operations[i+1] == '*' || operations[i+1] == '/')) {
                    if (operations[i] == '+') {
                        new_open = Expression.getMainOperation(input, '+', true, new_open)+1;
                    }
                    else if (operations[i] == '-') {
                        new_open = Expression.getMainOperation(input, '-', true, new_open)+1;
                    }
                    Style.println("SET OPEN For i: " + i + " New open: " + new_open);
                    // i++;
                }
                else if (cycle == 0 && i < operations.length-1 && (operations[i] == '*' || operations[i] == '/' || operations[i] == '+' || operations[i] == '-') && (operations[i+1] == '^')) {
                    if (operations[i] == '*') {
                        new_open = Expression.getMainOperation(input, '*', true, new_open)+1;
                    }
                    else if (operations[i] == '/') {
                        new_open = Expression.getMainOperation(input, '/', true, new_open)+1;
                    }
                    else if (operations[i] == '+') {
                        new_open = Expression.getMainOperation(input, '+', true, new_open)+1;
                    }
                    else if (operations[i] == '-') {
                        new_open = Expression.getMainOperation(input, '-', true, new_open)+1;
                    }
                    Style.println("SET OPEN For i: " + i + " New open: " + new_open);
                    // i++;
                }

                if ((cycle == 1 && (operations[i] == '*' || operations[i] == '/')) || 
                (cycle == 0 && operations[i] == '^')) {
                    Style.println("CURRENT CYCLE: " + cycle);
                    found_add = false;
                    // Style.println("Found * at" + i);

                    if (cycle == 0 && operations[i] == '^') {
                        for(i = i+1; i < operations.length; i++) {
                            if (operations[i] == '-') {
                                new_close = Expression.getMainOperation(input, '-', true, new_open);
                                Style.println("Found - at" + i);
                                break;
                            }
                            else if (operations[i] == '+') {
                                new_close = Expression.getMainOperation(input, '+', true, new_open);
                                Style.println("Found + at" + i);
                                break;
                            }
                            else if (operations[i] == '*') {
                                new_close = Expression.getMainOperation(input, '*', true, new_open);
                                Style.println("Found * at" + i);
                                break;
                            }
                            else if (operations[i] == '/') {
                                new_close = Expression.getMainOperation(input, '/', true, new_open);
                                Style.println("Found / at" + i);
                                break;
                            }
                            else if (i == operations.length-1) {
                                new_close = input.length();
                                Style.println("Found END at" + i);
                                break;
                            }
                        }
                    }
                    
                    if (cycle == 1 && (operations[i] == '*' || operations[i] == '/')) {
                        found_add = false;
                        for(i = i+1; i < operations.length; i++) {
                            if (operations[i] == '-') {
                                new_close = Expression.getMainOperation(input, '-', true, new_open);
                                Style.println("Found - at" + i);
                                break;
                            }
                            else if (operations[i] == '+') {
                                new_close = Expression.getMainOperation(input, '+', true, new_open);
                                Style.println("Found + at" + i);
                                break;
                            }
                            else if (operations[i] == '^') {
                                new_close = Expression.getMainOperation(input, '^', true, new_open);
                                Style.println("Found ^ at" + i);
                                break;
                            }
                            else if (i == operations.length-1) {
                                new_close = input.length();
                                Style.println("Found END at" + i);
                                break;
                            }
                        }
                    }

                    Style.println("New open: " + new_open);
                    Style.println("New close: " + new_close);
                    // String new_sub_expression = input.substring(new_open, new_close);
                    // buffer[buffer_index++] = new_sub_expression;
                    // Style.printColor(Style.BLUE, new_sub_expression + "\n");

                    StringBuilder grouper = new StringBuilder(input);
                    grouper.insert(new_open, '(');
                    grouper.insert(new_close+1, ')');

                    new_open = new_close+3;
                    input = grouper.toString();
                    Style.printColor(Style.GREEN, "Cycle: " + cycle + " Updated string to: " + input + "\n");
                }
            }
        }

        input = trimGroupings(input);

        Style.printColor(Style.GREEN, "Regrouped String: " + input + "\n");

        return input;
    }

    public static String appendMultiply(String input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            result.append(currentChar);

            if (i < input.length() - 1) {
                char nextChar = input.charAt(i + 1);

                if ((Character.isDigit(currentChar) || currentChar == ')' || Character.isLetter(currentChar)) &&
                    (nextChar == '(')) {
                    result.append('*');
                } else if (currentChar == ')' &&
                        (Character.isDigit(nextChar) || Character.isLetter(nextChar))) {
                    result.append('*');
                }
            }
        }

        return result.toString();
    }
    






    public static boolean hasSubtract(String input) {
        boolean hasSubtract = false;

        // Loop through the string
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            // Check for a standalone '-'
            if (currentChar == '-' && i > 0 && i < input.length() - 1) {
                char prevChar = input.charAt(i - 1);
                char nextChar = input.charAt(i + 1);

                // If it's not part of '*-' or '/-', set hasSubtract to true
                if (prevChar != '*' && prevChar != '/' && nextChar != '-') {
                    hasSubtract = true;
                    break;
                }
            }
        }
        Style.println("hasSubtract: " + hasSubtract);

        return hasSubtract;
    }
    
    public static boolean hasExponent(String input) {
        for (int i = 1; i < input.length() - 1; i++) {
            char currentChar = input.charAt(i);

            if (currentChar == '^') {
                char leftChar = input.charAt(i - 1);
                char rightChar = input.charAt(i + 1);

                if (!(Character.isLetter(leftChar) && Character.isDigit(rightChar))) {
                    return true; // Found "^" but not between a letter and a digit.
                }
            }
        }

        return false; // If we reached here, no such pattern found.
    }

    public static char[] getOperations(String input, String analyzed) {
        char[] operations = new char[0];
        Boolean minus_found = false;
        int array_index = 0;
        for (int i = 0; i < analyzed.length(); i++) {

            if (i == 0 && analyzed.charAt(i) == '-') {
                minus_found = true;
            } 
            else if (analyzed.charAt(i) == '+' || analyzed.charAt(i) == '-' 
            || analyzed.charAt(i) == '*' || analyzed.charAt(i) == '/' || analyzed.charAt(i) == '^') {
                operations = Charrays.insertChar(operations, analyzed.charAt(i), array_index);
                array_index++;
            }
        }
        
        for(int i = 0; i < operations.length; i++) {
            if (i == 0) {
                if (operations[i] == '*' || operations[i] == '/') {
                    operations = Charrays.insertChar(operations, '*', i);
                }
                else if (minus_found) {
                    operations = Charrays.insertChar(operations, '-', i);
                } 
                else if (operations[i] == '+' || operations[i] == '-') {
                    operations = Charrays.insertChar(operations, '+', i);
                }
                else if (operations[i] == '^') {
                    operations = Charrays.insertChar(operations, '^', i);
                }
                i++;
            }
            else if (operations[i] == '*' || operations[i] == '/') {
                operations = Charrays.insertChar(operations, '*', i);
                i++;
            }
            else if (operations[i] == '^') {
                operations = Charrays.insertChar(operations, '^', i);
                i++;
            }
        }

        operations = Charrays.filterEmpty(operations);

        Style.println("OPERATION LENGTH: " + operations.length);
        for (int j = 0; j < operations.length; j++) {
            Style.println(Style.color(Style.RED, (operations[j]) + ""));
        }

        return operations;
    }
}

