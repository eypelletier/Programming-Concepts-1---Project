import java.util.*;
import java.text.SimpleDateFormat;

public class Shipment {
    private String name, shipmentTrackingNumber;
    private City origin;
    private City destination;
    private DeliveryModality shippingMethod;
    private DeliveryStandard shippingSpeed;
    private final ArrayList<Package> shipmentPackages;

    public enum PackageProblems {
        TOO_HEAVY_FOR_MODALITY,
        TOO_HEAVY_FOR_STANDARD,
        TOO_LARGE_FOR_MODALITY,
        TOO_LARGE_FOR_STANDARD,
        GOODS_RESTRICTED_FOR_MODALITY,
    }

    public Shipment() {
        this.name = null;
        this.origin = null;
        this.destination = null;
        this.shippingMethod = null;
        this.shippingSpeed = null;
        shipmentPackages = new ArrayList<>();
        this.shipmentTrackingNumber = null;
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

    public Package getPackageByIndex(int packageNum){
        int packageCount = shipmentPackages.size();
        if ( packageNum < 0 || packageNum >= packageCount) throw new IndexOutOfBoundsException("Package number out of bounds: " + packageNum);

        return shipmentPackages.get(packageNum);
    }

    public String getShipmentTrackingNumber() {
        return shipmentTrackingNumber;
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

    public void setShipmentTrackingNumber(String trackingNumber) {
        this.shipmentTrackingNumber = trackingNumber;
    }

    public void addToPackages(Package pack) {
        shipmentPackages.add(pack);
    }

    public void displayPackages() {
        for (Package pack : shipmentPackages) {
            System.out.println(pack);
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
            if (pkg.getLargestDimension() > shippingMethod.getMaxAllowableDimension() ) {
                surcharge += 10.0;
            }
        }

        surcharge += shippingSpeed.getSurcharge();

        return surcharge;
    }

    //Method to return any problematic packages according to constraints of DeliveryStandard and/or DeliveryModality
    public Map<Package, EnumSet<PackageProblems>> getRejectedPackages(){
        Map<Package, EnumSet<PackageProblems>> packagesRejected = new HashMap<>();
        int packageCount = shipmentPackages.size();
        for (int packageIndex = 0; packageIndex < packageCount; packageIndex++) {
            Package pkg = shipmentPackages.get(packageIndex);
            EnumSet<PackageProblems> problems = EnumSet.noneOf(PackageProblems.class);

            //Check for problems related to DeliveryStandard
            if (!shippingSpeed.meetsDimensions(pkg.getLength(), pkg.getWidth(), pkg.getHeight())) {
                problems.add(PackageProblems.TOO_LARGE_FOR_STANDARD);
            }

            if (!shippingSpeed.meetsWeight(pkg.getWeight())) {
                problems.add(PackageProblems.TOO_HEAVY_FOR_STANDARD);
            }

            //Check for problems related to DeliveryModality
            if (pkg.getLargestDimension() > shippingMethod.getMaxAllowableDimension()) {
                problems.add(PackageProblems.TOO_LARGE_FOR_MODALITY);
            }

            if (pkg.getWeight() > shippingMethod.getMaxWeight()){
                problems.add(PackageProblems.TOO_HEAVY_FOR_MODALITY);
            }

            if (!shippingMethod.canTransportGoodsCategory(pkg.getGoodsClassification())){
                problems.add(PackageProblems.GOODS_RESTRICTED_FOR_MODALITY);
            }

            if (!problems.isEmpty()){
                packagesRejected.put(pkg, problems);
            }
        }

        return packagesRejected;
    }

    //Method to get the total weight of the shipment
    public double getTotalWeight(){
        double totalWeight = 0.0;
        for (Package pkg : shipmentPackages) {
            totalWeight += pkg.getWeight();
        }
        return totalWeight;
    }

    //Get Weight Surcharge
    public double getWeightSurcharge(){
        double totalWeight = getTotalWeight();
        double totalSurchage = 0.0;
        if ( totalWeight > shippingMethod.getMaxWeight() ) totalSurchage+=10;
        if ( totalWeight > shippingSpeed.getMaxWeight() ) totalSurchage+=50;
        return totalSurchage;
    }
    //Get Surcharge for Priority
    public double getPrioritySurcharge(){
        return shippingSpeed.getSurcharge();
    }

    //Get Surcharge for Method
    public double getModalitySurcharge(){
        return shippingMethod.getSurcharge();
    }

    //Get BaseRate
    public double getBaseRate() {
        return calculateBaseRate();
    }

}
