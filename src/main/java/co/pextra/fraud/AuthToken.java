package co.pextra.fraud;

import java.util.UUID;

/**
 * Created by bernardosunderhus on 14-May-17.
 */
public class AuthToken {
    private UUID token;
    public AuthToken() {
        token = UUID.randomUUID();
    }
    public UUID getToken() {
        return token;
    }
}
