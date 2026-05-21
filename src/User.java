package src;

public abstract class User {
    protected String userId;
    protected String username;
    protected String password;
    protected String location; // Represented as "lat,long"
    protected double ratings;

    public User(String userId, String username, String password, String location, double ratings) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.location = location;
        this.ratings = ratings;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public double getRatings() { return ratings; }
    public void setRatings(double ratings) { this.ratings = ratings; }
}