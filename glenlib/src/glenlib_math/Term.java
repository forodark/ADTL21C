package glenlib_math;

import java.util.Arrays;

import glenlib.Str;
import glenlib.Style;

public class Term {
    private int coefficient;
    private Variable[] variables;
    private Boolean inverted = false;

    public Term(String term) {
        this.coefficient = parseCoefficient(term);
        this.variables = parseVariable(term);
    }

    public Term(Boolean inverted, String term) {
        this.inverted = inverted;
        this.coefficient = parseCoefficient(term);
        this.variables = parseVariable(term);
    }

    public String toString() {
        String buffer = Str.convertString(coefficient);
        for (Variable variable : variables) {
            buffer += variable.toString();
        }
        return buffer;
    }

    public void print() {
        Style.print(toString());
    }

    public int getCoefficient() {
        return coefficient;
    }
    
    public Variable[] getVariables() {
        return variables;
    }

    public Boolean getInverted() {
        return inverted;
    }

    public static Term parse(String input) {
        return new Term(input);
    }

    public static int parseCoefficient(String term) {
        int signMultiplier = 1;
    
        if (term.startsWith("-")) {
            signMultiplier = -1;  // Set the multiplier to -1 if the term starts with a negative sign.
            term = term.substring(1);  // Trim off that negative sign.
        }
    
        String buffer = term.replaceAll("[^\\d]", "");  // Now, let's proceed with the digit grab.
        int coefficient = Integer.parseInt(buffer) * signMultiplier;  // Apply the sign multiplier.
        return coefficient;
    }
    
    

    public static Variable[] parseVariable(String term) {
        if (term.startsWith("-")) {
            term = term.substring(1);  // Trim off that negative sign.
        }


        String[] variableChunks = term.split("(?=[a-zA-Z])");
    
        variableChunks = Arrays.stream(variableChunks)
                                .filter(chunk -> !chunk.matches("\\d+"))
                                .toArray(String[]::new);
    
        Variable[] variables = new Variable[variableChunks.length];
    
        for (int i = 0; i < variableChunks.length; i++) {
            variables[i] = createVariable(variableChunks[i]);
        }
    
        return variables;
    }
    
    private static Variable createVariable(String chunk) {
        char base = chunk.charAt(0);
    
        int exponent = 1;
        int radical = 1;
    
        // Check if there's an exponent or radical
        if (chunk.length() > 1) {
            if (chunk.contains("^")) {
                String[] parts = chunk.split("\\^");
                int[] indexes = parseIndex(parts[1]);
                exponent = indexes[0];
                if(indexes.length > 1)
                    radical = indexes[1];
            }
        }
    
        return new Variable(base, exponent, radical);
    }

    public static int[] parseIndex(String index) {
        String[] parts = index.split("/");
        int[] indexes = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            indexes[i] = Integer.parseInt(parts[i].replaceAll("[()]", ""));
        }
        return indexes;
    }
    
    
    
    
    
    


}
