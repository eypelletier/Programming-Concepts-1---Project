//Package Class
public class Package {
    private String label;
    private double height;
    private double width;
    private double length;
    private double weight;
    private GoodsCategory goodsClassification;

    public Package() {
        this.label = "[N/A]";
        this.height = 0.0;
        this.width = 0.0;
        this.length = 0.0;
        this.weight = 0.0;
        this.goodsClassification = null;
    }

    public Package setHeight(double height){ 
        this.height = height; 
        return this;
    }
    
    public Package setWidth(double width){ 
        this.width = width; 
        return this;
    }
    
    public Package setLength(double length){
        this.length = length;
        return this;
    }

    public Package setWeight(double weight){
        this.weight = weight;
        return this;
    }

    public Package setLabel(String label){
        this.label = label;
    }

    public Package setGoodsClassification(GoodsCategory category){
        this.goodsClassification = category;
        return this;
    }

    public double getHeight(){ return this.height; }
    public double getWidth(){ return this.width; }
    public double getLength(){ return this.length; }
    public double getWeight(){ return this.weight; }
    public String getLabel(){ return this.label; }

    public GoodsCategory getGoodsClassification(){
        return new GoodsCategory(this.goodsClassification);
    }

    //Return the value of the largest dimension of the package
    public double getLargestDimension(){
        double largestDimension = 0.0;

        if (this.height > largestDimension) largestDimension = this.height;
        if (this.width > largestDimension) largestDimension = this.width;
        if (this.length > largestDimension) largestDimension = this.length;

        return largestDimension;
    }

}
