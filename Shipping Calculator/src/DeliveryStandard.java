public class DeliveryStandard {
    private String name;
    private DeliveryModality transportMethod;
    private String deliveryPriority;
    private double surchargeRate;
    private double maxWeight;
    private double maxAllowableDimension;

    public static final String STANDARD = "Standard";
    public static final String EXPRESS = "Express";

    public DeliveryStandard(String transportMethodName, DeliveryModality transportMethod, String deliveryPriority) {
        this.transportMethod = transportMethod;
        this.deliveryPriority = deliveryPriority;
        this.maxWeight = transportMethod.getMaxWeight();
        this.maxAllowableDimension = transportMethod.getMaxAllowableDimension();

        // Setting surcharge based on delivery priority
        if (STANDARD.equals(deliveryPriority)) {
            this.surchargeRate = 0.1;  // 10% surcharge for Standard
        } else if (EXPRESS.equals(deliveryPriority)) {
            this.surchargeRate = 0.25;  // 25% surcharge for Express
        }

        // Constructing name as a combination of transport method and priority
        this.name = deliveryPriority + " - " + transportMethodName;
    }

    public String getName() {
        return name;
    }

    public DeliveryModality getTransportMethod() {
        return transportMethod;
    }

    public String getDeliveryPriority() {
        return deliveryPriority;
    }

    public double getSurchargeRate() {
        return surchargeRate;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public double getMaxAllowableDimension() {
        return maxAllowableDimension;
    }

    public void setTransportMethod(DeliveryModality transportMethod) {
        this.transportMethod = transportMethod;
    }

    public void setDeliveryPriority(String deliveryPriority) {
        this.deliveryPriority = deliveryPriority;
    }

    public void setSurchargeRate(double surchargeRate) {
        this.surchargeRate = surchargeRate;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setMaxAllowableDimension(double maxAllowableDimension) {
        this.maxAllowableDimension = maxAllowableDimension;
    }
}
