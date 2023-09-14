import java.util.Arrays;

public class Manager extends Employee {
    private int employee_count;

    public Manager(String name, String id, double salary, int employee_count) {
        super(name, id, salary);
        this.employee_count = employee_count;
    }

    public int getEmployeeCount() {
        return employee_count;
    }

    public static Manager[] getManagers(Employee[] employees) { //method for getting all managers only
        Manager[] managers = new Manager[0];
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                managers = Arrays.copyOf(managers, managers.length + 1);
                managers[managers.length - 1] = manager;
            }
        }
        return managers;
    }
}
