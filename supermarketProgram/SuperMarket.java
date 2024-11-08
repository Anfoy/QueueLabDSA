package supermarketProgram;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SuperMarket {
    private SuperExpressLane superExpressLane;
    private List<ExpressLane> expressLanes;
    private List<StandardLane> standardLanes;
    private int maxSimTime;
    private int arrivalRate;
    private int numSuper;
    private int numExp;
    private int maxItems;
    private Random random = new Random();

    public SuperMarket(int maxSimTime, int arrivalRate, int numSuper, int numExp, int numStandLines, int maxItems) {
        this.maxSimTime = maxSimTime;
        this.arrivalRate = arrivalRate;
        this.numSuper = numSuper;
        this.numExp = numExp;
        this.maxItems = maxItems;

        // Initialize lanes
        superExpressLane = new SuperExpressLane();
        expressLanes = new ArrayList<>();
        expressLanes.add(new ExpressLane());
        expressLanes.add(new ExpressLane());

        standardLanes = new ArrayList<>();
        for (int i = 0; i < numStandLines; i++) {
            standardLanes.add(new StandardLane());
        }
    }

    public void runSimulation() {
        for (int time = 0; time < maxSimTime; time++) {
            // Generate a customer if conditions are met
            if (shouldGenerateCustomer()) {
                Customer customer = new Customer(randomItems(), time);
                assignCustomerToLane(customer);
            }

            //  Process each lane's queue
            processAllLanes(time);
        }

        // Display statistics after the simulation ends
        displayStatistics();
    }

    private boolean shouldGenerateCustomer() {
        return random.nextDouble() < (arrivalRate / 3600.0);  // Assuming maxSimTime in seconds
    }

    private int randomItems() {
        return random.nextInt(maxItems) + 1; // Generates a number between 1 and maxItems (inclusive)
    }

    private void assignCustomerToLane(Customer customer) {
        int items = customer.getItemCount();
        if (items <= numSuper) {
            superExpressLane.addCustomer(customer);
        } else if (items <= numExp) {
            getShortestExpressLane().addCustomer(customer);
        } else {
            getShortestStandardLane().addCustomer(customer);
        }
    }

    private ExpressLane getShortestExpressLane() {
        return expressLanes.stream().min(Comparator.comparingInt(CheckoutLane::getQueueLength)).orElse(expressLanes.getFirst());
    }

    private StandardLane getShortestStandardLane() {
        return standardLanes.stream().min(Comparator.comparingInt(CheckoutLane::getQueueLength)).orElse(standardLanes.getFirst());
    }

    private void processAllLanes(int currentTime) {
        superExpressLane.processQueue(currentTime);
        expressLanes.forEach(lane -> lane.processQueue(currentTime));
        standardLanes.forEach(lane -> lane.processQueue(currentTime));
    }

    private int getOverallProcessedCustomers() {
        int total = superExpressLane.getProcessedCustomers();
        for (ExpressLane lane : expressLanes) total += lane.getProcessedCustomers();
        for (StandardLane lane : standardLanes) total += lane.getProcessedCustomers();
        return total;
    }

    private int getOverallProcessedItems() {
        int total = superExpressLane.getProcessedItems();
        for (ExpressLane lane : expressLanes) total += lane.getProcessedItems();
        for (StandardLane lane : standardLanes) total += lane.getProcessedItems();
        return total;
    }

    private double getOverallAverageWaitTime() {
        int totalCustomers = getOverallProcessedCustomers();
        if (totalCustomers == 0) return 0;
        double totalWaitTime = superExpressLane.getTotalWaitTime();
        for (ExpressLane lane : expressLanes) totalWaitTime += lane.getTotalWaitTime();
        for (StandardLane lane : standardLanes) totalWaitTime += lane.getTotalWaitTime();
        return totalWaitTime / totalCustomers;
    }

    private int getOverallFreeTime() {
        int totalFreeTime = superExpressLane.getFreeTime();
        for (ExpressLane lane : expressLanes) totalFreeTime += lane.getFreeTime();
        for (StandardLane lane : standardLanes) totalFreeTime += lane.getFreeTime();
        return totalFreeTime;
    }

    private void displayStatistics() {
        System.out.println("Super Express Lane:");
        printLaneStats(superExpressLane);

        for (int i = 0; i < expressLanes.size(); i++) {
            System.out.println("Express Lane " + (i + 1) + ":");
            printLaneStats(expressLanes.get(i));
        }

        for (int i = 0; i < standardLanes.size(); i++) {
            System.out.println("Standard Lane " + (i + 1) + ":");
            printLaneStats(standardLanes.get(i));
        }

        // Print overall statistics
        System.out.println("\nOverall Statistics:");
        System.out.printf("  Overall Average Wait Time: %.2f seconds%n", getOverallAverageWaitTime());
        System.out.printf("  Total Customers Processed: %d%n", getOverallProcessedCustomers());
        System.out.printf("  Total Items Processed: %d%n", getOverallProcessedItems());
        System.out.printf("  Overall Free Time: %d seconds%n", getOverallFreeTime());
    }

    private void printLaneStats(CheckoutLane lane) {
        System.out.printf("  Average Wait Time: %.2f seconds%n", lane.getAverageWaitTime());
        System.out.println("  Max Line Length: " + lane.getMaxLength());
        System.out.println("  Total Customers Processed: " + lane.getProcessedCustomers());
        System.out.println("  Total Items Processed: " + lane.getProcessedItems());
        System.out.printf("  Free Time: %d seconds%n", lane.getFreeTime());
        System.out.printf("  Customers per Hour: %.2f%n", lane.getCustomersPerHour(maxSimTime));
        System.out.printf("  Items per Hour: %.2f%n", lane.getItemsPerHour(maxSimTime));
    }
}