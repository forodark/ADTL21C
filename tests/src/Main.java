import glenlib_table.Tbl;

import glenlib.Style;

public class Main {


    static Student[] students = {
        new Student("3ge", 20),
        new Student("4", 30),
        new Student("7ge", 40),
        new Student("6", 50),
        new Student("1ge", 10)
    };



    public static void main(String[] args) {
        //object sorter test

        // sort(students);

// Style.println(Sort.compareStr(students[0], students[3], "getName"));

        // Style.println(Sort.checkAsc(Sort.selection(students, "getAge"), "getAge"));

        new Tbl<Student>()
        .Array(Sort.selection(students, "getName"))
        .auto(Student.class);

    }
}
