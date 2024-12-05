import java.util.ArrayList;

/** Represents the modality used for shipment delivery
 * @since 1.0
 */
public class DeliveryModality {
    private String name;
    private double maxAllowableDimension;
    private double maxWeight;
    private double surcharge;
    private ArrayList<GoodsCategory> goodsRestrictions;

    public static final DeliveryModality TRUCK = CreateModality("Truck");
    public static final DeliveryModality RAIL = CreateModality("Rail");
    public static final DeliveryModality SEA = CreateModality("Sea");
    public static final DeliveryModality AIR = CreateModality("Air");

    /**
     * creates a pre-configured DeliveryModality
     * @param type of the preconfigured DeliveryModality to create
     * @return a new DeliveryModality
     */
    private static DeliveryModality CreateModality(String type){
        DeliveryModality newModality = null;
        switch (type.toLowerCase()){
            case "truck":
                newModality = new DeliveryModality("Truck",400,200);
                newModality.goodsRestrictions.add(GoodsCategory.HAZARDOUS);
                newModality.surcharge = 20;
                break;
            case "rail":
                newModality = new DeliveryModality("Rail",800,1000);
                newModality.goodsRestrictions.add(GoodsCategory.EXPLOSIVE);
                newModality.surcharge = 40;
                break;
            case "sea":
                newModality = new DeliveryModality("Sea",800,2000);
                newModality.surcharge = 30;
                break;
            case "air":
                newModality = new DeliveryModality("Air",200,100);
                newModality.goodsRestrictions.add(GoodsCategory.HAZARDOUS);
                newModality.goodsRestrictions.add(GoodsCategory.EXPLOSIVE);
                newModality.surcharge = 100;
                break;
            default:
                throw new IllegalArgumentException("Invalid modality type");
        }

        newModality.name = type;

        return newModality;
    }

    /**
     *  constructor
     * @param name - the human readable name for the DeliveryModality
     * @param maxAllowableDimension - the size of the largest dimension it can accommodate
     * @param maxWeight - the maximum weight for transport via this DeliveryModality
     */
    public DeliveryModality(String name, double maxAllowableDimension, double maxWeight){
        this.name = name;
        this.maxAllowableDimension = maxAllowableDimension;
        this.maxWeight = maxWeight;
        this.surcharge = 0.0;
        this.goodsRestrictions = new ArrayList<>();
    }

    /** Gets the name for the DeliveryModality
     *
     * @return A String representing the name of the DeliveryModality
     */
    public String getName(){ return this.name; }

    /** Gets the maximum allowable dimension
     *
     * @return A double representing the maximum allowable dimension for the DeliveryModality
     */
    public double getMaxAllowableDimension() { return maxAllowableDimension; }

    /** Gets the maximum allowable weight
     *
     * @return A double representing the maximum weight allowed by the DeliveryModality
     */
    public double getMaxWeight() { return maxWeight; }

    /** Gets the related DeliveryModality surcharge
     *
     * @return A double representing a surcharge related to this DeliveryModality
     */
    public double getSurcharge() { return surcharge; }

    /** Set the name for the DeliveryModality
     *
     * @param name a string representing the name
     */
    public void setName(String name){ this.name = name; }

    /** Set the Maximum Allowable Dimension
     *
     * @param dimension, a double representing the largest maximum size for any dimension
     */
    public void setMaxAllowableDimension(double dimension) { this.maxAllowableDimension = dimension; }

    /** Set the maximum allowable weight
     *
     * @param weight, a double representing the largest possible weight
     */
    public void setMaxWeight(double weight) { this.maxWeight = weight; }

    /** Add a GoodsCategory(s) that this DeliveryModality cannot carry
     *
     * @param goodsRestrictionsList, a list of GoodsCategories that cannot be transported via this DeliveryModality
     * @return this DeliveryModality to support expressive method chaining
     */
    public DeliveryModality addGoodsRestriction(ArrayList<GoodsCategory> goodsRestrictionsList){
        this.goodsRestrictions.addAll(goodsRestrictionsList);
        return this;
    }

    /** Check to see if a particular GoodsCategory can be transported
     *
     * @param goodsCategory, the GoodsCategory to check for.
     * @return boolean representing whether or not the specified GoodsCategory can be transported
     */
    public boolean canTransportGoodsCategory(GoodsCategory goodsCategory){
        return !(goodsRestrictions.contains(goodsCategory));
    }

    /** toString override for convenience printing
     *
     * @return a string representing the name of the DeliveryModality
     */
    @Override
    public String toString(){
        return this.name;
    }
}