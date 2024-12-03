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
        if (height < 0.0) throw new RuntimeException("Height of package cannot be negative.");
        this.height = height; 
        return this;
    }
    
    public Package setWidth(double width){
        if (width < 0.0) throw new RuntimeException("Width of package cannot be negative.");
        this.width = width; 
        return this;
    }
    
    public Package setLength(double length){
        if (length < 0.0) throw new RuntimeException("Length of package cannot be negative.");
        this.length = length;
        return this;
    }

    public Package setWeight(double weight){
        if (weight < 0.0) throw new RuntimeException("Weight of package cannot be negative.");
        this.weight = weight;
        return this;
    }

    public Package setLabel(String label){
        this.label = label;
        return this;
    }

    public Package setGoodsClassification(GoodsCategory category){
        this.goodsClassification = category;
        return this;
    }

    public double getLength(){ return this.length; }
    public double getWidth(){ return this.width; }
    public double getHeight(){ return this.height; }

    public double getWeight(){ return this.weight; }
    public String getLabel(){ return this.label; }

    public GoodsCategory getGoodsClassification(){
        return this.goodsClassification;
    }

    //Return the value of the largest dimension of the package
    public double getLargestDimension(){
        double largestDimension = this.height;
        if (this.width > largestDimension) largestDimension = this.width;
        if (this.length > largestDimension) largestDimension = this.length;

        return largestDimension;
    }

    public boolean meetsDimensions(double length, double width, double height){
        return (this.length <= length) && (this.width <= width) && (this.height <= height);
    }

    public boolean meetsWeight(double weight){
        return (this.weight <= weight);
    }
}
