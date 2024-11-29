import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Shipment {
    private List<Package> packages; // Assuming Package is another class that you define
    private String trackingNumber;
    private String origin;
    private String destination;
    private DeliveryStandard shipmentDeliveryMethod;

    // Constructor
    public Shipment(List<Package> packages, String origin, String destination, DeliveryStandard shipmentDeliveryMethod) {
        this.packages = packages;
        this.origin = origin;
        this.destination = destination;
        this.shipmentDeliveryMethod = shipmentDeliveryMethod;
        this.trackingNumber = generateTrackingNumber();
    }

    // Generate the tracking number based on current date and origin/destination codes
    private String generateTrackingNumber() {
        // Get the current date in yyyyMMdd format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datePart = sdf.format(new Date());

        // Number for origin and destination
        int originCode = getLocationCode(origin);
        int destinationCode = getLocationCode(destination);

        // Random number to append to end of tracking number
        Random rand = new Random();
        int numRand = rand.nextInt(100);

        return "TRK" + datePart + originCode + destinationCode + numRand;
    }

    // Get location code for origin/destination
    private int getLocationCode(String location) {
        switch (location.toLowerCase()) {
            case "montreal":
                return 11;
            case "toronto":
                return 12;
            case "vancouver":
                return 13;
            default:
                throw new IllegalArgumentException("Invalid location");
        }
    }

    // Getters and setters
    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public DeliveryStandard getShipmentDeliveryMethod() {
        return shipmentDeliveryMethod;
    }

    public void setShipmentDeliveryMethod(DeliveryStandard shipmentDeliveryMethod) {
        this.shipmentDeliveryMethod = shipmentDeliveryMethod;
    }
}
