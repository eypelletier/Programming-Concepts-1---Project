public enum DeliveryStandard {
    STANDARD(1,"Standard",100, 200, 150, 1000,0),
    EXPRESS(2,"Express",50, 150, 100, 500,50);

    private final int identifier;
    private final String type;
    private final double maxHeight;
    private final double maxLength;
    private final double maxWidth;
    private final double maxWeight;
    private final double surcharge;

    DeliveryStandard(int identifier, String type, double maxHeight, double maxLength, double maxWidth, double maxWeight, double surcharge){
        this.identifier = identifier;
        this.type = type;
        this.maxHeight = maxHeight;
        this.maxLength = maxLength;
        this.maxWidth = maxWidth;
        this.maxWeight = maxWeight;
        this.surcharge = surcharge;
    }

    public String getType(){ return type; }
    public int getIdentifier(){ return identifier; }
    public double getMaxHeight() { return maxHeight; }
    public double getMaxLength() { return maxLength; }
    public double getMaxWidth() { return maxWidth; }
    public double getMaxWeight() { return maxWeight; }
    public double getSurcharge() { return surcharge; }

    public boolean meetsDimensions(double length, double width, double height){
        return (length <= this.maxLength) && (width <= this.maxWidth) && (height <= this.maxHeight);
    }

    public boolean meetsWeight(double weight){
        return (weight <= this.maxWeight);
    }

    @Override
    public String toString(){
        return this.type;
    }
}