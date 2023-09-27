package glenlib_math;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

public class Expression {
    private String expression;
    private Map<String, Double> variables = new HashMap<>();

    public Expression(String expression) {
        this.expression = expression;
        parseVariables();
    }

    public Expression add(Expression other) {
        String result = this.expression + " + " + other.expression;
        return new Expression(result);
    }

    public Expression subtract(Expression other) {
        String result = this.expression + " - " + other.expression;
        return new Expression(result);
    }

    public double evaluate() {
        // Implement parsing and evaluation logic for expressions
        String[] terms = expression.split("\\s*[+\\-]\\s*");
        double result = 0.0;

        for (String term : terms) {
            term = term.trim();
            result += evaluateTerm(term);
        }

        return result;
    }

    private void parseVariables() {
        if (expression.contains("||")) {
            String[] parts = expression.split("\\|\\|");
            String[] variablePairs = parts[1].split("\\s*,\\s*");

            for (String pair : variablePairs) {
                String[] keyValue = pair.split("\\s*=\\s*");
                if (keyValue.length == 2) {
                    String variableName = keyValue[0].trim();
                    double variableValue = Double.parseDouble(keyValue[1].trim());
                    variables.put(variableName, variableValue);
                }
            }
        }
    }

    private double evaluateTerm(String term) {
        try {
            return Double.parseDouble(term);
        } catch (NumberFormatException e) {
            // Handle variables
            if (variables.containsKey(term)) {
                return variables.get(term);
            }
            // Handle other cases if needed
            return 0.0; // Placeholder for unhandled terms
        }
    }

    public String toString() {
        return expression;
    }
}

