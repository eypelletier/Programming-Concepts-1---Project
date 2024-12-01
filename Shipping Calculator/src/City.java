public enum City {
    MONTREAL(1,"Montreal","MTL"),
    TORONTO(2, "Toronto","TOR"),
    VANCOUVER(4, "Vancouver","VAN");

    private final int cityCode;
    private final String cityName;
    private final String cityTrackingCode;

    City(int cityCode, String cityName, String trackingCode) {
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.cityTrackingCode = trackingCode;
    }

    public int getPairCode(City cityR) {
        return (this.cityCode | cityR.cityCode);
    }

    public String getTrackingCode(){
        return this.cityTrackingCode;
    }

    @Override
    public String toString(){
        return this.cityName;
    }
}