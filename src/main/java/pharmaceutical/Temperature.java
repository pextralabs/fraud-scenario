package pharmaceutical;

public class Temperature {
    private SensorReading lastValue;
    private Container bearer;

    public Temperature(Container bearer, SensorReading lastValue) {
        this.lastValue = lastValue;
        this.bearer = bearer;
    }

    public SensorReading getLastValue() {
        return lastValue;
    }

    public void setLastValue(SensorReading lastValue) {
        this.lastValue = lastValue;
    }

    public Container getBearer() {
        return bearer;
    }

    public void setBearer(Container bearer) {
        this.bearer = bearer;
    }
}
