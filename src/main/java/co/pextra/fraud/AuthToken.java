package co.pextra.fraud;

import java.util.UUID;

/**
 * Created by bernardosunderhus on 14-May-17.
 */
public class AuthToken {
    private UUID token;
    private Device device;
    private Client client;
    public AuthToken(Device device, Client client) {
        token = UUID.randomUUID();
        this.device = device;
        this.client = client;
    }
    public UUID getToken() {
        return token;
    }
    public Device getDevice () {
        return device;
    }
    public Client getClient () {
        return client;
    }
}
