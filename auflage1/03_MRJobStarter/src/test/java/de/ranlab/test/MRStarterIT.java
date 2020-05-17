package de.ranlab.test;

/**
 * Junit-Test für den Starter
 *
 * @see https://junit.org/junit5/docs/current/user-guide/
 * @see https://docs.marklogic.com/guide/mapreduce/hadoop
 * @author promyx
 */
@org.junit.jupiter.api.TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class MRStarterIT {
    private final static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(MRStarterIT.class.getName());
    private static de.jofre.mrstarter.MRStarter mrstarter;

    @org.junit.jupiter.api.BeforeAll
    static void setup() {
        log.info("@BeforeAll - executes once before all test methods in this class");
        mrstarter = new de.jofre.mrstarter.MRStarter();
    }

    /**
     * Löscht das Ausgabeverzeichnis
     */
    @org.junit.jupiter.api.DisplayName("Lösche das Ausgabeverzeichnis")
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(1)
    public void test001_loescheAusgabe() {
        final boolean result = mrstarter.deleteOutput();
        org.junit.jupiter.api.Assertions.assertTrue(result);
    }

    /**
     * Startet den Job
     */
    @org.junit.jupiter.api.DisplayName("Startet den Job")
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(2)
    public void tesst002_startJob() {
        mrstarter.startJob(null);
    }
}
