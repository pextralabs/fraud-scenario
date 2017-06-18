package co.pextra.fraud;

public class Device {
    private Location currentLocation;
    public Device(Double latitude, Double longitude) {
        currentLocation = new Location(latitude, longitude);
    }
    public Location getCurrentLocation() {
        return currentLocation;
    }
    public void setCurrentLocation(Double lat, Double lng){
        currentLocation.setLatitude(lat);
        currentLocation.setLongitude(lng);
    }
}
