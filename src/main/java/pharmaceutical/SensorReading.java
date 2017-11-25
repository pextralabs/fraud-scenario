package pharmaceutical;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.io.Serializable;
import java.util.Date;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("2h30m")
public class SensorReading implements Serializable{
    private static final long serialVersionUID = 2L;
    private Date executionTime;
    private double value;

    public SensorReading () {}

    public SensorReading(double value) {
        this.value = value;
        this.executionTime = new Date();
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
