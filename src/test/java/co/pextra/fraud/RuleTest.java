package co.pextra.fraud;

import br.ufes.inf.lprm.scene.SceneApplication;
import br.ufes.inf.lprm.scene.base.listeners.SCENESessionListener;

// import org.apache.log4j.chainsaw.Main;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RuleTest {
    static final Logger LOG = LoggerFactory.getLogger(RuleTest.class);
    // @Test
    public void test() {
        KieServices kieServices = KieServices.Factory.get();

        KieContainer kContainer = kieServices.getKieClasspathContainer();
        Results verifyResults = kContainer.verify();
        for (Message m : verifyResults.getMessages()) {
            LOG.info("{}", m);
        }

        LOG.info("Creating kieBase");

        KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();
        config.setOption(EventProcessingOption.STREAM);
        KieBase kieBase = kContainer.newKieBase(config);

        LOG.info("There should be rules: ");
        for ( KiePackage kp : kieBase.getKiePackages() ) {
            for (Rule rule : kp.getRules()) {
                LOG.info("kp " + kp + " rule " + rule.getName());
            }
        }

        LOG.info("Creating kieSession");
        KieSession session = kieBase.newKieSession();

        new SceneApplication("fraud-scenario", session);

        session.addEventListener(new SCENESessionListener());

        final RuleEngineThread ruleEngineThread = new RuleEngineThread(session);
        ruleEngineThread.start();

        LOG.info("Now running data");

        Client client1 = new Client("John doe", 1L);
        session.insert(client1);
        for (int i = 0; i < 15; i++) {
            session.insert(new Transaction(client1, 0.0));
        }
        Client client2 = new Client("David Hasselhoft", 1L);
        session.insert(client2);
        for (int i = 0; i < 2; i++) {
//            session.insert(new Transaction(client2.getId(), 2001.0));
            session.insert(new Transaction(client1, 2001.0));
        }

        Client client3 = new Client("Little fish", 1L);
        session.insert(client3);

        EntryPoint smallClients = session.getEntryPoint("small client");
        for (int i = 0; i < 11; i++) {
//            smallClients.insert(new Transaction(client3.getId(), 20.0));
            smallClients.insert(new Transaction(client1, 20.0));
        }
        Client client4 = new Client("Big fish",1L);
        session.insert(client4);

        EntryPoint bigClients = session.getEntryPoint("big client");
        for (int i = 0; i < 101; i++) {
//            bigClients.insert(new Transaction(client4.getId(), 20.0));
            bigClients.insert(new Transaction(client1, 20.0));
        }

        LOG.info("Final checks");

        while(true);

    }
}
