package glenlib_table;

import java.lang.reflect.Method;
import java.util.List;

import glenlib.Str;
import glenlib.Style;
import glenlib.Util;

public class Table {
    public static final int TABLE_PAGE_LENGTH = 10;

    protected List<TableColumn<?>> columns;

    public Table(List<TableColumn<?>> columns) {
        this.columns = columns;
    }

    protected Object invokeGetter(List<?> data, int index, String getterMethod) {
        try {
            Class<?> rowClass = data.get(index).getClass();
            Method method = rowClass.getMethod(getterMethod);
            return method.invoke(data.get(index));
        } catch (Exception e) {
            return "N/A";
        }
    }

    public void printFull() {
        printFull("", "");
    }

    public void printFull(String title) {
        printFull(title, "");
    }

    public void printFull(String title, String caption) {
        int num_columns = columns.size();
        int table_width = num_columns + 1;

        for (TableColumn<?> column : columns) {
            table_width += Str.extractNumber(column.getFormat());
        }
        
        Util.clear();
        // Print title
        Style.line(table_width);

        if (!title.isEmpty()) {
            Style.printCentered(table_width, title);
            System.out.println();
            Style.line(table_width);
        }

        if(!caption.isEmpty()) {
            Style.println(Str.paragraph(caption, table_width));
            Style.line(table_width);
        }

        // Print headers
        System.out.print("|");
        for (TableColumn<?> column : columns) {
            int column_width = Str.extractNumber(column.getFormat());

            if (column.getHeader().length() > column_width) {
                Style.print(Str.truncate(column.getHeader(), column_width));
            }
            else {
            Style.printCentered(column_width, column.getHeader());
            }
            
            System.out.print("|");  
        }

        System.out.println();

        // Print content
        for (int i = 0; i < columns.get(0).getData().size(); i++) {
            System.out.print("|");
            for (TableColumn<?> column : columns) {
                List<?> column_data = column.getData();
                Object value = invokeGetter(column_data, i, column.getGetterMethod());
                System.out.print(" " + Str.formatString(value, Str.extractNumber(column.getFormat())-1, Str.extractDecimal(column.getFormat())) + "|");
            }
            System.out.println();
        }
        Style.line(table_width);
    }

    /* Sample:

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Fruit {
    private int id;
    private String fruit;
    private double price;

    public Fruit(int id, String fruit, double price) {
        this.id = id;
        this.fruit = fruit;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getFruit() {
        return fruit;
    }

    public double getPrice() {
        return price;
    }
}

public class test {
    public static void main(String[] args) {
        Fruit[] fruits = new Fruit[]{
            new Fruit(1, "Apple", 1.5),
            new Fruit(2, "Banana", 2.75),
            new Fruit(3, "Cherry", 3.0),
            new Fruit(4, "Orange", 3.25),
            new Fruit(5, "Mango", 3.5),
            new Fruit(6, "Pineapple", 3.75),
            new Fruit(7, "Strawberry", 4.0),
            new Fruit(8, "Watermelon", 4.25),
            new Fruit(9, "Peach", 4.5),
            new Fruit(10, "Pear", 4.75),
            new Fruit(11, "Apricot", 5.0),
            new Fruit(12, "Lemon", 5.25)
        };

        List<Fruit> data = Arrays.asList(fruits);

        List<TableColumn<?>> columns = new ArrayList<>();
        columns.add(new TableColumn<>("ID", data, "%4d", "getId"));
        columns.add(new TableColumn<>("Fruit", data, "%10s", "getFruit"));
        columns.add(new TableColumn<>("Price", data, "%5.2f", "getPrice"));
        Table TEST = new Table(columns);

        TEST.printFull("Sample Table");
    }
}
     */



}

