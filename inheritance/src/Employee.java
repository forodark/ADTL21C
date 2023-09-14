import java.util.Arrays;

public class Employee {
    private String name;
    private String id;
    private double salary;

    public Employee(String name, String id, double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public double getSalary() {
        return salary;
    }

    public static Employee[] getNonManagers(Employee[] employees) { //method for getting all non-managers only
        Employee[] non_managers = new Employee[0];
        for (Employee employee : employees) {
            if (!(employee instanceof Manager)) {
                non_managers = Arrays.copyOf(non_managers, non_managers.length + 1);
                non_managers[non_managers.length - 1] = employee;
            }
        }
        return non_managers;
    }
}
