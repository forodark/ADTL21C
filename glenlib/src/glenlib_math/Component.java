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

    public void print() {
        if(content.length == 1) {
            if (content[0] instanceof Term)
                Style.println(((Term) content[0]).toString());
            else if (content[0] instanceof Expression)
                ((Expression) content[0]).print();
        } else {
            for(int i = 0; i < content.length; i++) {
                if (content[i] instanceof Term) {
                    if (this instanceof Add) {
                        if (((Term) content[i]).toString().toCharArray()[0] != '-') {
                            Style.print(((Term) content[i]).toString());
                            if (i != content.length - 1) {
                                Style.print(" + ");
                            }
                        }
                        else {
                            Style.print(((Term) content[i]).toString().substring(1, ((Term) content[i]).toString().length()));
                            if (i != content.length - 1) {
                                Style.print(" - ");
                            }
                        }

                    }
                    else if (this instanceof Multiply) {
                        Style.print(((Term) content[i]).toString());
                    }
                    
                }
                else if (content[i] instanceof Expression) {
                    if (this instanceof Add) {
                        Style.print("(");
                        ((Expression) content[i]).print();
                        Style.print(")");

                        if (i != content.length - 1) {
                            Style.print(" + ");
                        }
                    }
                    else if (this instanceof Multiply) {
                        Style.print("(");
                        ((Expression) content[i]).print();
                        Style.print(")");
                    }
                }

            }
        }
    }

    // TODO: add handling for subtract and divide, make sure signs are handled by all objects esp. Term
    public static Component parse(String input) {
        Object[] buffer;

        String[] analyzed = analyze(input);
        if(analyzed[0].contains("+") || analyzed[0].contains("-")) {
            buffer = new Object[Str.countSubstr(analyzed[0], "+") + Str.countSubstr(analyzed[0], "-") + 1];
            String[] parts = analyzed[0].split("\\+");
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
            return new Add(buffer);
        }
        else {
            buffer = new Object[Str.countSubstr(analyzed[0], ")(") + 1];
        }

    
        return null;
    }

    // TODO: add handling for nested groupings like (2x + (3x + 4z))
    public static String[] analyze(String input) {
        int grouping_count = Str.countSubstr(input, "(");
        // Style.println(grouping_count);

        String[] buffer = new String[grouping_count + 1];
        int opening_indexes[] = new int[grouping_count];
        int closing_indexes[] = new int[grouping_count];
    
        // Iterating through all parentheses pairs
        for (int i = 0; i < grouping_count; i++) {
            if (i == 0) {
                opening_indexes[i] = input.indexOf('(');
            } else {
                opening_indexes[i] = input.indexOf('(', opening_indexes[i]+1);

            }
            closing_indexes[i] = input.indexOf(')', opening_indexes[i]);

    
            // Extracting the contents inside the current pair of parentheses
            String insideParentheses = input.substring(opening_indexes[i] + 1, closing_indexes[i]);
            buffer[i+1] = insideParentheses;
    
            // Creating the output string with removed contents inside current parentheses
            input = input.substring(0, opening_indexes[i]+1) + input.substring(closing_indexes[i]);
        }
        buffer[0] = input;


        return buffer;
    }
    
    

    
    
    
    
    
    

}
