import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Shipment {
    private String name;
    private City origin;
    private City destination;
    private DeliveryModality shippingMethod;
    private DeliveryStandard shippingSpeed;
    private ArrayList<Package> shipmentPackages;

    public Shipment() {
        this.name = null;
        this.origin = null;
        this.destination = null;
        this.shippingMethod = null;
        this.shippingSpeed = null;
        shipmentPackages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public City getOrigin() {
        return origin;
    }

    public City getDestination() {
        return destination;
    }

    public int getCityPairCode(){
        return origin.getPairCode(destination);
    }

    public DeliveryModality getShippingMethod() {
        return shippingMethod;
    }

    public DeliveryStandard getShippingSpeed() {
        return shippingSpeed;
    }

    public ArrayList<Package> getPackages() {
        return shipmentPackages;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public void setOrigin(City origin) {
        this.origin = origin;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public void setShippingMethod(DeliveryModality shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public void setShippingSpeed(DeliveryStandard shippingSpeed) {
        this.shippingSpeed = shippingSpeed;
    }

    public void addToPackages(Package pack) {
        shipmentPackages.add(pack);
    }

    public void displayPackages() {
        for (Package pack : shipmentPackages) {
            System.out.println(pack);
        }
    }

    // Method to generate tracking number
    public String generateTrackingNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(new Date()); // Get current date in yyyyMMdd format

        Random rand = new Random();

        // Get origin and destination location codes
        String originCode = origin.getTrackingCode();
        String destinationCode = destination.getTrackingCode();

        // Generate the tracking number using the current date and the location codes
        return "TRK" + date + originCode + destinationCode + rand.nextInt(100);
    }

    // Method to calculate shipping cost
    public double calculateShippingCost() {
        double baseRate = calculateBaseRate();
        double surcharge = calculateSurcharge();
        return baseRate + surcharge;
    }

    // Method to calculate the base rate of the shipment
    private double calculateBaseRate() {
        int montrealTorontoPairCode = City.MONTREAL.getPairCode(City.TORONTO);
        int montrealVancouverPairCode = City.MONTREAL.getPairCode(City.VANCOUVER);

        if (getCityPairCode() == montrealTorontoPairCode) {
            return 10.0;
        } else if (getCityPairCode() == montrealVancouverPairCode) {
            return 60.0;
        }
        return 45.0;
    }

    // Method to calculate surcharge
    private double calculateSurcharge() {
        double surcharge = 0;
        for (Package pkg : shipmentPackages) {
            if (pkg.getHeight() > shippingMethod.getMaxAllowableDimension() || pkg.getLength() > shippingMethod.getMaxAllowableDimension() ||
                    pkg.getWidth() > shippingMethod.getMaxAllowableDimension() || pkg.getWeight() > shippingMethod.getMaxWeight()) {
                surcharge += 10.0;
            }
        }

        surcharge += shippingSpeed.getSurcharge();

        return surcharge;
    }
}
