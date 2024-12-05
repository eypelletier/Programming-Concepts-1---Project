import java.util.ArrayList;

/** Represents a GoodsCategory, the type of goods an item contains or may contain
 *
 * @param identifier, an integer identifier for a category of goods
 * @param name, a string representing the name for the category
 * @param surchargeRate, a double representing any applicable surcharge
 * @param strId, a string representing an informative unique short string code for the category
 */
public record GoodsCategory(int identifier, String name, double surchargeRate, String strId) {
    private final static ArrayList<GoodsCategory> instances = new ArrayList<GoodsCategory>();

    public final static GoodsCategory REGULAR = new GoodsCategory(1, "Regular", 0, "REG");
    public final static GoodsCategory FRAGILE = new GoodsCategory(2, "Fragile", 10, "FRG");
    public final static GoodsCategory HAZARDOUS = new GoodsCategory(3, "Hazardous", 100, "HAZ");
    public final static GoodsCategory EXPLOSIVE = new GoodsCategory(4, "Explosive", 1000, "EXP");

    /** constructor
     *
     * @param identifier, an integer representing the identifier for the category
     * @param name, a string representing the name for the category
     * @param surchargeRate, a double representing any applicable surcharge
     * @param strId, a string representing an information short unique string code for the category
     */
    public GoodsCategory(int identifier, String name, double surchargeRate, String strId) {
        this.identifier = identifier;
        this.name = name;
        this.surchargeRate = surchargeRate;
        this.strId = strId;

        //Add to instances of GoodsCategory
        instances.add(this);
    }

    /** retrieves and existing instance of a goods category if it exists
     *
     * @param strId, a string representing the short unique code for the category
     * @return a GoodsCategory instance if it exists, or null if it does not
     */
    public static GoodsCategory getCategoryByStrId(String strId) {
        for (GoodsCategory category : instances) {
            if (category.strId.equals(strId)) return category;
        }
        return null;
    }

    /** check to see if a category exists with the specified strId
     *
     * @param strId, a string representing a short string identifier for the category
     * @return a boolean value of true if a category exists, and false otherwise
     */
    public static boolean hasCategoryWithStrId(String strId) {
        return (getCategoryByStrId(strId) != null);
    }

    /** toString convenience method
     *
     * @return a string representing the name for the category
     */
    @Override
    public String toString() {
        return this.name;
    }
}