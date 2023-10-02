package glenlib_math;

import glenlib.Style;

public class Expression {
    private Component numerator;
    private Component denominator;
    private int exponent = 1;
    private int radical = 1;
    private Boolean negative = false;

    public Expression(Component numerator, Component denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Expression(Component numerator) {
        this.numerator = numerator;
        this.denominator = null;
    }

    public Expression(String input) {
        Expression expression = Expression.parse(input);
        this.numerator = expression.getNumerator();
        this.denominator = expression.getDenominator();
    }

    public Expression(String input, Boolean negative) {
        Expression expression = Expression.parse(input);
        this.numerator = expression.getNumerator();
        this.denominator = expression.getDenominator();
        this.negative = expression.getNegative();
    }

    public Component getNumerator() {
        return numerator;
    }

    public Component getDenominator() {
        return denominator;
    }

    public int getExponent() {
        return exponent;
    }

    public int getRadical() {
        return radical;
    }

    public Boolean getNegative() {
        return negative;
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
        return expression;
    }

    public void print() {
        Style.print(toString());
    }
    

    public static Expression parse(String input) {
        Component numeratorObjects;
        Component denominatorObjects;
        Boolean negative = false;

        if (input.toCharArray()[0] == '-') {
            input = input.substring(1);
            negative = true;
        }
    
        try {
            if (Component.analyze(input)[0].contains("/")) {

                String numeratorString = input.substring(0, getFractionBar(input)).trim();
                String denominatorString = input.substring(getFractionBar(input)+1, input.length()).trim();
                Style.println(numeratorString);
                Style.println(denominatorString);
        
                numeratorObjects = Component.parse(numeratorString);
                denominatorObjects = Component.parse(denominatorString);
            } else {
                String numeratorString = input.trim();
                numeratorObjects = Component.parse(numeratorString);
                denominatorObjects = null;
            }
        
            Expression expression = new Expression(numeratorObjects, denominatorObjects);
        
            return expression;
        } catch (Exception e) {
            return new Expression(input, negative);
        }
    }
    
    public static int getFractionBar(String input) {
        int index = 0;

        int depth = 0;

        for(int i = 0; i < input.length(); i++) {

            if (input.charAt(i) == '(') {
                depth++;
            }
            if (input.charAt(i) == ')') {
                depth--;
            }
            
            if (input.charAt(i) == '/' && depth == 0) {
                index = i;
            }
        }

        return index;
    }
    
}
