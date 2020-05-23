package de.ranlab.mr.application;

/**
 * Das ist ein Wrapper um den konkreten Logger. Damit ist er schneller austauschbar.
 * @author randrae
 */
public class Logger {

    private final org.apache.logging.log4j.Logger logger;

    public Logger(final java.lang.Class<?> newClazz) {
        this.logger = org.apache.logging.log4j.LogManager.getLogger(newClazz);
    }

    public Logger(final java.lang.String newName) {
        this.logger = org.apache.logging.log4j.LogManager.getLogger(newName);
    }

    /**
     * Die Log-Nachicht ist komplett aufgebaut und wird nur mit der CID verknüpft.
     * Nutzer ist der RestProcessorInterceptor beim Erstellen der unterschiedlichen Exceptions.
     * @param newMessage         obligatorisch, Log-Nachricht
     */
    public void info(final java.lang.String newMessage) {
        this.logger.info(newMessage);
    }

    /**
     * Eine Log-Nachricht mit einer Exception
     * @param newMessage         obligatorisch, Log-Nachricht
     * @param newThrowable       obligatorisch, Exception
     */
    public void info(final java.lang.String newMessage, final java.lang.Throwable newThrowable) {
        this.logger.info(newMessage, newThrowable);
    }

    /**
     * Direkte Ausgabe auf den Logger.
     * @param newLogPrefix obligatorisch, Prefix der Log-Nachricht
     * @param newMessage   obligatorisch, Log-Nachricht
     */
    public void debug(final java.lang.String newLogPrefix, final java.lang.String newMessage) {
        this.logger.debug(java.lang.String.format("%s %s", newLogPrefix, newMessage));
    }

    public void error(final java.lang.String newMessage) {
        this.logger.error(newMessage);
    }
    public void error(final java.lang.String newLogPrefix, final java.lang.String newMessage, final java.lang.Throwable newThrowable) {
        this.logger.error(java.lang.String.format("%s %s", newLogPrefix, newMessage), newThrowable);
    }
    public void error(final java.lang.String newMessage, final java.lang.Throwable newThrowable) {
        this.logger.error(newMessage, newThrowable);
    }

    /**
     * Wandelt einen Request/Response in einen Log-Eintrag
     * @param newParameter
     * @return
     */
    public static java.lang.String toLog(final java.lang.Object[] newParameter) {
        final java.lang.StringBuffer bufParameter = new java.lang.StringBuffer();
        int pPos = 0;
        for (final java.lang.Object oParam : newParameter) {
            java.lang.String objEntity = "";
            if (oParam instanceof de.ranlab.mr.api.Request) {
                try {
                    final de.ranlab.mr.api.Request jsonEntity = (de.ranlab.mr.api.Request) oParam;
                    final com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    objEntity = mapper.writeValueAsString(jsonEntity);
                } catch (final com.fasterxml.jackson.core.JsonProcessingException ex) {
                    objEntity = "" + oParam;
                } catch (final java.lang.RuntimeException ex1) {
                    objEntity = "" + oParam;

                }
            } else if (oParam instanceof java.lang.String) {
                objEntity = (java.lang.String) oParam;
            } else {
                objEntity = "" + oParam;
            }

            if (pPos > 0) {
                bufParameter.append(" ");
            }
            bufParameter.append(objEntity);
            pPos++;
        }
        return bufParameter.toString();
    }

    /**
     * Wandelt einen Request/Response in einen Log-Eintrag
     * @param newEntity
     * @return
     */
    public static java.lang.String toLog(final java.lang.Object newEntity) {
        java.lang.String objEntity = "";
        if (newEntity instanceof de.ranlab.mr.api.Request) {
            try {
                final de.ranlab.mr.api.Request jsonEntity = (de.ranlab.mr.api.Request) newEntity;
                final com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                objEntity = mapper.writeValueAsString(jsonEntity);
            } catch (final com.fasterxml.jackson.core.JsonProcessingException ex) {
                objEntity = "" + newEntity;
            } catch (final java.lang.RuntimeException ex1) {
                objEntity = "" + newEntity;

            }
        } else if (newEntity instanceof java.lang.String) {
            objEntity = (java.lang.String) newEntity;
        } else {
            objEntity = "" + newEntity;
        }
        return objEntity;
    }
}
