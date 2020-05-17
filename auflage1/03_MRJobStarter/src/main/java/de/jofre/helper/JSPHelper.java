package de.jofre.helper;

public class JSPHelper {

    private final static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(JSPHelper.class.getName());

    /**
     * Ausgabe von java.lang.Strings in eine JSP über ein POJO
     *
     * @param writer
     * @param text
     */
    public static void writeToJsp(final javax.servlet.jsp.JspWriter writer, final java.lang.String text) {
        if (writer != null) {
            try {
                writer.println(text);
            } catch (final java.io.IOException e) {
                log.warn("Fehler beim Schreiben der JSP.");
                e.printStackTrace();
            }
        }
    }
}
