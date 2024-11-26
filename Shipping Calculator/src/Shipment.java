import java.util.Scanner;
import java.time.*;
import java.util.Random;

public class Shipment {

    // Fields
    private String origin;
    private String destination;
    private String trackingNumber;
    private Package[] packages;  // Array to hold the packages

    // Constructor to initialize the shipment
    public Shipment(String origin, String destination, String trackingNumber, Package[] packages) {
        this.origin = origin;
        this.destination = destination;
        this.trackingNumber = trackingNumber;
        this.packages = packages;
    }

    // Getter and setter methods for each field
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

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Package[] getPackages() {
        return packages;
    }

    public void setPackages(Package[] packages) {
        this.packages = packages;
    }
}