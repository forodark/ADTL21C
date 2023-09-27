package glenlib_math;

import java.util.Arrays;
import java.util.Random;

public class Calc {

    public static int rand(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min + 1)) + min;
    }

    public static double rand(double min, double max) {
        return rand(min, max, 2);
    }

    public static double rand(double min, double max, int precision) {
        Random rand = new Random();
        double result = rand.nextDouble() * (max - min) + min;
        double multiplier = Math.pow(10, precision);
        return Math.round(result * multiplier) / multiplier;
    }

    public static double mean(Object[] data) {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] instanceof Number) {
                sum += ((Number) data[i]).doubleValue();
            } else {
                return 0;
            }
        }
        return sum / data.length;
    }

    public static double variance(Object[] data) {
        double mean = mean(data);
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] instanceof Number) {
                sum += Math.pow(((Number) data[i]).doubleValue() - mean, 2);
            } else {
                return 0;
            }
        }
        return sum / data.length;
    }

    public static double sd(Object[] data) {
        return Math.sqrt(variance(data));
    }

    public static Object median(Object[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        Arrays.sort(data);
        return data[data.length / 2];
    }

    public static Object[] mode(Object[] data) {
        if (data == null || data.length == 0) {
            return new Object[0];
        }
    
        int maxCount = 0;
        Object[] modes = new Object[data.length];
        int modeCount = 0;
    
        for (int i = 0; i < data.length; i++) {
            int count = 0;
            for (int j = 0; j < data.length; j++) {
                if (data[i].equals(data[j])) {
                    count++;
                }
            }
    
            boolean alreadyAdded = false;
            for (int k = 0; k < modeCount; k++) {
                if (modes[k] != null && modes[k].equals(data[i])) {
                    alreadyAdded = true;
                    break;
                }
            }
    
            if (!alreadyAdded) {
                if (count > maxCount) {
                    maxCount = count;
                    modeCount = 1;
                    modes[0] = data[i];
                } else if (count == maxCount) {
                    modes[modeCount] = data[i];
                    modeCount++;
                }
            }
        }
    
        Object[] result = new Object[modeCount];
        System.arraycopy(modes, 0, result, 0, modeCount);
        return result;
    }
    
    
    
}
