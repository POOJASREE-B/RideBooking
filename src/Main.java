package src;

import java.util.*;

public class Main {
    private static Map<String, Rider> riders = new HashMap<>();
    private static Map<String, Driver> drivers = new HashMap<>();
    private static Map<String, City> cities = new HashMap<>();
    private static Map<String, Ride> rides = new HashMap<>();
    private static List<Fare> globalFares = new ArrayList<>();

    public static void main(String[] args) {
        cities.put("OMR", new City("OMR", "OMR Tech Corridor", 18.0));
        cities.put("TNG", new City("TNG", "T-Nagar Central", 22.0));

        riders.put("R1", new Rider("R1", "Karthik", "karthik2026", "13.0418,80.2341", 4.8));
        riders.put("R2", new Rider("R2", "Priya", "priya@tn", "12.9010,80.2279", 4.9));

        Driver d1 = new Driver("D1", "Murugan", "murugan99", "13.0430,80.2350", 4.7, "TNG");
        d1.setStatus("AVAILABLE");
        drivers.put("D1", d1);

        Driver d2 = new Driver("D2", "Thangaraj", "thanga@77", "12.9025,80.2285", 4.6, "OMR");
        d2.setStatus("AVAILABLE");
        drivers.put("D2", d2);

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== CHENNAI RIDE BOOKING & SURGE SYSTEM CLI ===");

        while (true) {
            System.out.println("\n1. Register Rider\n2. Register Driver\n3. Match & Request Ride (Surge Engine)\n4. Progress Ride Lifecycle\n5. Generate System Revenue Reports\n6. Exit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid numeric option.");
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Rider ID: "); String rId = scanner.nextLine();
                    System.out.print("Enter Name: "); String rName = scanner.nextLine();
                    System.out.print("Enter Password: "); String rPass = scanner.nextLine();
                    System.out.print("Enter Location (lat,long): "); String rLoc = scanner.nextLine();

                    riders.put(rId, new Rider(rId, rName, rPass, rLoc, 5.0));
                    System.out.println("Rider Registered Successfully!");
                    break;

                case 2:
                    System.out.print("Enter Driver ID: "); String dId = scanner.nextLine();
                    System.out.print("Enter Name: "); String dName = scanner.nextLine();
                    System.out.print("Enter Password: "); String dPass = scanner.nextLine();
                    System.out.print("Enter Location (lat,long): "); String dLoc = scanner.nextLine();
                    System.out.print("Enter Zone ID (OMR/TNG): "); String cId = scanner.nextLine();

                    Driver driver = new Driver(dId, dName, dPass, dLoc, 5.0, cId);
                    driver.setStatus("AVAILABLE");
                    drivers.put(dId, driver);
                    System.out.println("Driver Registered and Status set to AVAILABLE!");
                    break;

                case 3:
                    System.out.print("Enter Rider ID requesting ride (e.g., R1, R2): ");
                    String reqRiderId = scanner.nextLine();
                    Rider currentRider = riders.get(reqRiderId);
                    if (currentRider == null) {
                        System.out.println("Rider not found.");
                        break;
                    }

                    System.out.print("Enter Target Zone ID (OMR/TNG): ");
                    String targetCityId = scanner.nextLine();
                    City currentCity = cities.get(targetCityId);
                    if (currentCity == null) {
                        System.out.println("Operational zone not found.");
                        break;
                    }

                    long activeDemands = rides.values().stream()
                            .filter(r -> r.getCityId().equals(targetCityId) && "REQUESTED".equals(r.getRideStatus()))
                            .count() + 1;

                    long activeSupply = drivers.values().stream()
                            .filter(d -> d.getCityId().equals(targetCityId) && "AVAILABLE".equals(d.getStatus()))
                            .count();

                    double dynamicSurgeFactor = 1.0;
                    if (activeSupply == 0 || (double) activeDemands / activeSupply > 1.0) {
                        dynamicSurgeFactor = 1.5;
                        System.out.println("HIGH DEMAND IN CHENNAI: Surge Multiplier Applied (1.5x)!");
                    }

                    String assignedRideId = "RIDE_" + (rides.size() + 1);
                    Ride currentRide = new Ride(assignedRideId, reqRiderId, targetCityId, dynamicSurgeFactor);

                    Driver closestDriver = null;
                    double minDistance = Double.MAX_VALUE;

                    for (Driver d : drivers.values()) {
                        if (currentRide.isDriverAvailable(d) && d.getCityId().equals(targetCityId)) {
                            double calculatedDist = currentRide.findDriverDistance(currentRider.getLocation(), d.getLocation());
                            if (calculatedDist < minDistance) {
                                minDistance = calculatedDist;
                                closestDriver = d;
                            }
                        }
                    }

                    if (closestDriver != null) {
                        currentRide.setDriverId(closestDriver.getDriverId());
                        currentRide.setStatus("ACCEPTED");
                        closestDriver.setStatus("ON_TRIP");
                        rides.put(assignedRideId, currentRide);

                        double calculatedFareAmount = (minDistance * currentCity.getFare()) * dynamicSurgeFactor;
                        Fare systemFare = new Fare("FARE_" + (globalFares.size() + 1), closestDriver.getDriverId(), reqRiderId, calculatedFareAmount);
                        globalFares.add(systemFare);

                        System.out.println("MATCH SUCCESSFUL!");
                        System.out.println("Assigned Driver: " + closestDriver.getDriverName() + " (Distance: " + String.format("%.2f", minDistance) + " km)");
                        System.out.println("Calculated Dynamic Bill: ₹" + String.format("%.2f", calculatedFareAmount));
                    } else {
                        System.out.println("Booking Rejected: No available drivers inside this Chennai grid.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Active Ride ID (e.g., RIDE_1): ");
                    String lifecycleRideId = scanner.nextLine();
                    Ride trackingRide = rides.get(lifecycleRideId);
                    if (trackingRide == null) {
                        System.out.println("Ride Record Not Located.");
                        break;
                    }

                    System.out.println("Current State: " + trackingRide.getRideStatus());
                    System.out.println("1. Transition to ONGOING\n2. Transition to COMPLETED");
                    System.out.print("Choose action: ");
                    int stateChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (stateChoice == 1) {
                        trackingRide.startRide();
                        System.out.println("Ride Lifecycle updated to ONGOING.");
                    } else if (stateChoice == 2) {
                        trackingRide.endRide();
                        Driver d = drivers.get(trackingRide.getDriverId());
                        if (d != null) d.setStatus("AVAILABLE");
                        System.out.println("Ride Lifecycle updated to COMPLETED. Driver status reset back to AVAILABLE.");
                    }
                    break;

                case 5:
                    System.out.println("\n=== TAMIL NADU REGIONAL REVENUE REPORT ===");
                    double aggregativeTotalRevenue = 0;
                    for (Fare f : globalFares) {
                        aggregativeTotalRevenue += f.getFare();
                    }
                    System.out.println("Total Dynamic Platform Revenue Booked: ₹" + String.format("%.2f", aggregativeTotalRevenue));
                    System.out.println("Total Trips Managed: " + rides.size());
                    break;

                case 6:
                    System.out.println("Shutting down Chennai System Service Grid...");
                    System.exit(0);

                default:
                    System.out.println("Invalid operation selector.");
            }
        }
    }
}