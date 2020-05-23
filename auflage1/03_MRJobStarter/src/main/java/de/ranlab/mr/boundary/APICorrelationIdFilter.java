package de.ranlab.mr.boundary;

/**
 * Es wird ein Filter für die eingehenden Requests und die
 * ausgehenden Results initiiert, der eine CID ausliest.
 * Über <code>org.jboss.logging.MDC</code> soll die dann in das Logging kommen.
 *
 * @author randrae
 */
@javax.ws.rs.ext.Provider
@javax.annotation.Priority(javax.ws.rs.Priorities.HEADER_DECORATOR)
public class APICorrelationIdFilter implements javax.ws.rs.container.ContainerRequestFilter, javax.ws.rs.container.ContainerResponseFilter {
    private static final java.lang.String DEFAULT_HEADER_NAME = "CID";
    public static final java.lang.String DEFAULT_MDC_NAME = "CID";

    private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager
        .getLogger(APICorrelationIdFilter.class);

    private static java.lang.ThreadLocal<java.lang.String> cid = new java.lang.ThreadLocal<java.lang.String>();

    // Key im HTTP-HEader für die CID
    private final java.lang.String headerName;
    // Key in Log-Pattern für die CID
    private final java.lang.String mdcName;

    public APICorrelationIdFilter(final java.lang.String headerName, final java.lang.String mdcName) {
        this.headerName = java.util.Optional.ofNullable(headerName).orElse(DEFAULT_HEADER_NAME);
        this.mdcName = java.util.Optional.ofNullable(mdcName).orElse(DEFAULT_MDC_NAME);
    }

    public APICorrelationIdFilter() {
        this(null, null);
    }

    /**
     * Aufruf vor dem Aufruf der Resource.
     */
    @Override
    public void filter(final javax.ws.rs.container.ContainerRequestContext requestContext)
        throws java.io.IOException {
        java.lang.String correlationId = requestContext.getHeaderString(this.headerName.toUpperCase());
        if (correlationId == null) {
            correlationId = requestContext.getHeaderString(this.headerName.toLowerCase());
        }
        if (correlationId == null) {
            correlationId = "CID_" + java.util.UUID.randomUUID().toString();
            LOGGER.debug("Missing correlation id - new id " + correlationId + " created!");
            requestContext.getHeaders().add(this.headerName, correlationId);
        }

        // MDC Werte setzen
        org.apache.logging.log4j.ThreadContext.put(this.mdcName, correlationId);
        cid.set(correlationId);
    }

    /**
     * Aufruf nach der Verarbeitung.
     */
    @Override
    public void filter(final javax.ws.rs.container.ContainerRequestContext requestContext,
        final javax.ws.rs.container.ContainerResponseContext responseContext)
        throws java.io.IOException {
        org.apache.logging.log4j.ThreadContext.remove(this.mdcName);
        java.lang.String correlationIdRequest = requestContext.getHeaderString(this.headerName.toUpperCase());
        if (correlationIdRequest == null) {
            correlationIdRequest = requestContext.getHeaderString(this.headerName.toLowerCase());
        }
        // sollte eigentlich nie vorkommen
        if (correlationIdRequest == null) {
            correlationIdRequest = java.util.UUID.randomUUID().toString();
        }
        java.lang.String correlationIdResponse = responseContext.getHeaderString(this.headerName.toUpperCase());
        if (correlationIdResponse == null) {
            correlationIdResponse = responseContext.getHeaderString(this.headerName.toLowerCase());
        }
        if (correlationIdResponse == null) {
            responseContext.getHeaders().add(this.headerName, correlationIdRequest);
        }
    }

    public static java.lang.String getCid() {
        return cid.get();
    }
}
