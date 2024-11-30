import java.util.ArrayList;

public class GoodsCategory {
    private String name;
    private double surchargeRate;

    //Pre-configured categories
    public static final GoodsCategory REGULAR = new GoodsCategory("regular",0);
    public static final GoodsCategory FRAGILE = new GoodsCategory("fragile",10);
    public static final GoodsCategory HAZARDOUS = new GoodsCategory("hazardous",100);
    public static final GoodsCategory EXPLOSIVE = new GoodsCategory("explosive",1000);

    public GoodsCategory(GoodsCategory anotherObject) {
       this.name = anotherObject.getName();
       this.surchargeRate = anotherObject.getSurchargeRate();
    }

    public GoodsCategory(String name, double surchargeRate) {
        this.name = name;
        this.surchargeRate = surchargeRate;
    }

    public GoodsCategory setSurchargeRate(double rate){this.surchargeRate = rate; return this;}
    public GoodsCategory setName(String name){this.name = name; return this; }

    public double getSurchargeRate(){return this.surchargeRate;}
    public String getName() {return this.name;}
}
