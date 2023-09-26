package glenlib_objects;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Obj {
    protected static Object invokeGetter(Object data, String getter_method) {
        try {
            Class<?> rowClass = data.getClass();
            Method method = rowClass.getMethod(getter_method);
            return method.invoke(data);
        } catch (Exception e) {
            return "N/A";
        }
    }


    public static <T> T[] swap(T[] objects, int index_a, int index_b) {
        T temp = objects[index_a];
        objects[index_a] = objects[index_b];
        objects[index_b] = temp;
        return objects;
    }

    public static <T> T[] search(T[] objects, String getter_method, String query) {
        T[] results = Arrays.copyOf(objects, 0);
    
        for (int i = 0; i < objects.length; i++) {
            if (invokeGetter(objects[i], getter_method).toString().toLowerCase().contains(query.toLowerCase())) {
                results = Arrays.copyOf(results, results.length + 1);
                results[results.length - 1] = objects[i];
            }
        }
    
        return results;
    }

    public static <T> T[] search(T[] objects, String[] getter_methods, String query) {
        T[] results = Arrays.copyOf(objects, 0);
    
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < getter_methods.length; j++) {
                if (invokeGetter(objects[i], getter_methods[j]).toString().toLowerCase().contains(query.toLowerCase())) {
                    results = Arrays.copyOf(results, results.length + 1);
                    results[results.length - 1] = objects[i];
                    break;
                }       
            }
        }
    
        return results;
    }

    public static <T> T[] remove(T[] objects, int index) {
        for (; index < objects.length - 1; index++) {
            objects[index] = objects[index+1];
        }
        return Arrays.copyOf(objects, objects.length - 1);

    }

    public static <T> T[] append(T[] objects, T object) {
        objects = Arrays.copyOf(objects, objects.length + 1);
        objects[objects.length - 1] = object;
        return objects;
    }
}
