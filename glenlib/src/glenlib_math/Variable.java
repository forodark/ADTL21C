package glenlib_math;

public class Variable {
    private char base;
    private int exponent;
    private int radical;

    public Variable(char base, int exponent, int radical) {
        this.base = base;
        this.exponent = exponent;
        this.radical = radical;
    }
    public Variable(char base, int exponent) {
        this.base = base;
        this.exponent = exponent;
        this.radical = 1;
    }
    
    public Variable(char base) {
        this.base = base;
        this.exponent = 1;
        this.radical = 1;
    }

    public char getBase() {
        return base;
    }

    public int getExponent() {
        return exponent;
    }

    public int getRadical() {
        return radical;
    }

}
