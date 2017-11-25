package pharmaceutical;

import co.pextra.fraud.Location;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private Location location;
    private List<Container> containers;

    public Doctor(Location location) {
        this.location = location;
        this.containers = new ArrayList<Container>();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }
}
