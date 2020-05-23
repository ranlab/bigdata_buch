package de.ranlab.mr.api;

/**
 * Response eines RESTful-Requests
 * @author randrae
 */
@javax.xml.bind.annotation.XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class Response implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6842655270591618809L;

    private static final java.lang.String KEY_NODE_NAME = "jboss.node.name";

    /**
     * Header für den Response.
     */
    @javax.validation.constraints.NotNull(message = "{biz.ff.kontosystem.api.Response.responseHeader.NotNull.message}")
    @javax.validation.Valid
    @com.fasterxml.jackson.annotation.JsonProperty("header")
    @javax.xml.bind.annotation.XmlElement(name = "header")
    private de.ranlab.mr.api.Response.HeaderResult responseHeader;

    public Response() {
        this.responseHeader = new de.ranlab.mr.api.Response.HeaderResult();
        this.responseHeader.setNodeName(java.lang.System.getProperty(KEY_NODE_NAME));
    }

    public de.ranlab.mr.api.Response.HeaderResult getHeader() {
        return this.responseHeader;
    }

    public void setHeader(final de.ranlab.mr.api.Response.HeaderResult newRcHeader) {
        this.responseHeader = newRcHeader;
    }

    /**
     * Struktur für die Header-Informationen
     */
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    @com.fasterxml.jackson.annotation.JsonPropertyOrder({ "rc", "rcText", "nodeName" })
    @javax.xml.bind.annotation.XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
    @javax.xml.bind.annotation.XmlType(propOrder = { "rc", "rcText", "nodeName" })
    public static class HeaderResult implements java.io.Serializable {

        /**
         *
         */
        private static final long serialVersionUID = 4443893056069441299L;

        @javax.validation.constraints.NotNull
        @com.fasterxml.jackson.annotation.JsonIgnore
        @javax.xml.bind.annotation.XmlElement(name = "rc")
        private int rc = 0;
        // Klartext zum Return-Code
        @com.fasterxml.jackson.annotation.JsonProperty("rcText")
        @javax.xml.bind.annotation.XmlElement(name = "rcText")
        private java.lang.String rcText;
        // Node-Name
        @com.fasterxml.jackson.annotation.JsonProperty("nodeName")
        @javax.xml.bind.annotation.XmlElement(name = "nodeName")
        private java.lang.String nodeName;

        /**
         * Konvertiert den RC-Code in  einen String für die Json-Serialisierung
         * @return
         */
        @com.fasterxml.jackson.annotation.JsonGetter("rc")
        private final java.lang.String toJsonRc() {
            return "" + this.getRc();
        }
        public final int getRc() {
            return this.rc;
        }

        @com.fasterxml.jackson.annotation.JsonSetter("rc")
        public final void setRc(final java.lang.String newRc) {
            this.rc = -999;
            try {
                this.rc = java.lang.Integer.parseInt(newRc);
            } catch (final java.lang.NumberFormatException nex) {
            }
        }
        public final void setRc(final int newRc) {
            this.rc = newRc;
        }

        public final java.lang.String getRcText() {
            return this.rcText;
        }

        public final void setRcText(final java.lang.String rcText) {
            this.rcText = rcText;
        }

        public java.lang.String getNodeName() {
            return this.nodeName;
        }

        public void setNodeName(final java.lang.String nodeName) {
            this.nodeName = nodeName;
        }

        @Override
        public final java.lang.String toString() {
            return java.lang.String.format("rc=%d rcText=%s", this.getRc(), this.getRcText());
        }
    }
}
