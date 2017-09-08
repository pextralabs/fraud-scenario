package co.pextra.fraud;

import br.ufes.inf.lprm.scene.SceneApplication;
import br.ufes.inf.lprm.scene.base.listeners.SCENESessionListener;
import br.ufes.inf.lprm.situation.model.Situation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javassist.ClassPool;
import org.drools.core.ClassObjectFilter;
import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.junit.Assert;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoubleSessionLocationTest {
    static final Logger LOG = LoggerFactory.getLogger(DoubleSessionLocationTest.class);
    @Test public void test() {
        KieServices kieServices = KieServices.Factory.get();

        KieContainer kContainer = kieServices.getKieClasspathContainer();
        Results verifyResults = kContainer.verify();
        for (Message m : verifyResults.getMessages()) LOG.info("{}", m);

        LOG.info("Creating kieBase");

        KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();
        config.setOption(EventProcessingOption.STREAM);
        KieBase kieBase = kContainer.newKieBase(config);
        FactType sessionType = kieBase.getFactType("co.pextra.fraud", "Session");
        FactType doubleSessionLocationType = kieBase.getFactType("co.pextra.fraud", "DoubleSessionLocation");
        KieSessionConfiguration pseudoConfig = KieServices.Factory.get().newKieSessionConfiguration();
        pseudoConfig.setOption(ClockTypeOption.get("pseudo"));

        LOG.info("There should be rules: ");
        for ( KiePackage kp : kieBase.getKiePackages() ) {
            for (Rule rule : kp.getRules()) LOG.info("kp " + kp + " rule " + rule.getName());
        }

        LOG.info("Creating kieSession");
        KieSession session = kieBase.newKieSession(pseudoConfig, null);
        SessionPseudoClock clock = session.getSessionClock();

        new SceneApplication(ClassPool.getDefault(), session, "fraud-scenario"   );

        session.addEventListener(new SCENESessionListener());

        LOG.info("Now running data");

        Client client = new Client("client", 10);
        Device device = new Device(-20.3431336, -40.2864437);
        AuthToken token1 = new AuthToken(device, client);
        
        session.insert(client);
        session.insert(device);
        session.insert(token1);
        session.fireAllRules();
        {
            ArrayList<Situation> situations =  getSituations(session, sessionType);
            // Assert there is 1 situation
            Assert.assertEquals(1, situations.size());
        }
        
        clock.advanceTime(1, TimeUnit.MINUTES);
        AuthToken token2 = new AuthToken(device, client);
        session.insert(token2);
        session.fireAllRules();
        {
            ArrayList<Situation> situations = getSituations(session, sessionType, doubleSessionLocationType);
            Assert.assertEquals(3, situations.size());
        }
        LOG.info("Final checks");        
    }
    ArrayList<Situation> getSituations (KieSession session, FactType... types) {
        ArrayList<Situation> situs = new ArrayList<Situation>();
        for (FactType type : types) {
            if (type != null) {
                Collection<Situation> typeSitus = (Collection<Situation>) session.getObjects(new ClassObjectFilter(type.getFactClass()));
                situs.addAll(typeSitus);
            }
        }
        return situs;
    }
}
