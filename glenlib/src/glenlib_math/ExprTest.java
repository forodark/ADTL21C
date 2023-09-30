package glenlib_math;

import glenlib.In;
import glenlib.Style;

public class ExprTest {
    public static void main(String[] args) {
        Expression test = new Expression(
            new Add(new Object[]{new Term("2x"),new Term("3x"),new Term("4z")})
        );

        Expression test2 = Expression.parse("2x + 2y");
        // Term test2 = new Term("5x^(2/3)y^3");
        // Style.println(test2.getVariables()[1].getVariable());
        // Expression test2 =
        //     new Multiply(new Object[]{new Term("5x"),test});
        // Expression test3 = new Expression(
        //     new Add(new Object[]{test, test2}),
        //     new Multiply(new Object[]{test, test2})
        // );
        test2.print();
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
