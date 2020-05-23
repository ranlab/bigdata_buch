package de.ranlab.mr.boundary;

public class AbstractEndpoint {

    /**
     * Ein übergebener Json-String wird in eine Java-Klasse gewandelt
     * @param newJson      obligatorisch, Json-String
     * @param clazzRequest obligatorisch, Ziel-Klasse
     * @return
     */
    protected <TRES extends java.io.Serializable> java.util.Optional<TRES> castRequest(final java.lang.String newJson,
        final java.lang.Class<TRES> clazzRequest) {
        try {
            final com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            final TRES request = mapper.readValue(newJson, clazzRequest);
            return java.util.Optional.of(request);

        } catch (final javax.json.stream.JsonParsingException pex) {
        } catch (final com.fasterxml.jackson.databind.JsonMappingException pex1) {
        } catch (final java.io.IOException iex) {
        }

        return java.util.Optional.empty();
    }
}
