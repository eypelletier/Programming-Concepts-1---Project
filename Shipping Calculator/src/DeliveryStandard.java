//DeliveryStandard class definition file

public class DeliveryStandard {
    private String type;
    private final double maxHeight;
    private final double maxLength;
    private final double maxWidth;
    private final double maxWeight;
    private double surcharge;

    public static final DeliveryStandard STANDARD = CreateStandard("Standard");
    public static final DeliveryStandard EXPRESS = CreateStandard("Express");

    private static DeliveryStandard CreateStandard(String type){
            DeliveryStandard newStandard = null;
            switch (type.toLowerCase()){
                case "standard":
                    newStandard = new DeliveryStandard(100, 200, 150, 1000);
                    newStandard.surcharge = 0;
                    break;
                case "express":
                    newStandard = new DeliveryStandard(50, 150, 100, 500);
                    newStandard.surcharge = 50;
                    break;
            }

            if (newStandard != null){
                newStandard.type = type;
            }

            return newStandard;
    }

    DeliveryStandard(double maxHeight, double maxLength, double maxWidth, double maxWeight) {
        this.maxHeight = maxHeight;
        this.maxLength = maxLength;
        this.maxWidth = maxWidth;
        this.maxWeight = maxWeight;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public double getMaxLength() {
        return maxLength;
    }

    public double getMaxWidth() {
        return maxWidth;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public double getSurcharge() {
        return surcharge;
    }

    public String toString(){
        return this.type;
    }

}
