import java.util.ArrayList;

public class GoodsCategory {
    private String name;
    private double surchargeRate;

    public GoodsCategory setSurchargeRate(double rate){this.surchargeRate = rate; return this;}
    public GoodsCategory setName(String name){this.name = name; return this; }

    public double getSurchargeRate(){return this.surchargeRate;}
    public String getName() {return this.name;}
}
