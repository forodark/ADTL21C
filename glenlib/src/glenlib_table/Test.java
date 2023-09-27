package glenlib_table;

import glenlib.Style;
import glenlib_math.Calc;
import glenlib_objects.Obj;

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

    public void printInfo() {
        System.out.println(id + " " + fruit + " " + price);
    }
}

class Student {
    private String id;
    private int age;

    public Student(String id, int age) {
        this.id = id;
        this.age = age;
    }
    
    public String getId() {
        return id;
    }

    public int getAge() {
        return age;
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

        Student[] students = new Student[]{
            new Student("2020-001", 24),
            new Student("2018-014", 22),
            new Student("2021-003", 22),
            new Student("2021-004", 29),
            new Student("2023-001", 23),
            new Student("2020-013", 25),
            new Student("2019-001", 26),
            new Student("2018-002", 21),
            new Student("2019-002", 22),
            new Student("2019-003", 23),
            new Student("2022-004", 23),
            new Student("2016-005", 23)
        };

        // Student[] filtered = Sort.selection(Sort.exclude(students, s -> s.getAge() <= 22), "getAge"); 

        // Object[] ages = Obj.extractCol(students, "getAge");
        // Object[] mode = Calc.mode(ages);
        // for (int i = 0; i < mode.length; i++)
        // Style.println(mode[i]);

        // Object[] test = Obj.extractCol(fruits, "getId");
        // for (Object o : test) {
        //     System.out.println(o);
        // }

        // Style.println(Sort.compare(fruits[2], fruits[1], "getFruit"));

        // new Tbl<Student>()
        //     .Array(filtered)
        //     .Title("Test")
        //     .auto(Student.class);

        // for(int i = 0; i < 20; i++) Style.println(Calc.rand(0, 10.0));

        // int test = 10;
        // String test2 = "Hello";
        // double test3 = 10.00;

        // Style.println(Str.formatString(test, 10));
        // Style.println(Str.formatString(test2, 10));
        // Style.println(Str.formatString(test3, 10));

        // List<Fruit> data = Arrays.asList(fruits);

        // List<TableColumn<?>> columns = new ArrayList<>();
        // columns.add(new TableColumn<>("ID", data, "%4d", "getId"));
        // columns.add(new TableColumn<>("Fruit", data, "%10s", "getFruit"));
        // columns.add(new TableColumn<>("Price", data, "%5.2f", "getPrice"));
        // Table TEST = new Table(columns);

        // TEST.printFull("Sample Table");

    }
}