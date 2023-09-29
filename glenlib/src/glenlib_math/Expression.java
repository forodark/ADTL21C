package glenlib_math;
import java.util.ArrayList;
import java.util.List;

import glenlib.Style;

public class Expression {
    private Component numerator;
    private Component denominator;

    public Expression(Component numerator, Component denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Expression(Component numerator) {
        this.numerator = numerator;
        this.denominator = null;
    }

    public Component getNumerator() {
        return numerator;
    }

    public Component getDenominator() {
        return denominator;
    }

    public void print() {
        numerator.print();
        if(denominator == null)
            return;
        else
            Style.print(" / ");
        denominator.print();
    }
    

    // public static Expression parse(String input) {
    //     Component numeratorObjects;
    //     Component denominatorObjects;
    
    //     if (input.contains("/")) {
    //         String[] parts = input.split("/");
    //         String numeratorString = parts[0].trim();
    //         String denominatorString = parts[1].trim();
    
    //         numeratorObjects = parsePartial(numeratorString);
    //         denominatorObjects = parsePartial(denominatorString);
    //     } else {
    //         String numeratorString = input.trim();
    //         numeratorObjects = parsePartial(numeratorString);
    //         denominatorObjects = null;
    //     }
    
    //     Expression expression = new Expression(numeratorObjects, denominatorObjects);
    
    //     return expression;
    // }
    
    // public static Component parsePartial(String input) {
    //     List<Object> partialObjects = new ArrayList<>();
    //     List<Object> multiplicationObjects = new ArrayList<>(); // To hold multiply operations
    
    //     StringBuilder termBuilder = new StringBuilder();
    //     int parenthesesCount = 0;
    
    //     for (int i = 0; i < input.length(); i++) {
    //         char c = input.charAt(i);
    
    //         if (c == '(') {
    //             if (termBuilder.length() > 0) {
    //                 partialObjects.add(new Term(termBuilder.toString().trim()));
    //                 termBuilder.setLength(0);
    //             }
    //             parenthesesCount++;
    //             termBuilder.append(c);
    //         } else if (c == ')') {
    //             parenthesesCount--;
    //             termBuilder.append(c);
    //             if (parenthesesCount == 0) {
    //                 String termString = termBuilder.toString().trim();
    //                 if (termString.startsWith("(") && termString.endsWith(")")) {
    //                     // Remove outer parentheses
    //                     termString = termString.substring(1, termString.length() - 1);
    //                 }
    //                 multiplicationObjects.add(new Term(termString));
    //                 termBuilder.setLength(0);
    //             }
    //         } else if (c == '*' && parenthesesCount == 0) {
    //             if (termBuilder.length() > 0) {
    //                 partialObjects.add(new Term(termBuilder.toString().trim()));
    //                 termBuilder.setLength(0);
    //             }
    //         } else if (c == '+' && parenthesesCount == 0) {
    //             if (termBuilder.length() > 0) {
    //                 partialObjects.add(new Term(termBuilder.toString().trim()));
    //                 termBuilder.setLength(0);
    //             }
    //         } else {
    //             termBuilder.append(c);
    //         }
    //     }
    
    //     if (termBuilder.length() > 0) {
    //         partialObjects.add(new Term(termBuilder.toString().trim()));
    //     }
    
    //     // If there are multiplicationObjects, add them as a Multiply object
    //     if (!multiplicationObjects.isEmpty()) {
    //         partialObjects.add(new Multiply(multiplicationObjects.toArray()));
    //     }
    
    //     return partialObjects.toArray();
    // }
    
    
}
