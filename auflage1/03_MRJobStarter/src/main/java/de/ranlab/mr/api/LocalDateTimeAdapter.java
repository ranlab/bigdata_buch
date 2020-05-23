package de.ranlab.mr.api;

public class LocalDateTimeAdapter extends javax.xml.bind.annotation.adapters.XmlAdapter<java.lang.String, java.time.LocalDateTime> {

    private java.time.format.DateTimeFormatter formatter;

    protected LocalDateTimeAdapter() {
        super();
        this.formatter = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
    }

    @Override
    public java.time.LocalDateTime unmarshal(final java.lang.String newValue)
        throws java.time.format.DateTimeParseException {

        if ((newValue == null) || (newValue.isEmpty() == true)) {
            return null;
        }
        try {
            return java.time.LocalDateTime.parse(newValue, this.formatter);
        } catch (final java.time.format.DateTimeParseException ex1) {
            return null;
        }
    }

    @Override
    public java.lang.String marshal(final java.time.LocalDateTime newTime) {
        if (newTime == null) {
            return null;
        } else {
            return this.formatter.format(newTime);
        }

    }
}
