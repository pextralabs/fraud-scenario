package co.pextra.fraud;

import java.util.UUID;

/**
 * Created by bernardosunderhus on 29/04/17.
 */
public class Client {
    private String name;
    private UUID id;
    private long balance;

    public Client(String name, long balance) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }
}
