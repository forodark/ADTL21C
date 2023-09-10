package glenlib;

import java.util.ArrayList;
import java.util.List;

class TableRow {
    private int id;
    private String fruit;
    private double price;

    public TableRow(int id, String fruit, double price) {
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

public class Test {
    public static void main(String[] args) {
        // Create an array of data using the TableRow class
        TableRow[] data = new TableRow[]{
            new TableRow(1, "Apple", 1.5),
            new TableRow(2, "Banana", 2.75),
            new TableRow(3, "Cherry", 3.0)
        };

        List<TableColumn> columns = new ArrayList<>();
        columns.add(new TableColumn("ID", data, "%4d", "getId"));
        columns.add(new TableColumn("Fruit", data, "%10s", "getFruit"));
        columns.add(new TableColumn("Price", data, "%5.2f", "getPrice"));
        Table TEST = new Table(columns);

        TEST.printFull("Sample Table");
    }



}
