package glenlib;

import java.lang.reflect.Method;
import java.util.List;


class TableColumn<T> {
    private String header;
    private List<T> data;
    private String format;
    private String getterMethod;

    public TableColumn(String header, List<T> data, String format, String getterMethod) {
        this.header = header;
        this.data = data;
        this.format = format;
        this.getterMethod = getterMethod;
    }

    public String getHeader() {
        return header;
    }

    public List<T> getData() {
        return data;
    }

    public String getFormat() {
        return format;
    }

    public String getGetterMethod() {
        return getterMethod;
    }
}

class Table {
    public static final int TABLE_PAGE_LENGTH = 10;

    private List<TableColumn<?>> columns;

    public Table(List<TableColumn<?>> columns) {
        this.columns = columns;
    }

    private Object invokeGetter(List<?> data, int index, String getterMethod) {
        try {
            // Get the class of the object at the specified index
            Class<?> rowClass = data.get(index).getClass();

            // Create a Method object for the getter method based on its name
            Method method = rowClass.getMethod(getterMethod);

            // Invoke the getter method on the object at the specified index
            return method.invoke(data.get(index));
        } catch (Exception e) {
            // Handle any exceptions that may occur during reflection
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }


    public void printFull(String title) {
        int numColumns = columns.size();
        int tableWidth = numColumns + 1;

        // Calculate table width based on column formats
        for (TableColumn<?> column : columns) {
            tableWidth += Str.extractNumber(column.getFormat());
        }
        
        Util.clear();
        // Print title
        Style.line(tableWidth);

        if (!title.isEmpty()) {
            Style.printCentered(tableWidth, title);
            System.out.println();
            Style.line(tableWidth);
        }

        // Print headers
        System.out.print("|");
        for (TableColumn<?> column : columns) {
            int columnWidth = Str.extractNumber(column.getFormat());
            Style.printCentered(columnWidth, column.getHeader());
            
            System.out.print("|");  
        }

        System.out.println();

        // Print content
        for (int i = 0; i < columns.get(0).getData().size(); i++) {
            System.out.print("|");
            for (TableColumn<?> column : columns) {
                List<?> columnData = column.getData();
                Object value = invokeGetter(columnData, i, column.getGetterMethod());
                String buffer = Str.convertString(value);

                System.out.print(" " + Str.formatString(buffer, Str.extractNumber(column.getFormat())-1) + "|");
            }
            System.out.println();
        }
        Style.line(tableWidth);
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

    public void printPage(String title) {
        int numColumns = columns.size();
        int tableWidth = numColumns + 1;

        // Calculate table width based on column formats
        for (TableColumn<?> column : columns) {
            tableWidth += Str.extractNumber(column.getFormat());
        }
        int content_size = columns.get(0).getData().size();

        int page = 0;
        int max_page = (content_size / TABLE_PAGE_LENGTH) - 1;
        int page_row_counter = 0; int table_row_counter = 0;

        if (content_size % TABLE_PAGE_LENGTH != 0) {
            max_page++;
        }
        
        while(true) {
            Util.clear();
            // Print title
            Style.line(tableWidth);

            if (!title.isEmpty()) {
                Style.printCentered(tableWidth, title);
                System.out.println();
                Style.line(tableWidth);
            }

            // Print headers
            System.out.print("|");
            for (TableColumn<?> column : columns) {
                int columnWidth = Str.extractNumber(column.getFormat());
                Style.printCentered(columnWidth, column.getHeader());
                
                System.out.print("|");  
            }

            System.out.println();

            // Print content
            for (; page_row_counter < TABLE_PAGE_LENGTH && table_row_counter < content_size; page_row_counter++, table_row_counter++) {
                System.out.print("|");
                for (TableColumn<?> column : columns) {
                    List<?> columnData = column.getData();
                    Object value = invokeGetter(columnData, table_row_counter, column.getGetterMethod());
                    String buffer = Str.convertString(value);

                    System.out.print(" " + Str.formatString(buffer, Str.extractNumber(column.getFormat())-1) + "|");
                }
                System.out.println();
            }
            page_row_counter = 0;
            Style.line(tableWidth);
            Style.printf(" Page %d of %d   ", page+1, max_page+1);
            if (page > 0) {
                Style.print(" [8] Previous   ");
            }
            if (tableWidth < 31) {
                Style.nl();
            }
            if (page != max_page) {
                Style.print(" [9] Next   ");
            }
            Style.printf(" [0] Return%n");

            Style.line(tableWidth);

            int choice = In.getInt("Enter Choice: ");

            switch (choice) {
                case 8:
                    if (page > 0) {
                        page--;
                        table_row_counter = page*TABLE_PAGE_LENGTH;
                        break;
                    }
                    else {
                        Util.invalid(Util.INVALID, tableWidth);
                        table_row_counter = page*TABLE_PAGE_LENGTH;
                        continue;
                    }
                case 9:
                    if (page != max_page) {
                        page++;
                        table_row_counter = page*TABLE_PAGE_LENGTH;
                        break;
                    }
                    else {
                        Util.invalid(Util.INVALID, tableWidth);
                        table_row_counter = page*TABLE_PAGE_LENGTH;
                        continue;
                    }
                case 0:
                    Style.line(tableWidth);
                    return;
                default:
                    Util.invalid(Util.INVALID, tableWidth);
                    table_row_counter = page*TABLE_PAGE_LENGTH;
                    continue;
                
            }
        }

    }

}

