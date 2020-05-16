package de.jofre.helper;

public class WinUtilsSolver {
    private final static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
        .getLogger(WinUtilsSolver.class.getName());

    /**
     * Überprüft, ob die Datei winutils.exe existiert. Wenn nicht, wird
     * eine leere Datei angelegt, um einen Teil der Funktionalität der HDFS-API
     * zu gewährleisten.
     */
    public static void solveWinUtilError() {

        // Erfrage, ob die Systemeigenschaft hadoop.home.dir gesetzt ist
        if (System.getProperty("hadoop.home.dir") != null) {

            String hDir = System.getProperty("hadoop.home.dir");
            if (!hDir.endsWith("\\")) {
                hDir = hDir += "\\";
            }

            // Wenn ja, überprüfe ob darin ein Ordner "bin" existiert und
            // darin eine Datei "winutils.exe" liegt.
            final java.io.File winUtilsPath = new java.io.File(hDir + "bin\\winutils.exe");
            if (winUtilsPath.exists()) {

                log.info("winutils.exe in " + winUtilsPath.getAbsolutePath() + " gefunden, Workaround nicht nötig.");
                return;
            } else {
                log.warn("hadoop.home.dir ist zwar gesetzt, jedoch wurden keine Binaries gefunden.");
            }
        }

        // Existieren die Binaries denn?
        final java.io.File binaries = new java.io.File("E:\\hadoop-2.2.0\\bin\\winutils.exe");
        if (binaries.exists()) {

            // ... dann verlinke sie
            System.getProperties().put("hadoop.home.dir", "E:\\hadoop-2.2.0\\");
        } else {

            // Existieren sie nicht, simuliere sie
            log.info("Wende WinUtils-Workaround an...");

            // Erstelle eine Datei im aktuellen Ordner (in unserem Fall dem
            // Root-Ordner
            // von Eclipse)
            final java.io.File workaround = new java.io.File("E:\\hadoop-2.2.0\\");

            // Erstelle die Systemeigenschaft hadoop.home.dir und setze deren
            // Wert
            // auf den eben erstellten Ordner.
            System.getProperties().put("hadoop.home.dir", workaround.getAbsolutePath());

            // Erstelle in diesem Ordner den Ordner "bin" ...
            new java.io.File("./bin").mkdirs();
            try {

                // ... und darin eine leere Datei "winutils.exe"
                new java.io.File("E:\\hadoop-2.2.0\\bin\\winutils.exe").createNewFile();
            } catch (final java.io.IOException e) {
                log.error("Fehler beim Erstellen der Datei './bin/winutils.exe'.");
                e.printStackTrace();
            }
        }
    }
}
