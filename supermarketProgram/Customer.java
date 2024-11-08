package supermarketProgram;

public class Customer {
    private int itemCount;
    private int arrivalTime;
    private int waitTime;
    private int remainingProcessingTime;

    private static final int PROCESSING_TIME_PER_ITEM = 5; // e.g., 5 seconds per item

    public Customer(int itemCount, int arrivalTime) {
        this.itemCount = itemCount;
        this.arrivalTime = arrivalTime;
        this.waitTime = 0;
        this.remainingProcessingTime = itemCount * PROCESSING_TIME_PER_ITEM; // Set initial processing time
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getRemainingProcessingTime() {
        return remainingProcessingTime;
    }

    public void decrementProcessingTime() {
        if (remainingProcessingTime > 0) {
            remainingProcessingTime--;
        }
    }

    public boolean isProcessed() {
        return remainingProcessingTime <= 0;
    }
}