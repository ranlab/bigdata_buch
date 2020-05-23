package de.ranlab.mr.api;

/**
 * Beschreibt den Typ des Response - Meldung für Kontosystem oder Konto
 * @author randrae
 */
public enum ResponseType {
    KONTOSYSTEM("KONTOSYSTEM"), //
    KONTO("KONTO"), //
    ;

    @com.fasterxml.jackson.annotation.JsonValue()
    private final java.lang.String type;

    private ResponseType(final java.lang.String newType) {
        this.type = newType;
    }

    public java.lang.String getType() {
        return this.type;
    }

    @com.fasterxml.jackson.annotation.JsonCreator
    public static ResponseType forValue(final java.lang.String newValue) {
        for (final ResponseType t : ResponseType.values()) {
            if (t.getType().equals(newValue)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Adapter für das XML-Schreiben
     */
    public static class ResponseTypeAdapter
        extends javax.xml.bind.annotation.adapters.XmlAdapter<java.lang.String, de.ranlab.mr.api.ResponseType> {

        @Override
        public de.ranlab.mr.api.ResponseType unmarshal(final java.lang.String newValue)
            throws java.lang.Exception {
            return ResponseType.forValue(newValue);
        }

        @Override
        public java.lang.String marshal(final de.ranlab.mr.api.ResponseType newType)
            throws java.lang.Exception {
            return newType.getType();
        }
    }
}
