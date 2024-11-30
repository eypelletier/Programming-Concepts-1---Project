import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Shipment {
    private String origin;
    private String destination;
    private DeliveryModality shippingMethod;
    private DeliveryStandard shippingSpeed;
    private List<Package> packages;

    // Constants for origin and destination location codes
    public static final String MONTREAL = "Montreal";
    public static final String TORONTO = "Toronto";
    public static final String VANCOUVER = "Vancouver";

    // Maps for location codes to numbers
    private static final int MONTREAL_CODE = 21;
    private static final int TORONTO_CODE = 22;
    private static final int VANCOUVER_CODE = 23;

    public Shipment(String origin, String destination, DeliveryModality shippingMethod, DeliveryStandard shippingSpeed, List<Package> packages) {
        this.origin = origin;
        this.destination = destination;
        this.shippingMethod = shippingMethod;
        this.shippingSpeed = shippingSpeed;
        this.packages = packages;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public DeliveryModality getShippingMethod() {
        return shippingMethod;
    }

    public DeliveryStandard getShippingSpeed() {
        return shippingSpeed;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setShippingMethod(DeliveryModality shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public void setShippingSpeed(DeliveryStandard shippingSpeed) {
        this.shippingSpeed = shippingSpeed;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    // Method to generate tracking number
    public String generateTrackingNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(new Date()); // Get current date in yyyyMMdd format

        Random rand = new Random();

        // Get origin and destination location codes
        int originCode = getLocationCode(origin);
        int destinationCode = getLocationCode(destination);

        // Generate the tracking number using the current date and the location codes
        return "TRK" + date + originCode + destinationCode + rand.nextInt(100);
    }

    // Method to get codes for origin and destination
    private int getLocationCode(String location) {
        switch (location) {
            case MONTREAL:
                return MONTREAL_CODE;
            case TORONTO:
                return TORONTO_CODE;
            case VANCOUVER:
                return VANCOUVER_CODE;
            default:
                throw new IllegalArgumentException("Invalid location: " + location);
        }
    }

    // Method to calculate shipping cost
    public double calculateShippingCost() {
        double baseRate = calculateBaseRate();
        double surcharge = calculateSurcharge();
        return baseRate + surcharge;
    }

    // Method to calculate the base rate of the shipment
    private double calculateBaseRate() {
        if (origin.equals("Montreal") && destination.equals("Toronto") || origin.equals("Toronto") && destination.equals("Montreal")) {
            return 10.0;
        } else if (origin.equals("Montreal") && destination.equals("Vancouver") || origin.equals("Vancouver") && destination.equals("Montreal")) {
            return 60.0;
        }
        return 45.0;
    }

    // Method to calculate surcharge
    private double calculateSurcharge() {
        double surcharge = 0;
        for (Package pkg : packages) {
            if (pkg.getHeight() > shippingMethod.getMaxAllowableDimension() || pkg.getLength() > shippingMethod.getMaxAllowableDimension() ||
                    pkg.getWidth() > shippingMethod.getMaxAllowableDimension() || pkg.getWeight() > shippingMethod.getMaxWeight()) {
                surcharge += 10.0;
            }
        }

        surcharge += shippingSpeed.getSurcharge();

        return surcharge;
    }
}
