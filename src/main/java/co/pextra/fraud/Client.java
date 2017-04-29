package co.pextra.fraud;

/**
 * Created by bernardosunderhus on 29/04/17.
 */
public class Client {
    private String name;
    private Long id;

    public  Client(String name, Long id) {
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
