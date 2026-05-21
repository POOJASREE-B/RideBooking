package src;

public class Fare {
    private String fareId;
    private String driverId;
    private String riderId;
    private double totalFare;
    private String status;

    public Fare(String fareId, String driverId, String riderId, double baseFare) {
        this.fareId = fareId;
        this.driverId = driverId;
        this.riderId = riderId;
        this.totalFare = baseFare;
        this.status = "PENDING";
    }

    public double getFare() {
        return totalFare;
    }

    public void addFare(double amount) {
        this.totalFare += amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() { return status; }
}