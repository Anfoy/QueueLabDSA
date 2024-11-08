package executeprogram;

import java.util.Objects;

public class Executive {
    private final String name;
    private String department;
    private int seniority;
    private int salary;

    public Executive(String name) {
        this.name = name;
        this.department = null; // Initially unemployed
        this.seniority = 0;
        this.salary = 0;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSeniority() {
        return seniority;
    }

    public void increaseSeniority() {
        this.seniority++;
    }

    public int calculateSalary(int departmentSize) {
        this.salary = 40000 + (5000 * (departmentSize - seniority - 1));
        return this.salary;
    }

    @Override
    public String toString() {
        return name + " - Salary: $" + salary;
    }

}