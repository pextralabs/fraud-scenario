package co.pextra.fraud;

public class Device {
    private AuthToken token;
    private Location currentLocation;
    public Device(AuthToken token, Double latitude, Double longitude) {
        this.token = token;
        currentLocation = new Location(latitude, longitude);
    }
    public AuthToken getToken() {
        return token;
    }
    public Location getCurrentLocation() {
        return currentLocation;
    }
    public void setCurrentLocation(Double lat, Double lng){
        currentLocation.setLatitude(lat);
        currentLocation.setLongitude(lng);
    }
}