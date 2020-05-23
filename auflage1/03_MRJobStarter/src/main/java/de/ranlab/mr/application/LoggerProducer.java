package de.ranlab.mr.application;

/**
 * Stellt den Logger per Injection zur Verfügung.
 * Die Einstellung der log4j-Konfiguration erfolgt über project-defaults.yml.
 *
 * @author promyx
 */
@javax.enterprise.context.ApplicationScoped
public class LoggerProducer {

    /**
     * Defaultkonstruktor
     */
    public LoggerProducer() {
    }

    /**
     * Lade 1x die Log-Konfiguration
     */
    @javax.annotation.PostConstruct
    private void startup() {
    }

    /**
     * Erstellt einen normalen Logger.
     * @param injectionPoint
     * @return
     */
    @javax.enterprise.inject.Produces
    @javax.enterprise.inject.Default
    public de.ranlab.mr.application.Logger buildLogger(final javax.enterprise.inject.spi.InjectionPoint injectionPoint) {
        return new de.ranlab.mr.application.Logger(injectionPoint.getMember().getDeclaringClass());
    }

}
