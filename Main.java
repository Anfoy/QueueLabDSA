import executeprogram.Company;
import supermarketProgram.SuperMarket;

public class Main {
    public static void main(String[] args) {
testMarket();
    }

    private static void testMarket(){
        // Initialize simulation parameters
        int maxSimTime = 3600; // 1 hour in seconds
        int arrivalRate = 1000;  // Customers per hour
        int numSuper = 5;      // Max items for Super Express
        int numExp = 20;       // Max items for Express
        int numStandLines = 3; // Number of Standard lanes
        int maxItems = 50;     // Max items per customer

        // Create and run the simulation
        SuperMarket superMarket = new SuperMarket(maxSimTime, arrivalRate, numSuper, numExp, numStandLines, maxItems);
        superMarket.runSimulation();
    }

    private static void testDepartment(){
        Company company = new Company();
        company.run();
    }
}