package models.view_models.orders;

public class OrderOverviewViewModel {
    long totalCanceled;
    long totalPending;
    long totalCompleted;
    long totalReady;
    long totalReturned;
    long totalDelivering;

    public long getTotalCanceled() {
        return totalCanceled;
    }

    public void setTotalCanceled(long totalCanceled) {
        this.totalCanceled = totalCanceled;
    }

    public long getTotalPending() {
        return totalPending;
    }

    public void setTotalPending(long totalPending) {
        this.totalPending = totalPending;
    }

    public long getTotalCompleted() {
        return totalCompleted;
    }

    public void setTotalCompleted(long totalCompleted) {
        this.totalCompleted = totalCompleted;
    }

    public long getTotalReady() {
        return totalReady;
    }

    public void setTotalReady(long totalReady) {
        this.totalReady = totalReady;
    }

    public long getTotalReturned() {
        return totalReturned;
    }

    public void setTotalReturned(long totalReturned) {
        this.totalReturned = totalReturned;
    }

    public long getTotalDelivering() {
        return totalDelivering;
    }

    public void setTotalDelivering(long totalDelivering) {
        this.totalDelivering = totalDelivering;
    }
}
