package src;

public class Rider extends User {
    private String riderId;
    private String riderName;

    public Rider(String riderId, String riderName, String password, String location, double ratings) {
        super(riderId, riderName, password, location, ratings);
        this.riderId = riderId;
        this.riderName = riderName;
    }

    public void getRideDetails(Ride ride) {
        System.out.println("Ride ID: " + ride.getRideId() + " | Status: " + ride.getRideStatus());
    }

    public void bookRide(Ride ride) {
        System.out.println("Ride booked successfully by " + riderName);
    }

    public void cancelRide(Ride ride) {
        if ("ONGOING".equalsIgnoreCase(ride.getRideStatus())) {
            System.out.println("Penalty applied! Cannot cancel an ongoing trip without charges.");
        } else {
            System.out.println("Ride cancelled successfully.");
        }
    }

    public void giveRatings(User user, double rating) {
        user.setRatings((user.getRatings() + rating) / 2);
    }

    public void giveTips(Fare fare, double tipAmount) {
        fare.addFare(tipAmount);
        System.out.println("Tip of $" + tipAmount + " added to total fare.");
    }

    public String getRiderId() { return riderId; }
    public String getRiderName() { return riderName; }
}