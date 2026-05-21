package src;

public class Driver extends User {
    private String driverId;
    private String driverName;
    private String status;
    private String cityId;

    public Driver(String driverId, String driverName, String password, String location, double ratings, String cityId) {
        super(driverId, driverName, password, location, ratings);
        this.driverId = driverId;
        this.driverName = driverName;
        this.status = "OFFLINE";
        this.cityId = cityId;
    }

    public void acceptRide(Ride ride) {
        this.status = "ON_TRIP";
        ride.startRide();
    }

    public void cancelRide(Ride ride) {
        this.status = "AVAILABLE";
        ride.endRide();
    }

    public void giveRatings(User user, double rating) {
        user.setRatings((user.getRatings() + rating) / 2);
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriverId() { return driverId; }
    public String getDriverName() { return driverName; }
    public String getCityId() { return cityId; }
}