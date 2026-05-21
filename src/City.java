package src;

public class City {
    private String cityId;
    private String cityName;
    private double farePerKm;

    public City(String cityId, String cityName, double farePerKm) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.farePerKm = farePerKm;
    }

    public double getFare() {
        return farePerKm;
    }

    public String getCityId() { return cityId; }
    public String getCityName() { return cityName; }
}