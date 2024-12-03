public record GoodsCategory(int identifier, String name, double surchargeRate) {
    public final static GoodsCategory REGULAR = new GoodsCategory(1, "Regular", 0);
    public final static GoodsCategory FRAGILE = new GoodsCategory(2, "Fragile", 10);
    public final static GoodsCategory HAZARDOUS = new GoodsCategory(3, "Hazardous", 100);
    public final static GoodsCategory EXPLOSIVE = new GoodsCategory(4, "Explosive", 1000);

    @Override
    public String toString() {
        return this.name;
    }
}