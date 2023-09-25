import java.lang.reflect.Method;
import java.util.Arrays;

public class Search {
        protected static Object invokeGetter(Object data, String getter_method) {
        try {
            Class<?> rowClass = data.getClass();
            Method method = rowClass.getMethod(getter_method);
            return method.invoke(data);
        } catch (Exception e) {
            return "N/A";
        }
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
}
