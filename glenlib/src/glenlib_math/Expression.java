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

    public String toString() {
        String expression = "";
        if (negative) {
            expression += "-(";
        }
        expression += numerator.toString();
        if (denominator != null) {
            expression += " / ";
            expression += denominator.toString();
        }
        if (negative) {
            expression += ")";
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
            if (input.contains("/")) {
                String[] parts = input.split("/");
                String numeratorString = parts[0].trim();
                String denominatorString = parts[1].trim();
        
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
    
    
    
}
