package de.ranlab.mr.api;

public class LocalDateTimeSerializer extends com.fasterxml.jackson.databind.ser.std.StdSerializer<java.time.LocalDateTime> {

    /**
     *
     */
    private static final long serialVersionUID = 2021766810680877940L;

    private final java.time.format.DateTimeFormatter formatter;

    public LocalDateTimeSerializer() {
        super(java.time.LocalDateTime.class);
        this.formatter = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
    }

    @Override
    public void serialize(final java.time.LocalDateTime value,
        final com.fasterxml.jackson.core.JsonGenerator gen,
        final com.fasterxml.jackson.databind.SerializerProvider sp)
        throws java.io.IOException,
            com.fasterxml.jackson.core.JsonProcessingException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(this.formatter.format(value));
        }
    }

    public java.lang.String toJson(final java.time.LocalDateTime value) {
        if (value == null) {
            return "null";
        } else {
            return this.formatter.format(value);
        }
    }
}
