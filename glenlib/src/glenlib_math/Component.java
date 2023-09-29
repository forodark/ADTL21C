package glenlib_math;

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

}
