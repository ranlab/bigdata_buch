package de.ranlab.mr.application;

/**
 * REST-Service Einstieg der Anwendung.
 */
@javax.ws.rs.ApplicationPath("/api/v1.0")
@org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition( //
    info = @org.eclipse.microprofile.openapi.annotations.info.Info(//
        title = "Loader Extend", version = "1.0.0", //
        description = "Erweiterung des Loaders", //
        contact = @org.eclipse.microprofile.openapi.annotations.info.Contact(email = "ruediger.andrae@first-financial.biz", name = "Rüdiger Andrae")))
public class ApplicationConfig extends javax.ws.rs.core.Application {
    public static final java.util.concurrent.atomic.AtomicBoolean IS_ALIVE = new java.util.concurrent.atomic.AtomicBoolean(true);

    @javax.inject.Inject
    private de.ranlab.mr.application.Logger logger;

    /**
     * Wird beim Starten der Anwendung duchlaufen
     */
    @Override
    public java.util.Map<java.lang.String, java.lang.Object> getProperties() {
        final java.util.Map<java.lang.String, java.lang.Object> tmpProtperties = super.getProperties();

        // Kontrolle der Proxy-Einstellung
        this.logger.info(java.lang.String.format("%-20s: %s", "Proxy-Host", java.lang.System.getProperty("http.proxyHost")));
        this.logger.info(java.lang.String.format("%-20s: %d", "Proxy-Port", java.lang.System.getProperty("http.proxyPort")));
        this.logger.info(java.lang.String.format("%-20s: %s", "Non-Proxy-Hosts", java.lang.System.getProperty("http.nonProxyHosts")));

        // Setze den Hadoop-User
        java.lang.System.setProperty("HADOOP_USER_NAME", de.jofre.helper.HadoopProperties.getHadoopUser());
        this.logger.info(java.lang.String.format("%-20s: %s", "Hadoop User", java.lang.System.getProperty("HADOOP_USER_NAME")));

        return tmpProtperties;
    }
}
