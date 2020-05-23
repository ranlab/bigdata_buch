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
        log.info("Jetzt gehts los.");
        mrstarter.startJob(null);
    }
}
