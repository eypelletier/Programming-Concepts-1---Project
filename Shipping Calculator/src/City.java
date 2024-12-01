public enum City {
    MONTREAL(1,"Montreal","MTL"),
    TORONTO(2, "Toronto","TOR"),
    VANCOUVER(4, "Vancouver","VAN");

    private int cityCode;
    private String cityName;
    private String cityTrackingCode;

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

    public String toString(){
        return this.cityName;
    }
}