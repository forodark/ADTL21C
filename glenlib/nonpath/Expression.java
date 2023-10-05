package glenlib_math;

import glenlib.In;
import glenlib.Str;
import glenlib.Style;
import glenlib.Util;

public class Expression {
    private Component numerator;
    private Component denominator;
    private Expression exponent;
    private Boolean negative = false;
    private Boolean inverted = false;

    public static String valid_operators = "+-*/^";

    public Expression(Component numerator, Component denominator, Boolean negative, Boolean inverted, Expression exponent) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.negative = negative;
        this.inverted = inverted;
        this.exponent = exponent;
    }

    public Expression(String input) {
        Expression expression = Expression.parse(input);
        this.numerator = expression.getNumerator();
        this.denominator = expression.getDenominator();
        this.exponent = expression.getExponent();
    }

    public Expression(String input, Boolean negative) {
        Expression expression = Expression.parse(input);
        this.numerator = expression.getNumerator();
        this.denominator = expression.getDenominator();
        this.exponent = expression.getExponent();
        this.negative = negative;
    }

    public Expression(Boolean inverted, String input) {
        Expression expression = Expression.parse(input);
        this.numerator = expression.getNumerator();
        this.denominator = expression.getDenominator();
        this.exponent = expression.getExponent();
        this.inverted = inverted;
    }

    public Expression(String input, Boolean negative, Boolean inverted) {
        Expression expression = Expression.parse(input);
        this.numerator = expression.getNumerator();
        this.denominator = expression.getDenominator();
        this.exponent = expression.getExponent();
        this.inverted = inverted;
        this.negative = negative;
    }

    public Expression() {
        this.numerator = null;
    }

    public Component getNumerator() {
        return numerator;
    }

    public Component getDenominator() {
        return denominator;
    }

    public Expression getExponent() {
        return exponent;
    }

    public Boolean getNegative() {
        return negative;
    }

    public Boolean getInverted() {
        return inverted;    
    }

    // TODO: refine string to use multiple grouping types {[()]}
    public String toString() {
        String expression = "";

        if (denominator == null && numerator.getContent().length == 1 && negative) {
            expression += "-";
        }

        if (!negative && denominator != null && numerator.getContent().length > 1) {
            expression += "(" + numerator.toString() + ")";
        }
        else expression += numerator.toString();

        if (denominator != null) {
            expression += "/";
            if (denominator.getContent().length > 1) {
                expression += "(" + denominator.toString() + ")";
            }
            else expression += denominator.toString();

        }

        // if (negative) {
        //     expression = "-(" + expression + ")";
        // }

        if(exponent != null) {

            if (denominator == null && numerator.getContent().length <= 1 && numerator.getContent()[0] instanceof Term) {
                expression = expression + "^";
            }
            else {
                expression = "(" + expression + ")^";
            }

            if (exponent.getDenominator() == null && exponent.getExponent() == null && exponent.getNumerator().getContent().length <= 1) {
                expression += exponent.toString();
            }
            else {
                expression += "(" + exponent.toString() + ")";
            }

        }


        return expression;
    }

    public void print() {
        // try {
            Style.print(toString());
        // }
        // catch (Exception e) {
        //     Style.printColor(Style.RED, "Print Error: Cannot print invalid expression\n");
        // }

    }
    
    public static Expression parse(String input) {
        // try {
            return parseImpl(input);
        // }
        // catch (Exception e) {
        //     Style.printColor(Style.RED, "Parse Error: " + e.getMessage() + "\n");
        //     return new Expression();
        // }

    }

    public static Expression parseImpl(String input) {
        Style.printColor(Style.RED, "PARSING: " + input + "\n");
        String buffer = Component.trimGroupings(Str.removeSpaces(input));
        Component numeratorObjects;
        Component denominatorObjects;
        Expression exponent;
        Boolean negative = false;
        Boolean inverted = false;

        
      
        if ((buffer.toCharArray()[0] == '-' && buffer.length() > 1 && buffer.toCharArray()[1] == '(')) {
            buffer = buffer.substring(1);
            negative = true;
        }

        if (getExponentOperator(buffer) != -1) {
            Style.printColor(Style.RED, "THERES AN EXPONEnT OPERATOR");
            String exponent_string = buffer.substring(getExponentOperator(buffer)+1, buffer.length()).trim();
            Style.printColor(Style.GREEN, "Setting exponent to: " + exponent_string + "\n");
            exponent = new Expression(exponent_string);
        }
        else {
            // Style.printColor(Style.YELLOW, exponent_string);
            exponent = null;
        }

        if (getFractionBar(buffer) != -1) {
            Style.printColor(Style.RED, "THERES A FRACTION BAR");

            String numeratorString = buffer.substring(0, getFractionBar(buffer)).trim();

            int denominator_end = buffer.length();
            if (getExponentOperator(buffer) != -1) {
                denominator_end = getExponentOperator(buffer);
            }
            String denominatorString = buffer.substring(getFractionBar(buffer)+1, denominator_end).trim();
            Style.println(numeratorString + "WTF");
            Style.println(denominatorString + "WTF");
            numeratorObjects = Component.parse(numeratorString);
            denominatorObjects = Component.parse(denominatorString);

            Style.printColor(Style.GREEN, "Numerator for " + buffer + ": " + numeratorString + "\n");
            Style.printColor(Style.GREEN, "Denominator for " + buffer + ": " + denominatorString + "\n");

        } else {
            String numeratorString;

            if (exponent != null) {
                numeratorString = buffer.substring(0, getExponentOperator(buffer)).trim();
            }
            else {
                numeratorString = buffer.trim();
            }
            numeratorObjects = Component.parse(numeratorString);
            denominatorObjects = null;
        }

        // exponent = new Expression("4z");

        Style.println("NEGATIVE: " + negative + "\n");
        Style.println("INVERTED: " + inverted + "\n");

        Expression expression = new Expression(numeratorObjects, denominatorObjects, negative, inverted, exponent);

        if (exponent != null) {
            Style.line();
            String expr_string = expression.toString();
            Style.printColor(Style.RED, "EXPR STRING TEST: " + expr_string);
            Style.nl();
            Style.line();
        }

        return expression;

    }
    
    public static int getFractionBar(String input) {
        return getMainOperation(input, '/');
    }

    public static int getExponentOperator(String input) {
        return getMainOperation(input, '^');
    }

    public static int getMainOperation(String input, char operation) {
        return getMainOperation(input, operation, false, 0);
    }

    public static int getMainOperation(String input, char operation, Boolean multiple, int start_index) {
        int index = -1;

        int depth = 0;
        int i = start_index;

        if(start_index > input.length()-2) {
            return -1;
        }

        if (input.length() <= 1) {
            return -1;
        }

        for(; i < input.length(); i++) {
            // Style.println(i + " " + input.charAt(i)); 

            if (input.charAt(i) == '(') {
                depth++;
            }
            if (input.charAt(i) == ')') {
                depth--;
            }
            try {
                if (depth == 0 && input.charAt(i+1) == operation) {
                    i++;
                    index = i;
                    Style.println("FOUND AT: " + index);
                    break;
                }
            }
            catch (IndexOutOfBoundsException e) {
                break;
            }


            if (depth == 0 && !multiple) {
                return -1;
            }
        }
        i++;
        // Style.println("Fraction bar: " + index);

        for (; i < input.length(); i++) {
        //     Style.println(i + " " + input.charAt(i)); 
            if (input.charAt(i) == '(') {
                depth++;
            }
            if (input.charAt(i) == ')') {
                depth--;
            }
            if (i == input.length() - 1 && depth == 0) {
                break;
            }
            if (depth == 0 && !multiple && 
            valid_operators.contains(String.valueOf(input.charAt(i)))
            ) {
                return -1;
            }

        }
        return index;
    }
    

}
