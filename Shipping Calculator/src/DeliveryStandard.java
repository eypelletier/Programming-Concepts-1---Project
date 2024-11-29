//DeliveryStandard class definition file

public class DeliveryStandard {
    private String type;
    private final double maxHeight;
    private final double maxLength;
    private final double maxWidth;
    private final double maxWeight;

    public static DeliveryStandard CreateStandard(String type){
            DeliveryStandard newStandard = null;
            switch (type.toLowerCase()){
                case "standard":
                    newStandard = DeliveryStandard.Standard();
                    break;
                case "express":
                    newStandard = DeliveryStandard.Express();
                    break;
            }

            if (newStandard != null){
                newStandard.setType(type);
            }

            return newStandard;
    }

    private static DeliveryStandard Express() {
         return new DeliveryStandard(50, 150, 100, 500);
    }

    private static DeliveryStandard Standard(){
        return new DeliveryStandard(100, 200, 150, 1000);
    }

    // DeliveryStandard.ExpressStandard();

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
}
