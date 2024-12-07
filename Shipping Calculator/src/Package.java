/** Package class represents a package object
 *
 */
public class Package {
    private String label;
    private double height;
    private double width;
    private double length;
    private double weight;
    private GoodsCategory goodsClassification;

    /** constructor
     *
     */
    public Package() {
        this.label = "[N/A]";
        this.height = 0.0;
        this.width = 0.0;
        this.length = 0.0;
        this.weight = 0.0;
        this.goodsClassification = null;
    }

    /** Setter for height of package, prevents assignment of negative values
     *
     * @param height, a double representing the height of the package
     * @return this Package, to allow for method chaining
     */
    public Package setHeight(double height){
        if (height < 0.0) throw new RuntimeException("Height of package cannot be negative.");
        this.height = height; 
        return this;
    }

    /** Setter for the width of the package, prevents assignment of negative values
     *
     * @param width, a double representing the width of the package
     * @return this Package, to allow for method chaining
     */
    public Package setWidth(double width){
        if (width < 0.0) throw new RuntimeException("Width of package cannot be negative.");
        this.width = width; 
        return this;
    }

    /** Setter for the length of the package, prevents assignment of negative values
     *
     * @param length, a double representing the length of the Package
     * @return this Package, to allow for method chaining
     */
    public Package setLength(double length){
        if (length < 0.0) throw new RuntimeException("Length of package cannot be negative.");
        this.length = length;
        return this;
    }

    /** Setter for the weight of the package, prevents assignment of negative values
     *
     * @param weight, a double representing the weight of the package
     * @return this Package, to allonw for method chaining
     */
    public Package setWeight(double weight){
        if (weight < 0.0) throw new RuntimeException("Weight of package cannot be negative.");
        this.weight = weight;
        return this;
    }

    /** Setter for the label of the package
     *
     * @param label, a string representing the label (or name) of the package
     * @return this Package, to allow for method chaining
     */
    public Package setLabel(String label){
        this.label = label;
        return this;
    }

    /** Setter for the goods category of the package
     *
     * @param category, a GoodsCategory for packages
     * @return this Package, to allow for method chaining
     */
    public Package setGoodsClassification(GoodsCategory category){
        this.goodsClassification = category;
        return this;
    }

    /** Getter for package length
     *
     * @return a double representing the length of the package
     */
    public double getLength(){ return this.length; }

    /** Getter for the package width
     *
     * @return a double representing the width of the package
     */
    public double getWidth(){ return this.width; }

    /** Getter for the height of the package
     *
     * @return a double representing the height of the package
     */
    public double getHeight(){ return this.height; }

    /** Getter for the weight of the package
     *
     * @return a double representing the weight of the package
     */
    public double getWeight(){ return this.weight; }

    /** Getter for the label of the package
     *
     * @return a string representing the label of the package
     */
    public String getLabel(){ return this.label; }

    /** Getter for the Goods Category of the package
     *
     * @return a GoodsCategory
     */
    public GoodsCategory getGoodsClassification(){
        return this.goodsClassification;
    }

    /** Gets the largest dimension of the package
     *
     * @return a double representing the largest of all the dimensions of the package
     */
    public double getLargestDimension(){
        double largestDimension = this.height;
        if (this.width > largestDimension) largestDimension = this.width;
        if (this.length > largestDimension) largestDimension = this.length;

        return largestDimension;
    }

    /** Checks to see if the package meets the provided dimensions (fits them)
     *
     * @param length, a double representing the length to check
     * @param width, a double representing the width to check
     * @param height, a double representing the height to check
     * @return a boolean representing if the package meets all provided dimensions
     */
    public boolean meetsDimensions(double length, double width, double height){
        return (this.length <= length) && (this.width <= width) && (this.height <= height);
    }

    /** Checks to see if the package meets the provided weight (does not exceed that weight)
     *
     * @param weight, a double representing the weight to check
     * @return a boolean representing if the package meets the weight
     */
    public boolean meetsWeight(double weight){
        return (this.weight <= weight);
    }
}
