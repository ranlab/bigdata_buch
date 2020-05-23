package de.ranlab.mr.api;

/**
 * Liest ein Datum aus einem JSon.
 * @author randrae
 */
public class LocalDateDeserializer extends com.fasterxml.jackson.databind.deser.std.StdDeserializer<java.time.LocalDate> {

    /**
     *
     */
    private static final long serialVersionUID = 2817585510589829979L;

    private final java.time.format.DateTimeFormatter formatter;

    protected LocalDateDeserializer() {
        super(java.time.LocalDate.class);
        this.formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy.MM.dd");
    }

    @Override
    public java.time.LocalDate deserialize(final com.fasterxml.jackson.core.JsonParser jp,
        final com.fasterxml.jackson.databind.DeserializationContext ctxt)
        throws java.io.IOException,
            com.fasterxml.jackson.core.JsonProcessingException {
        final java.lang.String sDate = jp.readValueAs(java.lang.String.class);
        if ((sDate == null) || (sDate.isEmpty() == true)) {
            return null;
        }
        try {
            return java.time.LocalDate.parse(sDate, this.formatter);
        } catch (final java.time.format.DateTimeParseException ex1) {
            return null;
        }
    }
}
