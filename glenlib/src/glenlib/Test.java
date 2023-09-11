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
            new TableRow(3, "Cherry", 3.0),
            new TableRow(4, "Orange", 3.25),
            new TableRow(5, "Mango", 3.5),
            new TableRow(6, "Pineapple", 3.75),
            new TableRow(7, "Strawberry", 4.0),
            new TableRow(8, "Watermelon", 4.25),
            new TableRow(9, "Peach", 4.5),
            new TableRow(10, "Pear", 4.75),
            new TableRow(11, "Apricot", 5.0),
            new TableRow(12, "Lemon", 5.25)
        };

        List<TableColumn> columns = new ArrayList<>();
        columns.add(new TableColumn("ID", data, "%4d", "getId"));
        columns.add(new TableColumn("Fruit", data, "%10s", "getFruit"));
        columns.add(new TableColumn("Price", data, "%5.2f", "getPrice"));
        Table TEST = new Table(columns);

        TEST.printPage("Sample Table");
    }



}
