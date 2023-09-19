import glenlib.In;
import glenlib.Style;
import glenlib.Util;
// import glenlib_menu.*;

public class Main {
    public static void main(String[] args) throws Exception {
        calculator();
        Util.exit();
    }

    public static void calculator() {
        while(true) {
            Util.clear();
            Style.printTitle(31, "Calculator");
            double num1 = In.getInt("Enter a number [1/2]: ");
            double num2 = In.getInt("Enter a number [2/2]: ");

            Style.line();
            switch(In.getChar("Choose an operation (+, -, *, /, %): ")) {
                case '+': Style.println("Result: " + num1 + " + " + num2 + " = " + (num1 + num2)); break;
                case '-': Style.println("Result: " + num1 + " - " + num2 + " = " + (num1 - num2)); break;
                case '*': Style.println("Result: " + num1 + " * " + num2 + " = " + (num1 * num2)); break;
                case '/': Style.println("Result: " + num1 + " / " + num2 + " = " + (num1 / num2)); break;
                case '%': Style.println("Result: " + num1 + " % " + num2 + " = " + (num1 % num2)); break;
                default: Style.println("Invalid operation."); break;
            }

            Style.line();
            if(In.getBool("Would you like to go again? (y/n): ") == true) {
                continue;
            } else if (In.getBool("Are you sure you want to exit? (y/n): ") == false) {
                continue;
            } else {return;}
        }
    }
}
