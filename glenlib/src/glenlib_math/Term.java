package glenlib_math;

import glenlib.Str;

// TODO: add separate coefficient and variable to store data
public class Term {
    private int coefficient;
    private Variable[] variables;

    public Term(String term) {
        this.coefficient = parseCoefficient(term);
        this.variables = parseVariable(term);
    }

    public String getTerm() {
        String buffer = Str.convertString(coefficient);
        for 
    }
    public static int parseCoefficient(String term) {
        String buffer = term.replaceAll("[^\\d]", "");
        int coefficient = Integer.parseInt(buffer);
        return coefficient;
    }

    public static Variable[] parseVariable(String term) {
        String buffer = term.replaceAll("\\d", "");
        return buffer.toCharArray()
    }



}
