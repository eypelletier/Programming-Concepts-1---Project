import java.util.ArrayList;

public class DeliveryModality {
    private String name;
    private double maxAllowableDimension;
    private double maxWeight;
    private ArrayList<GoodsCategory> goodsRestrictions;

    public static final DeliveryModality TRUCK = CreateModality("truck");
    public static final DeliveryModality RAIL = CreateModality("rail");
    public static final DeliveryModality SEA = CreateModality("sea");
    public static final DeliveryModality AIR = CreateModality("air");

    private static DeliveryModality CreateModality(String type){
        DeliveryModality newModality = null;
        switch (type){
            case "truck":
                newModality = new DeliveryModality("truck",400,200);
                newModality.goodsRestrictions.add(GoodsCategory.HAZARDOUS);
                break;
            case "rail":
                newModality = new DeliveryModality("rail",800,1000);
                newModality.goodsRestrictions.add(GoodsCategory.EXPLOSIVE);
                break;
            case "sea":
                newModality = new DeliveryModality("sea",800,2000);
                break;
            case "air":
                newModality = new DeliveryModality("air",200,100);
                newModality.goodsRestrictions.add(GoodsCategory.HAZARDOUS);
                newModality.goodsRestrictions.add(GoodsCategory.EXPLOSIVE);
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
        this.goodsRestrictions = new ArrayList<>();
    }

    public String getName(){ return this.name; }
    public double getMaxAllowableDimension() { return maxAllowableDimension; }
    public double getMaxWeight() { return maxWeight; }

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
}
