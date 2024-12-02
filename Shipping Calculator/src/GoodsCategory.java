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

/*public enum GoodsCategory {
    REGULAR(1,"Regular",0),
    FRAGILE(2,"Fragile",10),
    HAZARDOUS(3,"Hazardous", 100),
    EXPLOSIVE(4,"Explosive", 1000);

    private final String name;
    private final double surchargeRate;
    private final int identifier;

    GoodsCategory(int identifier,String name, double surchargeRate) {
        this.name = name;
        this.surchargeRate = surchargeRate;
        this.identifier = identifier;
    }

    public double getSurchargeRate() {
        return surchargeRate;
    }

    public String getName() {
        return name;
    }

    public int getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return this.name;
    }
}*/