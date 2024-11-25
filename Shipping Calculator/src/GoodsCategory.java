import java.util.ArrayList;

public class GoodsCategory {
    private String name;
    private double surchargeRate;
    private ArrayList<DeliveryModality> modalityRestrictions; 

    public GoodsCategory setSurchargeRate(double rate){this.surchargeRate = rate; return this;}
    public GoodsCategory setName(String name){this.name = name; return this; }

    public GoodsCategory addRestriction(DeliveryModality[] modalityList){
        //TODO
        return this;
    }

    public double getSurchargeRate(){return this.surchargeRate;}
    public String getName() {return this.name;}
    public ArrayList<DeliveryModality> getModalityRestrictions(){ return this.modalityRestrictions;}
}
