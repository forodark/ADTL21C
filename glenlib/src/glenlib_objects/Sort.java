package glenlib_objects;
import java.util.Arrays;

public class Sort {

    
    public static <T> T[] selection(T[] objects, String getter_method) {
        T[] buffer = Arrays.copyOf(objects, objects.length);
        T[] sorted = Arrays.copyOf(objects, 0);
    
        while (buffer.length > 0) {
            int lowest = 0;
            for (int index = 0; index < buffer.length; index++) {
                if (!compare(buffer[lowest], buffer[index], getter_method)) {
                    lowest = index;
                }
            }
    
            // Adds the lowest to the sorted array
            sorted = Arrays.copyOf(sorted, sorted.length + 1);
            sorted[sorted.length - 1] = buffer[lowest];
    
            // Deletes the lowest
            for (int i = lowest; i < buffer.length - 1; i++) {
                buffer[i] = buffer[i + 1];
            }
            buffer = Arrays.copyOf(buffer, buffer.length - 1);
        }
    
        return sorted;
    }
    
    public static <T> T[] insertion(T[] objects, String getter_method) {
        T[] buffer = Arrays.copyOf(objects, objects.length);
    
        for (int i = 1; i < buffer.length; i++) {
            for (int j = i; j > 0 && !compare(buffer[j - 1], buffer[j], getter_method); j--) {
                buffer = Obj.swap(buffer, j, j - 1);
            }
        }
    
        return buffer;
    }

    public static <T> T[] bubble(T[] objects, String getter_method) {
        T[] buffer = Arrays.copyOf(objects, objects.length);
    
        while(!isAscending(buffer, getter_method)) {
            for (int i = 0; i < buffer.length - 1; i++) {
                if (!compare(buffer[i], buffer[i + 1], getter_method)) {
                    buffer = Obj.swap(buffer, i, i + 1);
                    System.out.println("TEST");
                }
            }
        }
    
        return buffer;
    }

    public static <T> T[] invert(T[] objects) {
        T[] buffer = Arrays.copyOf(objects, objects.length);

        for (int i = 0; i < buffer.length / 2; i++) {
            buffer = Obj.swap(buffer, i, buffer.length - i - 1);
        }
    
        return buffer;
    }

    public static <T> T[] random(T[] objects) {
        T[] buffer = Arrays.copyOf(objects, objects.length);

        for (int i = 0; i < buffer.length; i++) {
            int randomIndex = (int) (Math.random() * buffer.length);
            buffer = Obj.swap(buffer, i, randomIndex);
        }
    
        return buffer;
    }

    

    //returns true if student a is alphabetically before student b
    public static Boolean compare(Object a, Object b, String getter_method) {
        return Obj.invokeGetter(a, getter_method).toString().compareTo(Obj.invokeGetter(b, getter_method).toString()) <= 0;
    }


    public static <T> Boolean isAscending(T[] objects, String getter_method) {
        for(int i = 0; i < objects.length - 1; i++) {
            if(!compare(objects[i], objects[i + 1], getter_method)) {
                return false;
            }
        }
        return true;
    }

    public static <T> Boolean isDescending(T[] objects, String getter_method) {
        for(int i = 0; i < objects.length - 1; i++) {
            if(compare(objects[i], objects[i + 1], getter_method)) {
                return false;
            }
        }
        return true;
    }


}
