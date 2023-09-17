package glenlib_table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tbl<T> {
    private List<T> data;
    private List<TableColumn<?>> columns;
    private String title;

    public Tbl<T> Array(T[] data) {
        this.data = Arrays.asList(data);
        return this;
    }

    public Tbl<T> List(List<T> data) {
        this.data = data;
        return this;
    }

    public Tbl<T> Col(String columnName, String format, String methodName) {
        if (columns == null) {
            columns = new ArrayList<>();
        }
        columns.add(new TableColumn<>(columnName, data, format, methodName));
        return this;
    }

    public Tbl<T> Title(String title) {
        this.title = title;
        return this;
    }

    public void build() {
        if (data == null || columns == null) {
            throw new IllegalStateException("Data and columns must be initialized.");
        }
        Table table = new Table(columns);
        table.printFull(title);
    }

    public void buildPage() {
        if (data == null || columns == null) {
            throw new IllegalStateException("Data and columns must be initialized.");
        }
        PageTable table = new PageTable(columns);
        table.printFull(title);
    }

    public void auto(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String field_name = field.getName();
            String formatted_name = field_name.substring(0, 1).toUpperCase() + field_name.substring(1);
            Col(formatted_name, "%" + (field_name.length()+8) + "s", "get" + formatted_name);
        }
        this.title = "* " + clazz.getSimpleName() + " Table *";
        build();
    }


}
/*
 * This introduces a new way to build tables
 * Note: if using list then use .List() rather than .Array()
 *        new Tbl<Fruit>()
            .Array(fruits)
            .Col("ID", "%4d", "getId")
            .Col("Fruit", "%10s", "getFruit")
            .Col("Price", "%5.2f", "getPrice")
            .Title("List of Fruits")
            .build();
    OR
            new Tbl<Fruit>()
            .Array(fruits)
            .auto(Fruit.class);
    
 */