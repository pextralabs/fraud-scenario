package pharmaceutical;

public class Batch {
    private String code;
    private ProdType productType;
    private Container container;

    public Batch(ProdType productType, Container container) {
        this.productType = productType;
        this.container = container;
        container.getBatches().add(this);
    }

    public ProdType getProductType() {
        return productType;
    }

    public void setProductType(ProdType productType) {
        this.productType = productType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
