import glenlib.Style;
import glenlib.In;
import glenlib.Util;

public class Main {
    public static void main(String[] args) throws Exception {
        sumAve();
    }
    
    public static void sumAve() {
        Util.clear();
        Style.println(Style.color(Style.GREEN, "Enter a series of integers (non-integers to exit): "));
        int sum = 0;
        double average = 0;
        int num_count = 0;
        while (true) {
            try {
                int number = Integer.parseInt(In.getString(""));
                sum += number;
                num_count++;
            } catch (Exception e) {
                break;
            }
        }
        average = sum / (double) num_count;
        Style.printColor(Style.GREEN, "Average: " + average);
    }
}
