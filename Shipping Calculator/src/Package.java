import GoodsCategory;
//Package Class
public class Package {
    private double height;
    private double width;
    private double length;
    private double weight;
    private GoodsCategory goodsCategory;

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

    public Package setGoodsCategory(GoodsCategory category){
        this.goodsCategory = category;
        return this;
    }

    public double getHeight(){ return this.height; }
    public double getWidth(){ return this.width; }
    public double getLength(){ return this.length; }
    public double getWeight(){ return this.width; }
    public GoodsCategory getGoodsCategory(){ return this.goodsCategory; }

    //Return the value of the largest dimension of the package
    public double getLargestDimension(){
        double largestDimension = 0.0;

        if (this.height > largestDimension) largestDimension = this.height;
        if (this.width > largestDimension) largestDimension = this.width;
        if (this.length > largestDimension) largestDimension = this.length;

        return largestDimension;
    }

}
