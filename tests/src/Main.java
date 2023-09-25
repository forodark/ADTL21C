import glenlib_table.Tbl;
import glenlib.Style;

public class Main {

    public static Student[] sort(Student[] students) {
        Student[] sorted;



        int index = 0;
        for(Student student : students) {
            Style.println(students[index].getName());
            Student lowest = student;
            index++;
        }


        return students;
    }

    public static Student compareAlpha(Student a, Student b) {
        
    }




    static Student[] students = {
        new Student("Glen", 20),
        new Student("Angelo", 21)
    };



    public static void main(String[] args) {
        //object sorter test
        sort(students);

    }
}
