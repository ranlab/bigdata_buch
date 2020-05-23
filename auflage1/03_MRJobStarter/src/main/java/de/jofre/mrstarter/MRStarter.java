package de.jofre.mrstarter;

/**
 * Dient dem Starten des Jobs
 *
 * @see BigDataBuch, Seite 93
 * @author promyx
 */
public class MRStarter {

    private final static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(MRStarter.class.getName());

    // Verzeichnis, in das die Ausgabe des Jobs geschrieben wird (im HDFS)
    private final static java.lang.String MR_OUTPUT_DIR = "/hdfs/mr2/output";

    // Beinhaltet alle Eigenschaften der Hadoop-Konfiguration
    private org.apache.hadoop.conf.Configuration conf = null;

    /**
     *  Konstruktor wird bei jedem Erzeugen des Objekts aufgerufen
     */
    public MRStarter() {

        // Setze den Hadoop-User
        System.setProperty("HADOOP_USER_NAME", de.jofre.helper.HadoopProperties.get("hadoop_user"));

        // Gebe des Verzeichnis der Hadoop-Binaries bekannt
        de.jofre.helper.WinUtilsSolver.solveWinUtilError();

        // Erstelle die Konfiguration durch setzen der Hadoop-Configuration
        this.conf = new org.apache.hadoop.conf.Configuration();
        this.conf.set("yarn.resourcemanager.scheduler.address", de.jofre.helper.HadoopProperties.get("scheduler_address"));
        this.conf.set("yarn.resourcemanager.address", de.jofre.helper.HadoopProperties.get("resourcemgr_address"));
        this.conf.set("yarn.resourcemanager.resource-tracker.address", de.jofre.helper.HadoopProperties.get("task_tracker_address"));
        this.conf.set("fs.defaultFS", de.jofre.helper.HadoopProperties.get("hdfs_address"));
        //
        this.conf.set("mapreduce.framework.name", "yarn");
    }

    /**
     * Löscht das Ausgabeverzeichnis für den Map-Reduce-Job, falls es vorhanden ist.
     *
     * @return
     */
    public boolean deleteOutput() {

        // Initialisieren des FileSystem-Zugriffs.
        org.apache.hadoop.fs.FileSystem fs = null;
        try {
            fs = org.apache.hadoop.fs.FileSystem.get(this.conf);
        } catch (final java.io.IOException e) {
            log.info("Fehler beim Initialisieren des Zugriffs auf das FileSystem.");
            e.printStackTrace();
            return false;
        }

        // Löschen des Ausgabeverzeichnisses
        try {

            // Das true besagt, dass die Ordner unter unserem Pfad rekursiv
            // gelöscht werden sollen.
            fs.delete(new org.apache.hadoop.fs.Path(de.jofre.helper.HadoopProperties.get("hdfs_address") + MR_OUTPUT_DIR), true);
        } catch (final Exception e) {
            log.info("Fehler beim Löschen des Verzeichnisses '" + MR_OUTPUT_DIR + "'.");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Auslesen der Ergebnisdatei des Map-Reduce-Jobs
     * @return
     */
    public java.util.List<java.lang.String> readResult() {

        // Hier wird Hadoop die Datei ablegen
        final org.apache.hadoop.fs.Path pt = new org.apache.hadoop.fs.Path(
            de.jofre.helper.HadoopProperties.get("hdfs_address") + MR_OUTPUT_DIR + "/part-r-00000");
        final java.util.List<java.lang.String> result = new java.util.ArrayList<java.lang.String>();
        org.apache.hadoop.fs.FileSystem fs = null;
        java.io.BufferedReader br = null;
        try {

            // Zugriff auf das HDFS wird initialisiert
            fs = org.apache.hadoop.fs.FileSystem.get(this.conf);
            br = new java.io.BufferedReader(new java.io.InputStreamReader(fs.open(pt)));
            java.lang.String line;

            // Solange noch Zeilen in der ASCII-Datei zu finden sind,
            // lese diese aus und speicher sie in der Liste
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (final java.io.IOException e) {
            log.info("Fehler beim Lesen der Ausgabedatei.");
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (final java.io.IOException e) {
                    log.info("Fehler beim Schließen des Readers.");
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * Ausführen des Map-Reduce-Jobs
     * @param writer
     * @return
     */
    public boolean startJob(final javax.servlet.jsp.JspWriter writer) {

        boolean result = false;

        org.apache.hadoop.mapreduce.Job job = null;
        try {
            job = org.apache.hadoop.mapreduce.Job.getInstance(this.conf);
        } catch (final java.io.IOException e1) {
            log.info("Fehler beim Setzen der Job-Config.");
            e1.printStackTrace();
        }

        de.jofre.helper.JSPHelper.writeToJsp(writer, "Job-Konfiguration erstellt!<br>");

        final java.util.TreeMap<java.lang.String, java.lang.String> treeConfig = new java.util.TreeMap<>();
        for (final java.util.Map.Entry<java.lang.String, java.lang.String> entry : this.conf) {
            if (entry.getKey().contains("address") || entry.getKey().contains("mapreduce.framework.name")) {
                treeConfig.put(entry.getKey(), entry.getValue());
            }
        }
        for (final java.util.Map.Entry<java.lang.String, java.lang.String> entry : treeConfig.entrySet()) {
            de.jofre.helper.JSPHelper.writeToJsp(writer, java.lang.String.format("%s=%s <br/>", entry.getKey(), entry.getValue()));
        }

        // Hadoop soll ein verfügbares JAR verwenden, das die Klasse
        // GradesDriver enthält.
        job.setJarByClass(de.jofre.grades.GradesDriver.class);

        // Mapper- und Reducer-Klasse werden festgelegt
        job.setMapperClass(de.jofre.grades.GradesMapper.class);
        job.setReducerClass(de.jofre.grades.GradesReducer.class);

        // Ausgabetypen werden festgelegt
        job.setOutputKeyClass(org.apache.hadoop.io.IntWritable.class);
        job.setOutputValueClass(org.apache.hadoop.io.FloatWritable.class);
        job.setMapOutputKeyClass(org.apache.hadoop.io.IntWritable.class);
        job.setMapOutputValueClass(org.apache.hadoop.io.IntWritable.class);
        job.setInputFormatClass(org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat.class);
        job.setOutputFormatClass(org.apache.hadoop.mapreduce.lib.output.TextOutputFormat.class);

        de.jofre.helper.JSPHelper.writeToJsp(writer, "Klassen für Job gesetzt.<br/>");

        // Den Input-Pfad setzen wir diesmal im Code
        try {
            org.apache.hadoop.mapreduce.lib.input.FileInputFormat
                .addInputPath(job, new org.apache.hadoop.fs.Path(de.jofre.helper.HadoopProperties.get("hdfs_address") + "/hdfs/mr1/input"));
        } catch (final java.io.IOException e) {
            log.info("Fehler beim Setzen des Eingabepfades!");
            de.jofre.helper.JSPHelper
                .writeToJsp(writer, "<font color=\"#FF0000\">Fehler beim Ausführen des Jobs" + e.getStackTrace() + "</font><br>");
            e.printStackTrace();
        }

        de.jofre.helper.JSPHelper
            .writeToJsp(writer, "Eingabepfad gesetzt auf: " + de.jofre.helper.HadoopProperties.get("hdfs_address") + "/hdfs/mr1/input<br>");

        // Auch der Ausgabe-Pfad wird statisch gesetzt
        org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
            .setOutputPath(job, new org.apache.hadoop.fs.Path(de.jofre.helper.HadoopProperties.get("hdfs_address") + "/hdfs/mr2/output"));
        de.jofre.helper.JSPHelper
            .writeToJsp(writer,
                "Ausgabepfad gesetzt auf: " + de.jofre.helper.HadoopProperties.get("hdfs_address") + "/hdfs/mr2/output<br>");

        try {
            // Führe den Job aus und warte, bis er beendet wurde
            de.jofre.helper.JSPHelper.writeToJsp(writer, "Führe Job aus...<br/>");
            result = job.waitForCompletion(true);
        } catch (final Exception e) {
            log.info("Fehler beim Ausführen des Jobs!");
            de.jofre.helper.JSPHelper
                .writeToJsp(writer, "<font color=\"#FF0000\">Fehler beim Ausführen des Jobs" + e.getStackTrace() + "</font><br>");
            e.printStackTrace();
        }

        de.jofre.helper.JSPHelper.writeToJsp(writer, "<b>Fertig!</b><br><br>");
        log.info("Fertig!");

        de.jofre.helper.JSPHelper.writeToJsp(writer, "<b>Ergebnisse:</b><br>");
        final java.util.List<java.lang.String> results = this.readResult();

        for (final java.lang.String result2 : results) {
            de.jofre.helper.JSPHelper.writeToJsp(writer, result2 + "<br>");
        }
        return result;
    }
}
