package glenlib;

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

public class Test {
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

        // new Tbl<Fruit>()
        //     .Array(fruits)
        //     .Col("ID", "%4d", "getId")
        //     .Col("Fruit", "%10s", "getFruit")
        //     .Col("Price", "%5.2f", "getPrice")
        //     .Title("List of Fruits")
        //     .build();

        int test = 100;
        String test2 = "hello";
        double test3 = 200.25;

        Style.println(Str.formatString(test, 10));
        Style.println(Str.formatString(test2, 10));
        Style.println(Str.formatString(test3, 10));


        // List<Fruit> data = Arrays.asList(fruits);

        // List<TableColumn<?>> columns = new ArrayList<>();
        // columns.add(new TableColumn<>("ID", data, "%4d", "getId"));
        // columns.add(new TableColumn<>("Fruit", data, "%10s", "getFruit"));
        // columns.add(new TableColumn<>("Price", data, "%5.2f", "getPrice"));
        // Table TEST = new Table(columns);

        // TEST.printFull("Sample Table");

    }
}