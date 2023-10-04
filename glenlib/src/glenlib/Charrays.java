package glenlib;

public class Charrays {
    public static char[] filterEmpty(char[] array) {
        int count = 0;

        // Count non-empty characters
        for (char c : array) {
            if (c != '\0') {
                count++;
            }
        }

        // Create a new char array without empty characters
        char[] result = new char[count];
        int index = 0;

        // Copy non-empty characters to the new array
        for (char c : array) {
            if (c != '\0') {
                result[index++] = c;
            }
        }

        return result;
    }

    public static char[] insertChar(char[] array, char c, int index) {
        char[] result = new char[array.length + 1];
    
        System.arraycopy(array, 0, result, 0, index);
        result[index] = c;
        System.arraycopy(array, index, result, index + 1, array.length - index);
    
        return result;
    }
}
