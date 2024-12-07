import java.util.*;

/** Shipment class
 *
 */
public class Shipment {
    private String name, shipmentTrackingNumber;
    private City origin;
    private City destination;
    private DeliveryModality shippingMethod;
    private DeliveryStandard shippingSpeed;
    private final ArrayList<Package> shipmentPackages;

    /** enum of problems that a specific package may encounter as part of the shipment
     *
     */
    public enum PackageProblems {
        MISMATCH_MODALITY,
        MISMATCH_STANDARD,
        TOO_HEAVY_FOR_MODALITY,
        TOO_HEAVY_FOR_STANDARD,
        TOO_LARGE_FOR_MODALITY,
        TOO_LARGE_FOR_STANDARD,
        GOODS_RESTRICTED_FOR_MODALITY,
    }

    /** constructor
     *
     */
    public Shipment() {
        this.name = null;
        this.origin = null;
        this.destination = null;
        this.shippingMethod = null;
        this.shippingSpeed = null;
        shipmentPackages = new ArrayList<>();
        this.shipmentTrackingNumber = null;
    }

    /** gets the name of the shipment
     *
     * @return a string representing the name of the shipment
     */
    public String getName() {
        return name;
    }

    /** gets the origin of the shipment
     *
     * @return a City representing the origin for the shipment
     */
    public City getOrigin() {
        return origin;
    }

    /** gets the destination of the shipment
     *
     * @return a City representing the destination for the shipment
     */
    public City getDestination() {
        return destination;
    }

    /** get the unique pair code for destination & origin cities
     *
     * @return an integer representing the unique pair code for the destination and origin cities of the shipment
     */
    public int getCityPairCode(){
        return origin.getPairCode(destination);
    }

    /** gets the delivery modality (or method) for the shipment
     *
     * @return a DeliveryModality representing the shipment method
     */
    public DeliveryModality getShippingMethod() {
        return shippingMethod;
    }

    /** gets the delivery standard for the shipment
     *
     * @return a DeliveryStandard representing the delivery standard for the shipment
     */
    public DeliveryStandard getShippingSpeed() {
        return shippingSpeed;
    }

    /** gets the list of packages that form the shipment
     *
     * @return an ArrayList<Package> containing all packages of the shipment
     */
    public ArrayList<Package> getPackages() {
        return shipmentPackages;
    }

    /** gets a specific package according to its index in the package list
     *  throws an error if provided package index is out of bounds
     *
     * @param packageNum, an integer representing the position of the package in the list
     * @return a Package, representing the given package if found in the less
     */
    public Package getPackageByIndex(int packageNum){
        int packageCount = shipmentPackages.size();
        if ( packageNum < 0 || packageNum >= packageCount) throw new IndexOutOfBoundsException("Package number out of bounds: " + packageNum);

        return shipmentPackages.get(packageNum);
    }

    /** gets the tracking number for the shipment
     *
     * @return a String representing the tracking number for the shipment
     */
    public String getShipmentTrackingNumber() {
        return shipmentTrackingNumber;
    }

    /** sets the name for the shipment
     *
     * @param Name, a String representing the name for the shipment
     */
    public void setName(String Name) {
        this.name = Name;
    }

    /** sets the origin city for the shipment
     *
     * @param origin, a City representing the origin city of the shipment
     */
    public void setOrigin(City origin) {
        this.origin = origin;
    }

    /** sets the destination city for the shipment
     *
     * @param destination, a City representing the destination city for the shipment
     */
    public void setDestination(City destination) {
        this.destination = destination;
    }

    /** sets the shipping method
     *
     * @param shippingMethod, a DeliveryModality representing the shipment method to use
     */
    public void setShippingMethod(DeliveryModality shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    /** set the shipping speed (or standard) for the shipment
     *
     * @param shippingSpeed, a DeliveryStandard representing the standard to use for the shipment
     */
    public void setShippingSpeed(DeliveryStandard shippingSpeed) {
        this.shippingSpeed = shippingSpeed;
    }

    /** set the shipping tracking number
     *
     * @param trackingNumber, a String representing the tracking number for the shipment
     */
    public void setShipmentTrackingNumber(String trackingNumber) {
        this.shipmentTrackingNumber = trackingNumber;
    }

    /** adds a package to the shipment
     *
     * @param pkg, a Package representing the package to add to the shipment
     */
    public void addToPackages(Package pkg) {
        shipmentPackages.add(pkg);
    }


    /** display (print to screen) the packages for the shipment
     *
     */
    public void displayPackages() {
        for (Package pack : shipmentPackages) {
            System.out.println(pack);
        }
    }

    /** calculates the total shipping cost
     *
      * @return a double representing the total shipping cost
     */
    public double calculateTotalShippingCost() {
        double baseRate = calculateBaseRate();
        double surcharge = calculateSurcharge();
        return baseRate + surcharge;
    }

    /** calculate the base rate depending on the city pair for destination and origin
     *
     * @return a double representing the base rate for the shipment
     */
    private double calculateBaseRate() {
        int montrealTorontoPairCode = City.MONTREAL.getPairCode(City.TORONTO);
        int montrealVancouverPairCode = City.MONTREAL.getPairCode(City.VANCOUVER);
        int torontoVancouverPairCode = City.TORONTO.getPairCode(City.VANCOUVER);

        if (getCityPairCode() == montrealTorontoPairCode) {
            return 10.0;
        } else if (getCityPairCode() == torontoVancouverPairCode) {
            return 50.0;
        } else if (getCityPairCode() == montrealVancouverPairCode) {
            return 60.0;
        }

        //The shipment is within the city
        return 5.0;
    }

    /** calculate the total surcharge for the shipment
     *
     * @return a double representing the total surcharge for the shipment
     */
    private double calculateSurcharge() {
        double surcharge = 0;
        for (Package pkg : shipmentPackages) {
            if (pkg.getLargestDimension() > shippingMethod.getMaxAllowableDimension() ) {
                surcharge += 10.0;
            }
        }

        surcharge += shippingSpeed.getSurcharge();
        surcharge += shippingMethod.getSurcharge();
        surcharge += getWeightSurcharge();

        return surcharge;
    }

    /** gets packages rejected during condition/validation checks according to constraints
     * of DeliveryStandard and/or DeliveryModality
     *
     * @return a Map<Package, EnumSet<PackageProblems>> representing a mapping of packages to problems related
     * to the package
     */
    public Map<Package, EnumSet<PackageProblems>> getRejectedPackages(){
        Map<Package, EnumSet<PackageProblems>> packagesRejected = new HashMap<>();
        int packageCount = shipmentPackages.size();
        for (int packageIndex = 0; packageIndex < packageCount; packageIndex++) {
            Package pkg = shipmentPackages.get(packageIndex);
            EnumSet<PackageProblems> problems = EnumSet.noneOf(PackageProblems.class);

            //Check for problems related to DeliveryStandard
            if (!shippingSpeed.meetsDimensions(pkg.getLength(), pkg.getWidth(), pkg.getHeight())) {
                problems.add(PackageProblems.MISMATCH_STANDARD);
                problems.add(PackageProblems.TOO_LARGE_FOR_STANDARD);
            }

            if (!shippingSpeed.meetsWeight(pkg.getWeight())) {
                problems.add(PackageProblems.MISMATCH_STANDARD);
                problems.add(PackageProblems.TOO_HEAVY_FOR_STANDARD);
            }

            //Check for problems related to DeliveryModality
            if (pkg.getLargestDimension() > shippingMethod.getMaxAllowableDimension()) {
                problems.add(PackageProblems.MISMATCH_MODALITY);
                problems.add(PackageProblems.TOO_LARGE_FOR_MODALITY);
            }

            if (pkg.getWeight() > shippingMethod.getMaxWeight()){
                problems.add(PackageProblems.MISMATCH_MODALITY);
                problems.add(PackageProblems.TOO_HEAVY_FOR_MODALITY);
            }

            if (!shippingMethod.canTransportGoodsCategory(pkg.getGoodsClassification())){
                problems.add(PackageProblems.MISMATCH_MODALITY);
                problems.add(PackageProblems.GOODS_RESTRICTED_FOR_MODALITY);
            }

            if (!problems.isEmpty()){
                packagesRejected.put(pkg, problems);
            }
        }

        return packagesRejected;
    }

    /** gets the total weight for the shipment
     *
     * @return a double representing the total weight for the shipment
     */
    public double getTotalWeight(){
        double totalWeight = 0.0;
        for (Package pkg : shipmentPackages) {
            totalWeight += pkg.getWeight();
        }
        return totalWeight;
    }

    /** gets the total surcharge related to exceeding maximum weight of standard or method of shipment
     *
     * @return a double representing the total weight surcharge in effect
     */
    public double getWeightSurcharge(){
        double totalWeight = getTotalWeight();
        double totalSurchage = 0.0;
        if ( totalWeight > shippingMethod.getMaxWeight() ) totalSurchage+=10;
        if ( totalWeight > shippingSpeed.getMaxWeight() ) totalSurchage+=50;
        return totalSurchage;
    }

    /** gets surcharge related to shipping speed/standard
     *
     * @return a double representing the surcharge related to shipping speed/standard
     */
    public double getPrioritySurcharge(){
        return shippingSpeed.getSurcharge();
    }

    /** gets surcharge related to shipping method
     *
     * @return a double representing the surcharge related to shipping method
     */
    public double getModalitySurcharge(){
        return shippingMethod.getSurcharge();
    }

    /** gets the base rate for the shipment
     *
     * @return a double representing the base rate for the shipment
     */
    public double getBaseRate() {
        return calculateBaseRate();
    }

}
