package glenlib;

import java.util.concurrent.TimeUnit;

public class test {
    public static void main(String[] args) {
        style.line();
        style.printCentered("Hello", 11);
        System.out.print("|");
        util.sleep(2);
        util.clear();
        style.printCentered("Hello", 11);
    }
}
