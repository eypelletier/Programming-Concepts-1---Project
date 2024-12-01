public enum City {
    MONTREAL(1,1,"Montreal","MTL"),
    TORONTO(2,2, "Toronto","TOR"),
    VANCOUVER(3,4, "Vancouver","VAN");

    private final int identifier;
    private final int code;
    private final String name;
    private final String trackingCode;

    City(int identifier, int cityCode, String cityName, String trackingCode) {
        this.identifier = identifier;
        this.code = cityCode;
        this.name = cityName;
        this.trackingCode = trackingCode;
    }

    public String getTrackingCode(){ return this.trackingCode; }
    public int getIdentifier() { return identifier; }
    public String getName() { return name; }

    public int getPairCode(City cityR) {
        return (this.code | cityR.code);
    }

    @Override
    public String toString(){
        return this.name;
    }
}