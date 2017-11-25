package pharmaceutical;

import java.util.ArrayList;
import java.util.List;

public class Container {
    private Temperature temperature;
    private Doctor owner;
    private List<Batch> batches;
    public Container(double initialTemperatureValue, Doctor owner) {
        this.temperature = new Temperature(this, new SensorReading(initialTemperatureValue));
        this.owner = owner;
        this.batches = new ArrayList<Batch>();
        this.owner.getContainers().add(this);
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Doctor getOwner() {
        return owner;
    }

    public void setOwner(Doctor owner) {
        this.owner = owner;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatch(List<Batch> batches) {
        this.batches = batches;
    }
}
