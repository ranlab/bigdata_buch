package de.ranlab.mr.api;

public class LocalDateAdapter extends javax.xml.bind.annotation.adapters.XmlAdapter<java.lang.String, java.time.LocalDate> {

    private final java.time.format.DateTimeFormatter formatter;

    public LocalDateAdapter() {
        super();
        this.formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy.MM.dd");
    }

    @Override
    public java.time.LocalDate unmarshal(final java.lang.String newValue)
        throws java.lang.Exception {
        if ((newValue == null) || (newValue.isEmpty() == true)) {
            return null;
        }
        try {
            return java.time.LocalDate.parse(newValue, this.formatter);
        } catch (final java.time.format.DateTimeParseException ex1) {
            return null;
        }
    }

    @Override
    public java.lang.String marshal(final java.time.LocalDate newDate)
        throws java.lang.Exception {
        if (newDate == null) {
            return null;
        } else {
            return this.formatter.format(newDate);
        }
    }
}
