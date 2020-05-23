package de.ranlab.mr.api;

/**
 * Liest ein Datum aus einem JSon.
 * @author promyx
 */
public class LocalDateTimeDeserializer extends com.fasterxml.jackson.databind.deser.std.StdDeserializer<java.time.LocalDateTime> {

    /**
     *
     */
    private static final long serialVersionUID = 2817585510589829979L;

    private final java.time.format.DateTimeFormatter formatter;

    protected LocalDateTimeDeserializer() {
        super(java.time.LocalDate.class);
        this.formatter = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
    }

    @Override
    public java.time.LocalDateTime deserialize(final com.fasterxml.jackson.core.JsonParser jp,
        final com.fasterxml.jackson.databind.DeserializationContext ctxt)
        throws java.io.IOException,
            com.fasterxml.jackson.core.JsonProcessingException {
        final java.lang.String sDate = jp.readValueAs(java.lang.String.class);
        if ((sDate == null) || (sDate.isEmpty() == true)) {
            return null;
        }
        try {
            return java.time.LocalDateTime.parse(sDate, this.formatter);
        } catch (final java.time.format.DateTimeParseException ex1) {
            return null;
        }
    }
}
