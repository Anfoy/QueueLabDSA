package executeprogram;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Department {
    private final String name;
    private final Queue<Executive> executivesQueue;

    public Department(String name) {
        this.name = name;
        this.executivesQueue = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    /**
     * Adds an executive to this department object. it also increases their seniority.
     * @param executive Executive object to add.
     */
    public void addExecutive(Executive executive) {
        executive.setDepartment(name);
        executive.increaseSeniority();
        executivesQueue.add(executive);
    }

    /**
     * removes the desired executive if they exist in the department.
     * @param executiveName name of executive to look for.
     */
    public void removeExecutive(String executiveName) {
        executivesQueue.removeIf(executive -> executive.getName().equals(executiveName));
    }

    public int getDepartmentSize() {
        return executivesQueue.size();
    }

    /**
     * displays each's executive's salary in decreasing order of seniority.
     */
    public void displayPayroll() {
        int departmentSize = getDepartmentSize();

        // Sort executives by seniority before displaying payroll
        List<Executive> sortedExecutives = executivesQueue.stream()
                .sorted((e1, e2) -> Integer.compare(e2.getSeniority(), e1.getSeniority())) // Sort descending by seniority
                .toList();

        System.out.println("Payroll for Department: " + name);
        for (Executive executive : sortedExecutives) {
            executive.calculateSalary(departmentSize);
            System.out.println(executive);
        }
    }

    /**
     * Finds the executive object that is paired with the parameterized name.
     * @param executiveName Name to look for.
     * @return Executive object if found; null otherwise.
     */
    public Executive findExecutive(String executiveName) {
        for (Executive executive : executivesQueue) {
            if (executive.getName().equals(executiveName)) {
                return executive;
            }
        }
        return null;
    }
}