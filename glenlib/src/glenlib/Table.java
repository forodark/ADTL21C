package glenlib;

import java.lang.reflect.Method;
import java.util.List;

public class Table {
    public static final int TABLE_PAGE_LENGTH = 10;

    private List<TableColumn<?>> columns;

    public Table(List<TableColumn<?>> columns) {
        this.columns = columns;
    }

    private Object invokeGetter(List<?> data, int index, String getterMethod) {
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
            Style.printCentered(column_width, column.getHeader());
            
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

    public int page;
    public int max_page;
    int page_row_counter;
    int table_row_counter;
    public int table_width;
    String saved_title;

    public int getTable_width() {
        return table_width;
    }

    public void printPage(String title) {
        printPage(title, false, false);
    }

    public void printPage(String title, Boolean no_menu) {
        printPage(title, no_menu, false);
    }

    public void printPage(String title, Boolean no_menu, Boolean dont_reset) {
        saved_title = title;
        int num_columns = columns.size();
        table_width = num_columns + 1;

        for (TableColumn<?> column : columns) {
            table_width += Str.extractNumber(column.getFormat());
        }
        int content_size = columns.get(0).getData().size();

        if (dont_reset == false) {
        page = 0;
        max_page = (content_size / TABLE_PAGE_LENGTH) - 1;
        page_row_counter = 0; table_row_counter = 0;  
        }


        if (content_size % TABLE_PAGE_LENGTH != 0) {
            max_page++;
        }
        
        while(true) {
            Util.clear();
            // Print title
            Style.line(table_width);

            if (!title.isEmpty()) {
                Style.printCentered(table_width, title);
                System.out.println();
                Style.line(table_width);
            }

            // Print headers
            System.out.print("|");
            for (TableColumn<?> column : columns) {
                int column_width = Str.extractNumber(column.getFormat());
                Style.printCentered(column_width, column.getHeader());
                
                System.out.print("|");  
            }

            System.out.println();

            // Print content
            for (; page_row_counter < TABLE_PAGE_LENGTH && table_row_counter < content_size; page_row_counter++, table_row_counter++) {
                System.out.print("|");
                for (TableColumn<?> column : columns) {
                    List<?> column_data = column.getData();
                    Object value = invokeGetter(column_data, table_row_counter, column.getGetterMethod());

                    System.out.print(" " + Str.formatString(value, Str.extractNumber(column.getFormat())-1, Str.extractDecimal(column.getFormat())) + "|");
                }
                System.out.println();
            }
            page_row_counter = 0;
            Style.line(table_width);

            if (no_menu == false) {

                Style.printf(" Page %d of %d   ", page+1, max_page+1);
                if (page > 0) {
                    Style.print(" [8] Previous   ");
                }
                if (table_width < 31) {
                    Style.nl();
                }
                if (page != max_page) {
                    Style.print(" [9] Next   ");
                }
                Style.printf(" [0] Return%n");

                Style.line(table_width);

                int choice = In.getInt("Enter Choice: ");

                switch (choice) {
                    case 8:
                        if (page > 0) {
                            page--;
                            table_row_counter = page*TABLE_PAGE_LENGTH;
                            break;
                        }
                        else {
                            Util.invalid(Util.INVALID, table_width);
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
                            Util.invalid(Util.INVALID, table_width);
                            table_row_counter = page*TABLE_PAGE_LENGTH;
                            continue;
                        }
                    case 0:
                        Style.line(table_width);
                        return;
                    default:
                        Util.invalid(Util.INVALID, table_width);
                        table_row_counter = page*TABLE_PAGE_LENGTH;
                        continue;
                    
                }
            }
            else {
                break;
            }
        }
    }

    public void nextPage() {
        if (page != max_page) {
            page++;
            table_row_counter = page*TABLE_PAGE_LENGTH;
            printPage(saved_title, true, true);
        }
        else {
            Util.invalid("Page does not exist", table_width);
            table_row_counter = page*TABLE_PAGE_LENGTH;
        }
        Menu.dontWait();
    }

    public void prevPage() {
        if (page > 0) {
            page--;
            table_row_counter = page*TABLE_PAGE_LENGTH;
            printPage(saved_title, true, true);
        }
        else {
            Util.invalid("Page does not exist", table_width);
            table_row_counter = page*TABLE_PAGE_LENGTH;
        }
        Menu.dontWait();
    }

}

