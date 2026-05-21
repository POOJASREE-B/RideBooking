package src;

public class Ride {
    private String rideId;
    private String status;
    private String riderId;
    private String driverId;
    private String cityId;
    private double surgeFactor;

    public Ride(String rideId, String riderId, String cityId, double surgeFactor) {
        this.rideId = rideId;
        this.riderId = riderId;
        this.cityId = cityId;
        this.surgeFactor = surgeFactor;
        this.status = "REQUESTED";
    }

    public String getRideStatus() {
        return this.status;
    }

    public boolean isDriverAvailable(Driver driver) {
        return "AVAILABLE".equalsIgnoreCase(driver.getStatus());
    }

    public double findDriverDistance(String riderLocation, String driverLocation) {
        try {
            String[] rLoc = riderLocation.split(",");
            String[] dLoc = driverLocation.split(",");
            double latDiff = Double.parseDouble(rLoc[0]) - Double.parseDouble(dLoc[0]);
            double lonDiff = Double.parseDouble(rLoc[1]) - Double.parseDouble(dLoc[1]);
            return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff) * 100;
        } catch (Exception e) {
            return 5.0;
        }
    }

    public void startRide() {
        this.status = "ONGOING";
    }

    public void endRide() {
        this.status = "COMPLETED";
    }

    public void setDriverId(String driverId) { this.driverId = driverId; }
    public void setStatus(String status) { this.status = status; }
    public String getRideId() { return rideId; }
    public String getDriverId() { return driverId; }
    public String getRiderId() { return riderId; }
    public String getCityId() { return cityId; }
    public double getSurgeFactor() { return surgeFactor; }
}