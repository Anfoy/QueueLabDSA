package supermarketProgram;

import java.util.LinkedList;
import java.util.Queue;

public class CheckoutLane {
    protected Queue<Customer> queue = new LinkedList<>();
    protected int totalWaitTime = 0;
    protected int processedCustomers = 0;
    protected int processedItems = 0;
    protected int freeTime = 0;
    protected int maxLength = 0;
    protected boolean isFree = true;

    // Time per item
    private static final int PROCESSING_TIME_PER_ITEM = 5;

    /**
     * Adds a customer to the queue and updates the line length.
     * @param customer Customer object to add to queue.
     */
    public void addCustomer(Customer customer) {
        queue.add(customer);
        maxLength = Math.max(maxLength, queue.size());
    }

    /**
     * Processes the first customer in the queue and updates lane metrics
     * @param currentTime time of program.
     */
    public void processQueue(int currentTime) {
        if (!queue.isEmpty()) {
            Customer currentCustomer = queue.peek();
            if (isFree) {
                int waitTime = currentTime - currentCustomer.getArrivalTime();
                currentCustomer.setWaitTime(waitTime);
                totalWaitTime += waitTime;
                isFree = false;
            }

            // Decrement the remaining processing time
            currentCustomer.decrementProcessingTime();

            if (currentCustomer.isProcessed()) {
                queue.poll();
                processedCustomers++;
                processedItems += currentCustomer.getItemCount();
                isFree = true;
            }
        } else {
            freeTime++;  // Increment free time if no customers in queue
        }
    }

    // Metrics for display
    public int getProcessedCustomers() {
        return processedCustomers;
    }

    public int getProcessedItems() {
        return processedItems;
    }

    public int getFreeTime() {
        return freeTime;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public double getAverageWaitTime() {
        return processedCustomers > 0 ? (double) totalWaitTime / processedCustomers : 0;
    }

    public double getCustomersPerHour(int maxSimTime) {
        return processedCustomers / (maxSimTime / 3600.0);
    }

    public double getItemsPerHour(int maxSimTime) {
        return processedItems / (maxSimTime / 3600.0);
    }

    public int getQueueLength() {
        return queue.size();
    }

    public int getTotalWaitTime() {
        return totalWaitTime;
    }
}