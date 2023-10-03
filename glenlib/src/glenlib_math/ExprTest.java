package glenlib_math;

import glenlib.In;
import glenlib.Str;
import glenlib.Style;

public class ExprTest {
    public static void main(String[] args) {
        // Expression test = new Expression(
        //     new Add(new Object[]{new Term("2x"),new Term("3x"),new Term("4z")})
        // );

        // Expression test = Expression.parse("2x+3y");
        // test.print();
        // Style.nl();

        // Term test = new Term("-6y");
        // Style.println(test.getCoefficient());
        // // Style.println(test.getVariables)
        // test.print();

        // String test = Component.trimGroupings("((2x+3y)/(4z+3x))/(5z+6x)");
        // Style.println(test);


        Expression test1 = Expression.parse("(2x+3y)(4z+3x)+(5z+6x)");
        test1.print();

        // Expression test1 = Expression.parse("(5z+6x)");
        // test1.print();

        // String[] test3 = Component.analyze("((2x+3y)/(4z+3x))/((5z+6x))");
        // char[] test2 = Component.getMultiplyOperations(test3[0]);
        // for (int i = 0; i < test2.length; i++) {
        //     Style.println(test2[i]);
        // }

        // // int test4 = Expression.getFractionBar("((2x+3y)/(4z+3x))/(5z+6x)");
        // // Expression test1 = Expression.parse("((2x+3y)/(4z+3x))/(5z+6x)");
        // // // test1.getDenominator().print();

        // Style.line();
        // // Style.println(test4);
        // for (int i = 0; i < test2.length; i++) {
        //     Style.println(test2[i]);
        // }
        // for (int i = 0; i < test3.length; i++) {
        //     Style.println(test3[i]);
        // }           
        // Style.println(test2);
        // test1.print();
        Style.nl();
        // char[] test3 = Component.getOperations(test2[0]);
        // for(int i = 0; i < test3.length; i++)
        //     Style.println(test3[i]);
        // Style.nl();
        // Style.line();

        // Style.println("Analysis: " + Component.analyze("(2x+3y)+(4z+3x)-6y")[0]);
        // Style.line();
        // Style.println("Analysis: " + Component.analyze("(2x+4z+3y)+6y")[0]); 
        // Style.line();

        // int test = Str.findNthOccurrence("1231231123", "1", 3);
        // Style.println(test);

        // String test2[] = Component.analyze("-(2x+3y)+(4z+3x)-6y");
        // Style.println(test2.length);
        // Style.line();
        // char[] test3 = Component.getOperations(test2[0]);
        // Style.println(test2[0]);
        // Style.println(test3.length);
        // for(int i = 0; i < test3.length; i++)
        //     Style.println(test3[i]);
        // Style.nl();
        // Style.line();
        

        // Style.println(Str.countSubstr("1231231123", "1"));

        // Component test3 = new Add(new Object[]{new Term("2x"),new Term("2y")});
        // Term test2 = new Term("5x^(2/3)y^3");
        // Style.println(test2.getVariables()[1].getVariable());
        // Expression test2 =
        //     new Multiply(new Object[]{new Term("5x"),test});
        // Expression test3 = new Expression(
        //     new Add(new Object[]{test, test2}),
        //     new Multiply(new Object[]{test, test2})
        // );

        // Style.nl();
        // Style.println(((Term) test.getNumerator()[1]).getCoefficient());
        // while(true) {
        // Expression test = Expression.parse(In.getString("Enter expression: "));
        // test.print();
        // Style.nl();
        // Style.println(((Term) test.getNumerator()[0]).getTerm());
        // Style.println(((Term) test.getNumerator()[1]).getTerm());
        // }
    }

}
