package glenlib;

import java.lang.reflect.Method;
import java.util.List;

class TableColumn {
    private String header;
    private TableRow[] data;
    private String format;
    private String getterMethod;

    public TableColumn(String header, TableRow[] data, String format, String getterMethod) {
        this.header = header;
        this.data = data;
        this.format = format;
        this.getterMethod = getterMethod;
    }

    public String getHeader() {
        return header;
    }

    public TableRow[] getData() {
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
    private List<TableColumn> columns;

    public Table(List<TableColumn> columns) {
        this.columns = columns;
    }

    private Object invokeGetter(TableRow row, String getterMethod) {
    try {
        // Create a Class object for the TableRow class
        Class<?> rowClass = row.getClass();

        // Create a Method object for the getter method based on its name
        Method method = rowClass.getMethod(getterMethod);

        // Invoke the getter method on the given TableRow object
        return method.invoke(row);
    } catch (Exception e) {
        // Handle any exceptions that may occur during reflection
        e.printStackTrace();
        return null; // Return null in case of an error
    }

    public void printFull(String title) {
        int numColumns = columns.size();
        int tableWidth = numColumns + 1;

        // Calculate table width based on column formats
        for (TableColumn column : columns) {
            tableWidth += Str.extractNumber(column.getFormat());
        }

        // Print title
        Style.line(tableWidth);

        if (!title.isEmpty()) {
            Style.printCentered(tableWidth, title);
            System.out.println();
            Style.line(tableWidth);
        }

        // Print headers
        System.out.print("|");
        for (TableColumn column : columns) {
            int columnWidth = Str.extractNumber(column.getFormat());
            Style.printCentered(columnWidth, column.getHeader());
            
            System.out.print("|");  
        }

        System.out.println();

        // Print content
        for (int i = 0; i < columns.get(0).getData().length; i++) {
            System.out.print("|");
            for (TableColumn column : columns) {
                TableRow[] columnData = column.getData();
                Object value = invokeGetter(columnData[i], column.getGetterMethod());
                String buffer = Str.convertString(value);

                System.out.print(" " + Str.formatString(buffer, Str.extractNumber(column.getFormat())-1) + "|");
            }
            System.out.println();
        }
        Style.line(tableWidth);
    }

    /* Sample:
        List<TableColumn> columns = new ArrayList<>();
        columns.add(new TableColumn("ID", data, "%4d", "getId"));
        columns.add(new TableColumn("Fruit", data, "%10s", "getFruit"));
        columns.add(new TableColumn("Price", data, "%5.2f", "getPrice"));
        Table TEST = new Table(columns);

        TEST.printFull("Sample Table");
     */


}

}

