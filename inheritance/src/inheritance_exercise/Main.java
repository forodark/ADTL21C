package inheritance_exercise;

import glenlib.*;

public class Main {
    public static void main(String[] args) throws Exception {
        
        Employee[] employees = {
            new Manager("Glen", "M-0001", 75000.0, 5),
            new Employee("Happy", "E-0001", 50000.0),
            new Employee("Aki", "E-0002", 48000.0)
        };

        new Tbl<Employee>()
        .Array(employees)
        .Col("Name", "%15s", "getName")
        .Col("ID", "%8s", "getId")
        .Col("Salary", "%10.2f", "getSalary")
        .Col("Employees Managed", "%15d", "getEmployeeCount")
        .Title("Employees and Managers")
        .build();


        // System.out.println("Employees and Managers");
        // System.out.println("-----------------------------------------------------------");
        // System.out.printf("%-10s %-15s %-10s %s%n", "Name", "Employee ID", "Salary", "Employees Managed");
        // System.out.println("-----------------------------------------------------------");

        // for (Employee employee : employees) {
        //     if (employee instanceof Manager) {
        //         Manager manager = (Manager) employee;
        //         System.out.printf("%-10s %-15s %-10.2f %-15d%n",
        //                 employee.getName(), employee.getId(), employee.getSalary(),
        //                 manager.getEmployeeCount());
        //     } else {
        //         System.out.printf("%-10s %-15s %-10.2f %-15s%n",
        //                 employee.getName(), employee.getId(), employee.getSalary(), "");
        //     }
        // }
        // System.out.println("-----------------------------------------------------------");
    }
}
