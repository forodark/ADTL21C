package glenlib_math;

// TODO: add separate coefficient and variable to store data
public class Term {
    private String term;

    public Term(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }
    public int getCoefficient() {
        String buffer = term.replaceAll("[^\\d]", "");
        int coefficient = Integer.parseInt(buffer);
        return coefficient;
    }

    public String getVariable() {
        return term.replaceAll("\\d", "");
    }

}
