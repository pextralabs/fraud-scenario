package pharmaceutical;

public class  ProdType {
    private  String type;
    private double maxTemp;

    public ProdType(String type, double maxTemp) {
        this.type = type;
        this.maxTemp = maxTemp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }
}