package co.pextra.fraud;

import java.util.Date;
import java.util.List;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
public class SuspiciousActivity {
    private  Client client;
    private List<TransactionEvent> transactionEvents;
    private  String description;
    private Date executionTime;
    public SuspiciousActivity(Client client, List<TransactionEvent> transactionEvents, String description) {
        this.client = client;
        this.transactionEvents = transactionEvents;
        this.description = description;
        this.executionTime = new Date();
    }

    @Override
    public String toString() {
        return "Client:" + client.getName() + ", Transactions Amount:" + transactionEvents.size() + ", description: " + description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<TransactionEvent> getTransactionEvents() {
        return transactionEvents;
    }

    public void setTransactionEvents(List<TransactionEvent> transactionEvents) {
        this.transactionEvents = transactionEvents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }
}
