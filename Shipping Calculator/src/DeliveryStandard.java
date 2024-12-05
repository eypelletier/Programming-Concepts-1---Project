/** Represents the standard for delivery
 *
 */
public enum DeliveryStandard {
    //Populate two existing standards
    STANDARD(1,"Standard",100, 200, 150, 1000,0),
    EXPRESS(2,"Express",50, 150, 100, 500,50);

    private final int identifier;
    private final String type;
    private final double maxHeight;
    private final double maxLength;
    private final double maxWidth;
    private final double maxWeight;
    private final double surcharge;

    /** Constructor for the enum
     *
     * @param identifier, an int representing unique identifier
     * @param type, a string representing the type of standard
     * @param maxHeight, a double representing the maximum allowed height
     * @param maxLength, a double representing the maximum allowed length
     * @param maxWidth, a double representing the maximum allowed width
     * @param maxWeight, a double representing the maximum allowed weight
     * @param surcharge, a double representing a related surcharge for the standard
     */
    DeliveryStandard(int identifier, String type, double maxHeight, double maxLength, double maxWidth, double maxWeight, double surcharge){
        this.identifier = identifier;
        this.type = type;
        this.maxHeight = maxHeight;
        this.maxLength = maxLength;
        this.maxWidth = maxWidth;
        this.maxWeight = maxWeight;
        this.surcharge = surcharge;
    }

    /** get the type
     *
     * @return a string representing the type
     */
    public String getType(){ return type; }

    /** get the identifier
     *
     * @return an integer representing the identifier
     */
    public int getIdentifier(){ return identifier; }

    /** get the maximum length
     *
     * @return a double representing the maximum length
     */
    public double getMaxLength() { return maxLength; }

    /** get the maximum width
     *
     * @return a double representing the maximum width
     */
    public double getMaxWidth() { return maxWidth; }

    /** get the maximum height
     *
     * @return a double representing the maximum height
     */
    public double getMaxHeight() { return maxHeight; }

    /** get the maximum height
     *
     * @return a double representing the maximum weight
     */
    public double getMaxWeight() { return maxWeight; }

    /** get the surcharge
     *
     * @return a double representing the surcharge
     */
    public double getSurcharge() { return surcharge; }

    /** check to see if provided dimensions are allowable
     *
     * @param length, a double representing the length of an item
     * @param width, a double representing the width of an item
     * @param height, a double representing the height of an item
     * @return a boolean representing whether the given dimensions are allowable
     */
    public boolean meetsDimensions(double length, double width, double height){
        return (length <= this.maxLength) && (width <= this.maxWidth) && (height <= this.maxHeight);
    }

    /** check to see if the provided weight is allowable
     *
     * @param weight, a double representing the weight of an item
     * @return a boolean representing whether the given weight is allowable
     */
    public boolean meetsWeight(double weight){
        return (weight <= this.maxWeight);
    }

    /** toString convenience override
     *
     * @return a string representing the type of the DeliveryStandard
     */
    @Override
    public String toString(){
        return this.type;
    }
}