import java.util.ArrayList;

public class DeliveryModality {
    private String name;
    private double maxAllowableDimension;
    private double maxWeight;
    private ArrayList<GoodsCategory> goodsRestrictions;

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
