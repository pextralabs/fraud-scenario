package co.pextra.fraud;

import org.kie.api.runtime.KieSession;

/**
 * Created by bernardosunderhus on 29/04/17.
 */
public class RuleEngineThread extends Thread {
    private KieSession ksession;
    public RuleEngineThread(KieSession ksession) {
        this.ksession = ksession;
    }
    public void run() {
        this.ksession.fireUntilHalt();
    }
}