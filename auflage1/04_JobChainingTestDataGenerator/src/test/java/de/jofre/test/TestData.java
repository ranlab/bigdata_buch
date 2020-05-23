package de.jofre.test;

@org.junit.jupiter.api.TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class TestData {

    private final static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(TestData.class.getName());

    /**
     * Generiert Testdaten der Form "Vorname Nachname \t Note"
     *
     * @param args
     */
    @org.junit.jupiter.api.DisplayName("Generiert Testdaten")
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(1)
    public void test001_loescheAusgabeverzeichnis() {

        final int data_counter = 2000;
        final float minX = 1.0f;
        final float maxX = 5.0f;

        log.info("Schreibe " + data_counter + " Studenten...");
        try {
            final java.util.Random r = new java.util.Random();
            final java.io.PrintWriter out = new java.io.PrintWriter("mr_job_chaining_data.txt");

            for (int i = 0; i < data_counter; i++) {

                final int randomGrades = r.nextInt(10);

                // Füge mehrere Einträge für einen Namen hinzu
                final String name = NamesByGender.getRandomName();
                final String lastName = NamesByGender.getRandomLastName();

                for (int j = 0; j < randomGrades; j++) {

                    // Schreibe Datei
                    out.print(name + " ");
                    out.print(lastName);
                    out.print("\t");
                    out.print(String.format("%.1f", (r.nextFloat() * (maxX - minX)) + minX));
                    out.print(System.getProperty("line.separator"));
                }
            }

            out.close();
            log.info("Fertig!");

        } catch (final java.io.FileNotFoundException e) {
            log.error("Fehler beim Schreiben!");
            e.printStackTrace();
        }
    }

}
