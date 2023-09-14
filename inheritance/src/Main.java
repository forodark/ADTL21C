import glenlib.*;

public class Main {
    static Employee[] employees = { //Manager inherits the attributes of Employee
        new Manager("Glen Bautista", "M-0001", 75000.0, 3),
        new Employee("Anakin Skywalker", "E-0001", 50000.0),
        new Employee("Luke Skywalker", "E-0002", 48000.0),
        new Employee("Leia Organa", "E-0003", 45000.0),
        new Manager("Obi-Wan Kenobi", "M-0002", 70000.0, 2),
        new Employee("Han Solo", "E-0004", 40000.0),
        new Employee("Chewbacca", "E-0005", 35000.0)
    };

    public static void main(String[] args) {
        mainMenu();
        Util.exit();
    }

    public static void mainMenu() {
        MenuItem[] main_menu = {
            new Option("All Employees", () -> viewAllEmployees()),
            new Option("Manager Employees Only", () -> viewManagers()),
            new Option("Non-Manager Employees Only", () -> viewNonManagers()),
        };
        Menu.showMenu("Choose a table to view", main_menu);
    }

    public static void viewAllEmployees() {
        new Tbl<Employee>()
        .Array(employees)
        .Col("Name", "%20s", "getName")
        .Col("ID", "%8s", "getId")
        .Col("Salary", "%14.2f", "getSalary")
        .Col("Employees Managed", "%20d", "getEmployeeCount")
        .Title("All Employees")
        .build();

    }

    public static void viewManagers() {
        new Tbl<Manager>()
        .Array(Manager.getManagers(employees))
        .Col("Name", "%20s", "getName")
        .Col("ID", "%8s", "getId")
        .Col("Salary", "%14.2f", "getSalary")
        .Col("Employees Managed", "%20d", "getEmployeeCount")
        .Title("Manager Employees Only")
        .build();
    }

    public static void viewNonManagers() {
        new Tbl<Employee>()
        .Array(Employee.getNonManagers(employees))
        .Col("Name", "%20s", "getName")
        .Col("ID", "%8s", "getId")
        .Col("Salary", "%14.2f", "getSalary")
        .Title("Non-Manager Employees Only")
        .build();
    }
}
