public enum DeliveryStandard {
    STANDARD(100, 200, 150, 1000),
    EXPRESS(50, 150, 100, 500);

    private final double maxHeight;
    private final double maxLength;
    private final double maxWidth;
    private final double maxWeight;

    DeliveryStandard(double maxHeight, double maxLength, double maxWidth, double maxWeight) {
        this.maxHeight = maxHeight;
        this.maxLength = maxLength;
        this.maxWidth = maxWidth;
        this.maxWeight = maxWeight;
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
