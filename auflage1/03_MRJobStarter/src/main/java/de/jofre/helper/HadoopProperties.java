package de.jofre.helper;

/**
 * Platzhalter für die hadoop.properties Datei.
 * Arbeitet als Singleton.
 * @author promyx
 */
public class HadoopProperties {

    private static java.util.Properties prop = null;
    private final static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
        .getLogger(HadoopProperties.class.getName());

    /**
     * Klasse wird einmalig initialisiert
     */
    private static void initProps() {
        prop = new java.util.Properties();
        try {
            // Lesen der Datei aus dem ClassPath WebContent/WEB-INF/classes/
            prop.load(HadoopProperties.class.getClassLoader().getResourceAsStream("hadoop.properties"));
        } catch (final java.io.IOException e) {
            log.info("Konnte Properties-Datei nicht laden!");
            e.printStackTrace();
        }
    }

    /**
     * Zugriff auf eine Eigenschaft in der hadoop.properties
     *
     * @param key
     * @return
     */
    public static java.lang.String get(final java.lang.String key) {
        if (prop == null) {
            initProps();
        }
        final java.lang.String property = prop.getProperty(key);
        if (property == null) {
            log.info("Property '" + key + "' konnte nicht gefunden werden!");
        }
        return property;
    }
}
