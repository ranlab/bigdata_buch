package de.ranlab.mr.api;

public class LocalDateSerializer extends com.fasterxml.jackson.databind.ser.std.StdSerializer<java.time.LocalDate> {

    /**
     *
     */
    private static final long serialVersionUID = 2021766810680877940L;

    private final java.time.format.DateTimeFormatter formatter;

    public LocalDateSerializer() {
        super(java.time.LocalDate.class);
        this.formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy.MM.dd");
    }

    @Override
    public void serialize(final java.time.LocalDate value,
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

    public java.lang.String toJson(final java.time.LocalDate value) {
        if (value == null) {
            return "null";
        } else {
            return this.formatter.format(value);
        }
    }
}
