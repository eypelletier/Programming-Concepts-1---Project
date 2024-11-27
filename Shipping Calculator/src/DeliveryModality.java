public class DeliveryModality {
    private String name;
    private double maxAllowableDimension;
    private double maxWeight;

    public String getName(){ return this.name; }
    public double getMaxAllowableDimension() { return maxAllowableDimension; }
    public double getMaxWeight() { return maxWeight; }

    public void setName(String name){ this.name = name; }
    public void setMaxAllowableDimension(double maxAllowableDimension) { this.maxAllowableDimension = maxAllowableDimension; }
    public void setMaxWeight(double maxWeight) { this.maxWeight = maxWeight; }
}
