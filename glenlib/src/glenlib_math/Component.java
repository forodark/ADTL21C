package glenlib_math;

import java.util.ArrayList;
import java.util.List;

import glenlib.Style;

public class Component {
    private Object[] content;

    public Component(Object[] content) {
        this.content = content;
    }

    public Object[] getContent() {
        return content;
    }

    public void print() {
        if(content.length == 1) {
            if (content[0] instanceof Term)
                Style.println(((Term) content[0]).getTerm());
            else if (content[0] instanceof Expression)
                ((Expression) content[0]).print();
        } else {
            for(int i = 0; i < content.length; i++) {
                if (content[i] instanceof Term) {
                    
                    if (this instanceof Add) {
                        Style.print(((Term) content[i]).getTerm());
                        if (i != content.length - 1) {
                            Style.print(" + ");
                        }

                    }
                    else if (this instanceof Multiply) {
                        Style.print(((Term) content[i]).getTerm());
                    }
                    
                }
                else if (content[i] instanceof Expression) {
                    if (this instanceof Add) {
                        if (content[i] instanceof Add)
                        Style.print("(");
                        ((Expression) content[i]).print();
                        if (content[i] instanceof Add)
                        Style.print(")");


                        if (i != content.length - 1) {
                            Style.print(" + ");
                        }
                    }
                    else if (this instanceof Multiply) {
                        Style.print("(");
                        ((Expression) content[i]).print();
                        Style.print(")");
                    }
                }

            }
        }
    }
    // TODO: fix this
    public static Component parse(String input) {
        List<Object> partialObjects = new ArrayList<>();
        List<Object> multiplicationObjects = new ArrayList<>(); // To hold multiply operations
    
        StringBuilder termBuilder = new StringBuilder();
        int parenthesesCount = 0;
    
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
    
            if (c == '(') {
                if (termBuilder.length() > 0) {
                    partialObjects.add(new Term(termBuilder.toString().trim()));
                    termBuilder.setLength(0);
                }
                parenthesesCount++;
                termBuilder.append(c);
            } else if (c == ')') {
                parenthesesCount--;
                termBuilder.append(c);
                if (parenthesesCount == 0) {
                    String termString = termBuilder.toString().trim();
                    if (termString.startsWith("(") && termString.endsWith(")")) {
                        // Remove outer parentheses
                        termString = termString.substring(1, termString.length() - 1);
                    }
                    multiplicationObjects.add(new Term(termString));
                    termBuilder.setLength(0);
                }
            } else if (c == '*' && parenthesesCount == 0) {
                if (termBuilder.length() > 0) {
                    partialObjects.add(new Term(termBuilder.toString().trim()));
                    termBuilder.setLength(0);
                }
            } else if (c == '+' && parenthesesCount == 0) {
                if (termBuilder.length() > 0) {
                    partialObjects.add(new Term(termBuilder.toString().trim()));
                    termBuilder.setLength(0);
                }
            } else {
                termBuilder.append(c);
            }
        }
    
        if (termBuilder.length() > 0) {
            partialObjects.add(new Term(termBuilder.toString().trim()));
        }
    
        // If there are multiplicationObjects, add them as a Multiply object
        if (!multiplicationObjects.isEmpty()) {
            partialObjects.add(new Multiply(multiplicationObjects.toArray()));
        }
    
        return new Component(partialObjects.toArray());
    }

}
