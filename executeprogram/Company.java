package executeprogram;

import java.util.*;

public class Company {
    private final Map<String, Department> departments;
    private final Queue<Executive> unemployedQueue;

    public Company() {
        this.departments = new HashMap<>();
        this.unemployedQueue = new LinkedList<>();
    }

    /**
     * Adds a new department to the departments Map.
     * @param departmentName Name of department to map department object too.
     */
    public void addDepartment(String departmentName) {
        departments.putIfAbsent(departmentName, new Department(departmentName));
    }

    /**
     * Creates a new Executive object and puts it in the queue.
     * @param name Name of executive
     */
    public void hireExecutive(String name) {
        unemployedQueue.add(new Executive(name));
    }

    /**
     * Finds the desired executive based on the parameterized name, and then finds the department based on the department name.
     * @param executiveName Name to check for
     * @param departmentName department to check for.
     */
    public void assignToDepartment(String executiveName, String departmentName) {
        Executive executive = findExecutive(executiveName);
        if (executive != null) {
            unemployedQueue.remove(executive);
            Department department = departments.get(departmentName);
            if (department != null) {
                department.addExecutive(executive);
            }
        }
    }

    /**
     * Removes the executive from their respective department.
     * @param executiveName Name of executive to look for.
     */
    public void removeFromDepartment(String executiveName) {
        for (Department department : departments.values()) {
            if (department.findExecutive(executiveName) != null) {
                department.removeExecutive(executiveName);
                unemployedQueue.add(new Executive(executiveName));
                break;
            }
        }
    }

    public void changeDepartment(String executiveName, String newDepartmentName) {
        removeFromDepartment(executiveName);
        assignToDepartment(executiveName, newDepartmentName);
    }

    /**
     * prints the payroll for the department.
     */
    public void displayPayroll() {
        for (Department department : departments.values()) {
            department.displayPayroll();
        }
    }

    /**
     * Gets salary of executive based on parameterized name.
     * @param executiveName Name to check for.
     * @return Salary of desired executive
     */
    public int getSalary(String executiveName) {
        for (Department department : departments.values()) {
            Executive executive = department.findExecutive(executiveName);
            if (executive != null) {
                return executive.calculateSalary(department.getDepartmentSize());
            }
        }
        return 0; //Unemployed
    }

    private Executive findExecutive(String name) {
        for (Executive exec : unemployedQueue) {
            if (exec.getName().equals(name)) return exec;
        }
        return null;
    }


    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Enter command:");
            String command = scanner.nextLine().trim();
            String[] parts = command.split(" ");

            if (parts.length == 0) continue;

            switch (parts[0].toLowerCase()) {
                case "add":
                    if (parts.length < 2) {
                        System.out.println("Error: Please provide a department name for 'add' command.");
                    } else {
                        addDepartment(parts[1]);
                        System.out.println("Department '" + parts[1] + "' added.");
                    }
                    break;
                case "hire":
                    if (parts.length < 2) {
                        System.out.println("Error: Please provide a name for 'hire' command.");
                    } else {
                        hireExecutive(parts[1]);
                        System.out.println("Executive '" + parts[1] + "' hired.");
                    }
                    break;
                case "join":
                    if (parts.length < 3) {
                        System.out.println("Error: Please provide an executive name and department for 'join' command.");
                    } else {
                        assignToDepartment(parts[1], parts[2]);
                        System.out.println("Executive '" + parts[1] + "' joined department '" + parts[2] + "'.");
                    }
                    break;
                case "quit":
                    if (parts.length < 2) {
                        System.out.println("Error: Please provide an executive name for 'quit' command.");
                    } else {
                        removeFromDepartment(parts[1]);
                        System.out.println("Executive '" + parts[1] + "' removed from their department.");
                    }
                    break;
                case "change":
                    if (parts.length < 3) {
                        System.out.println("Error: Please provide an executive name and new department for 'change' command.");
                    } else {
                        changeDepartment(parts[1], parts[2]);
                        System.out.println("Executive '" + parts[1] + "' moved to department '" + parts[2] + "'.");
                    }
                    break;
                case "payroll":
                    displayPayroll();
                    break;
                case "salary":
                    if (parts.length < 2) {
                        System.out.println("Error: Please provide an executive name for 'salary' command.");
                    } else {
                        int salary = getSalary(parts[1]);
                        System.out.println(parts[1] + "'s Salary: $" + salary);
                    }
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
        scanner.close();
    }
}