package de.ranlab.mr.api;

@com.fasterxml.jackson.annotation.JsonPropertyOrder({ //
        "startZp",
        "endeZp" } //
)
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@javax.xml.bind.annotation.XmlRootElement(name = "MrJob")
@javax.xml.bind.annotation.XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
@javax.xml.bind.annotation.XmlType(propOrder = { "startZp", "endeZp" })
public class JobStartResponse extends de.ranlab.mr.api.Response {

    /**
     *
     */
    private static final long serialVersionUID = 4499864117017157636L;

    // Startzeitpunkt des Jobs
    @com.fasterxml.jackson.annotation.JsonProperty("startZp")
    @com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = de.ranlab.mr.api.LocalDateTimeDeserializer.class)
    @com.fasterxml.jackson.databind.annotation.JsonSerialize(using = de.ranlab.mr.api.LocalDateTimeSerializer.class)
    @javax.xml.bind.annotation.XmlElement(name = "startZp")
    @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(de.ranlab.mr.api.LocalDateTimeAdapter.class)
    private java.time.LocalDateTime startZp;

    // Endezeitpunkt des Jobs
    @com.fasterxml.jackson.annotation.JsonProperty("endeZp")
    @com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = de.ranlab.mr.api.LocalDateTimeDeserializer.class)
    @com.fasterxml.jackson.databind.annotation.JsonSerialize(using = de.ranlab.mr.api.LocalDateTimeSerializer.class)
    @javax.xml.bind.annotation.XmlElement(name = "endeZp")
    @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(de.ranlab.mr.api.LocalDateTimeAdapter.class)
    private java.time.LocalDateTime endeZp;

    public java.time.LocalDateTime getStartZp() {
        return this.startZp;
    }

    public de.ranlab.mr.api.JobStartResponse setStartZp(final java.time.LocalDateTime startZp) {
        this.startZp = startZp;
        return this;
    }

    public java.time.LocalDateTime getEndeZp() {
        return this.endeZp;
    }

    public de.ranlab.mr.api.JobStartResponse setEndeZp(final java.time.LocalDateTime endeZp) {
        this.endeZp = endeZp;
        return this;
    }

    @Override
    public java.lang.String toString() {
        final de.ranlab.mr.api.LocalDateTimeSerializer dtSerializer = new de.ranlab.mr.api.LocalDateTimeSerializer();
        return java.lang.String
            .format("startZp=%s endeZp=%s", dtSerializer.toJson(this.getStartZp()), dtSerializer.toJson(this.getEndeZp()));
    }
}
