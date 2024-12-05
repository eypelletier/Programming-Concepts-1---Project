/** Represents a city.
 * @version 1.0
 */
public enum City {
    MONTREAL(1,0b001,"Montreal","MTL"),
    TORONTO(2,0b010, "Toronto","TOR"),
    VANCOUVER(3,0b100, "Vancouver","VAN");

    private final int identifier;
    private final int code;
    private final String name;
    private final String trackingCode;

    /**
     * Enum constructor specifying identifier code for the city,
     * the cityCode (a unique bit vector/mask), the cityName as a string,
     * and an informative string trackingCode for the city
     */
    City(int identifier, int cityCode, String cityName, String trackingCode) {
        this.identifier = identifier;
        this.code = cityCode;
        this.name = cityName;
        this.trackingCode = trackingCode;
    }

    /**
     * @return the identifier for the enum
     */
    public int getIdentifier() { return this.identifier; }

    /**
     * @return the trackingCode for the enum
     */
    public String getTrackingCode(){ return this.trackingCode; }

    /**
     * @return the name for the enum
     */
    public String getName() { return this.name; }

    /**
     * This method returns a unique int for the current city with the provided city
     * @param cityR is the city to form the pair
     * @return an integer representing the unique bit vector for the city pair
     */
    public int getPairCode(City cityR) {
        return (this.code | cityR.code);
    }

    public int getCityCode(){ return this.code; }

    /**
     * toString method implementation returning just the name of the city for convenience.
     */
    @Override
    public String toString(){
        return this.name;
    }
}