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
        this.negative = negative;
    }

    public Expression(Boolean inverted, String input) {
        Expression expression = Expression.parse(input);
        this.numerator = expression.getNumerator();
        this.denominator = expression.getDenominator();
        this.inverted = inverted;
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

        if (!negative && denominator != null && numerator.getContent().length > 1) {
            expression += "(" + numerator.toString() + ")";
        }
        else expression += numerator.toString();

        if (denominator != null) {
            expression += " / ";
            if (denominator.getContent().length > 1) {
                expression += "(" + denominator.toString() + ")";
            }
            else expression += denominator.toString();

        }

        if (negative) {
            expression = "-(" + expression + ")";
        }

        if(exponent != null) {

            expression = "(" + expression + ")^";
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
        Style.print(toString());
    }
    

    public static Expression parse(String input) {
        Style.printColor(Style.RED, "PARSING: " + input + "\n");
        String buffer = Component.trimGroupings(Str.removeSpaces(input));
        Component numeratorObjects;
        Component denominatorObjects;
        Expression exponent;
        Boolean negative = false;
        Boolean inverted = false;
      
        if (buffer.toCharArray()[0] == '-') {
            buffer = buffer.substring(1);
            negative = true;
        }

            if (getExponentOperator(buffer) != -1) {
                String exponent_string = buffer.substring(getExponentOperator(buffer)+1, buffer.length()).trim();
                // Style.printColor(Style.GREEN, exponent_string + "\n");
                exponent = new Expression(exponent_string);
            }
            else {
                // Style.printColor(Style.YELLOW, exponent_string);
                exponent = null;
            }


            if (getFractionBar(buffer) != -1) {


            String numeratorString = buffer.substring(0, getFractionBar(buffer)).trim();

            int denominator_end = buffer.length();
            if (getExponentOperator(buffer) != -1) {
                denominator_end = getExponentOperator(buffer);
            }
            String denominatorString = buffer.substring(getFractionBar(buffer)+1, denominator_end).trim();
            Style.println(numeratorString);
            Style.println(denominatorString);
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

        // Style.line();
        // exponent.print();
        // Style.nl();
        // Style.line();

        Expression expression = new Expression(numeratorObjects, denominatorObjects, negative, inverted, exponent);

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
            if (input.charAt(i+1) == operation && depth == 0) {
                i++;
                index = i;
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
            if (depth == 0 && !multiple) {
                return -1;
            }

        }
        return index;
    }
    

}
