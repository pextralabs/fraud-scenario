package pharmaceutical;

import br.ufes.inf.lprm.scene.SceneApplication;
import br.ufes.inf.lprm.scene.base.listeners.SCENESessionListener;
import br.ufes.inf.lprm.situation.model.Situation;
import co.pextra.fraud.Location;
import javassist.ClassPool;
import org.drools.core.ClassObjectFilter;
import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
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
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class TemperatureTest {
    static final Logger LOG = LoggerFactory.getLogger(TemperatureTest.class);
    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        KieServices kieServices = KieServices.Factory.get();

        KieContainer kContainer = kieServices.getKieClasspathContainer();
        Results verifyResults = kContainer.verify();
        for (Message m : verifyResults.getMessages()) LOG.info("{}", m);

        LOG.info("Creating kieBase");

        KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();
        config.setOption(EventProcessingOption.STREAM);
        KieBase kieBase = kContainer.newKieBase(config);
        FactType highTempType = kieBase.getFactType("container", "HighTemp");
        KieSessionConfiguration pseudoConfig = KieServices.Factory.get().newKieSessionConfiguration();
        pseudoConfig.setOption(ClockTypeOption.get("pseudo"));

        LOG.info("There should be rules: ");
        for ( KiePackage kp : kieBase.getKiePackages() ) {
            for (Rule rule : kp.getRules()) LOG.info("kp " + kp + " rule " + rule.getName());
        }

        LOG.info("Creating kieSession");
        KieSession session = kieBase.newKieSession(pseudoConfig, null);
        SessionPseudoClock clock = session.getSessionClock();

        new SceneApplication(ClassPool.getDefault(), session, "fraud-scenario");

        session.addEventListener(new SCENESessionListener());

        LOG.info("Now running data");
        Doctor doctor = new Doctor(new Location(-20.2976180, -40.2957770));
        Container container = new Container(25, doctor);
        ProdType vaccine = new ProdType("vaccine", 36);
        Batch vaccinesBatch = new Batch(vaccine, container);

        session.insert(doctor);
        FactHandle containerFact = session.insert(container);
        session.insert(vaccine);
        session.insert(vaccinesBatch);

        session.fireAllRules();

        container.getTemperature().setLastValue(new SensorReading(37));
        session.update(containerFact, container);
        session.fireAllRules();

        clock.advanceTime(2, TimeUnit.MINUTES);

        container.getTemperature().setLastValue(new SensorReading(34));
        session.update(containerFact, container);
        session.fireAllRules();
        clock.advanceTime(2, TimeUnit.MINUTES);

        container.getTemperature().setLastValue(new SensorReading(37));
        session.update(containerFact, container);
        session.fireAllRules();
    }
    private ArrayList<Situation> getSituations (KieSession session, FactType... types) {
        ArrayList<Situation> situations = new ArrayList<Situation>();
        for (FactType type : types) {
            if (type != null) {
                Collection<Situation> typeSituations = (Collection<Situation>) session.getObjects(new ClassObjectFilter(type.getFactClass()));
                situations.addAll(typeSituations);
            }
        }
        return situations;
    }
}