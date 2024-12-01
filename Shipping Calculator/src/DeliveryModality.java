import java.util.ArrayList;

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

    private static DeliveryModality CreateModality(String type){
        DeliveryModality newModality = null;
        switch (type.toLowerCase()){
            case "truck":
                newModality = new DeliveryModality("Truck",400,200);
                newModality.goodsRestrictions.add(GoodsCategory.HAZARDOUS);
                break;
            case "rail":
                newModality = new DeliveryModality("Rail",800,1000);
                newModality.goodsRestrictions.add(GoodsCategory.EXPLOSIVE);
                break;
            case "sea":
                newModality = new DeliveryModality("Sea",800,2000);
                break;
            case "air":
                newModality = new DeliveryModality("Air",200,100);
                newModality.goodsRestrictions.add(GoodsCategory.HAZARDOUS);
                newModality.goodsRestrictions.add(GoodsCategory.EXPLOSIVE);
                newModality.surcharge = 50;
                break;
            default:
                throw new IllegalArgumentException("Invalid modality type");
        }

        newModality.name = type;

        return newModality;
    }

    public DeliveryModality(String name, double maxAllowableDimension, double maxWeight){
        this.name = name;
        this.maxAllowableDimension = maxAllowableDimension;
        this.maxWeight = maxWeight;
        this.surcharge = 0.0;
        this.goodsRestrictions = new ArrayList<>();
    }

    public String getName(){ return this.name; }
    public double getMaxAllowableDimension() { return maxAllowableDimension; }
    public double getMaxWeight() { return maxWeight; }
    public double getSurcharge() { return surcharge; }

    public void setName(String name){ this.name = name; }
    public void setMaxAllowableDimension(double dimension) { this.maxAllowableDimension = dimension; }
    public void setMaxWeight(double weight) { this.maxWeight = weight; }

    public DeliveryModality addGoodsRestriction(ArrayList<GoodsCategory> goodsRestrictionsList){
        this.goodsRestrictions.addAll(goodsRestrictionsList);
        return this;
    }

    public boolean canTransportGoodsCategory(GoodsCategory goodsCategory){
        return !(goodsRestrictions.contains(goodsCategory));
    }

    public String toString(){
        return this.name;
    }
}
