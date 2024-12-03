import java.util.ArrayList;

public record GoodsCategory(int identifier, String name, double surchargeRate, String strId) {
    private final static ArrayList<GoodsCategory> instances = new ArrayList<GoodsCategory>();

    public final static GoodsCategory REGULAR = new GoodsCategory(1, "Regular", 0, "REG");
    public final static GoodsCategory FRAGILE = new GoodsCategory(2, "Fragile", 10, "FRG");
    public final static GoodsCategory HAZARDOUS = new GoodsCategory(3, "Hazardous", 100, "HAZ");
    public final static GoodsCategory EXPLOSIVE = new GoodsCategory(4, "Explosive", 1000, "EXP");

    public GoodsCategory(int identifier, String name, double surchargeRate, String strId) {
        this.identifier = identifier;
        this.name = name;
        this.surchargeRate = surchargeRate;
        this.strId = strId;

        //Add to instances of GoodsCategory
        instances.add(this);
    }

    public static GoodsCategory getCategoryByStrId(String strId) {
        for (GoodsCategory category : instances) {
            if (category.strId.equals(strId)) return category;
        }
        return null;
    }

    public static boolean hasCategoryWithStrId(String strId) {
        return (getCategoryByStrId(strId) != null);
    }

    @Override
    public String toString() {
        return this.name;
    }
}