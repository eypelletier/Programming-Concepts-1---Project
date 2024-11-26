public class DeliveryStandard {
    // Fields
    private String name;
    private String deliveryModality;
    private String deliveryPriority;
    private double surchargeRate;
    private double maxWeight;
    private double maxAllowableDimensions;

    // Constructor
    public DeliveryStandard(String name, String deliveryModality, String deliveryPriority,
                            double surchargeRate, double maxWeight, double maxAllowableDimensions) {
        this.name = name;
        this.deliveryModality = deliveryModality;
        this.deliveryPriority = deliveryPriority;
        this.surchargeRate = surchargeRate;
        this.maxWeight = maxWeight;
        this.maxAllowableDimensions = maxAllowableDimensions;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getDeliveryModality() {
        return deliveryModality;
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

    public double getMaxAllowableDimensions() {
        return maxAllowableDimensions;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setDeliveryModality(String deliveryModality) {
        this.deliveryModality = deliveryModality;
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

    public void setMaxAllowableDimensions(double maxAllowableDimensions) {
        this.maxAllowableDimensions = maxAllowableDimensions;
    }

}