package glenlib_math;

import glenlib.Style;

public class Expression {
    private Component numerator;
    private Component denominator;
    private int exponent = 1;
    private int radical = 1;

    public Expression(Component numerator, Component denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Expression(Component numerator) {
        this.numerator = numerator;
        this.denominator = null;
    }

    public Expression(String input) {
        this.numerator = Expression.parse(input).getNumerator();
        this.denominator = Expression.parse(input).getDenominator();
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

    public void print() {
        numerator.print();
        if(denominator == null)
            return;
        else
            Style.print(" / ");
        denominator.print();
    }
    

    public static Expression parse(String input) {
        Component numeratorObjects;
        Component denominatorObjects;
    
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
            return new Expression(input);
        }
    }
    
    
    
}
