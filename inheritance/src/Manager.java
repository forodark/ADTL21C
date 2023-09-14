public class Manager extends Employee {
    private int employee_count;

    public Manager(String name, String id, double salary, int employee_count) {
        super(name, id, salary);
        this.employee_count = employee_count;
    }

    public int getEmployeeCount() {
        return employee_count;
    }
}
