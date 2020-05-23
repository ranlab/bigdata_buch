package de.ranlab.test;

/**
 * Junit-Test für die Schnittstelle.
 *
 * @see https://junit.org/junit5/docs/current/user-guide/
 * @see https://docs.marklogic.com/guide/mapreduce/hadoop
 * @author promyx
 */
@org.junit.jupiter.api.TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class MapReduceIT {
    private final static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(MapReduceIT.class.getName());

    // Verzeichnis, in das die Ausgabe des Jobs geschrieben wird (im HDFS)
    private final static java.lang.String MR_OUTPUT_DIR = "/hdfs/mr2/output";

    // Beinhaltet alle Eigenschaften der Hadoop-Konfiguration
    private static org.apache.hadoop.conf.Configuration conf = null;

    @org.junit.jupiter.api.BeforeAll
    static void setup() {
        log.info("Initialisierung Environment");

        // Setze den Hadoop-User
        java.lang.System.setProperty("HADOOP_USER_NAME", de.jofre.helper.HadoopProperties.get("hadoop_user"));

        // Erstelle die Konfiguration durch setzen der Hadoop-Configuration
        conf = new org.apache.hadoop.conf.Configuration();
        conf.set("yarn.resourcemanager.scheduler.address", de.jofre.helper.HadoopProperties.get("scheduler_address"));
        conf.set("yarn.resourcemanager.address", de.jofre.helper.HadoopProperties.get("resourcemgr_address"));
        conf.set("yarn.resourcemanager.resource-tracker.address", de.jofre.helper.HadoopProperties.get("task_tracker_address"));
        conf.set("fs.defaultFS", de.jofre.helper.HadoopProperties.get("hdfs_address"));
        //
        //  conf.set("mapreduce.framework.name", "yarn");
    }

    /**
     * Startet den Job
     */
    @org.junit.jupiter.api.DisplayName("Lösche das Ausgabeverzeichnis")
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(1)
    public void test001_loescheAusgabeverzeichnis() {
        boolean result = false;

        // Initialisieren des FileSystem-Zugriffs.
        org.apache.hadoop.fs.FileSystem fs = null;
        try {
            fs = org.apache.hadoop.fs.FileSystem.get(conf);
        } catch (final java.io.IOException e) {
            log.error("Fehler beim Initialisieren des Zugriffs auf das FileSystem.", e);
        }

        // Löschen des Ausgabeverzeichnisses
        try {

            // Das true besagt, dass die Ordner unter unserem Pfad rekursiv
            // gelöscht werden sollen.
            fs.delete(new org.apache.hadoop.fs.Path(de.jofre.helper.HadoopProperties.get("hdfs_address") + MR_OUTPUT_DIR), true);
            log.info("Verzeichnis gelöscht");
            result = true;
        } catch (final Exception e) {
            log.error("Fehler beim Löschen des Verzeichnisses '" + MR_OUTPUT_DIR + "'.", e);
        }

        org.junit.jupiter.api.Assertions.assertTrue(result);
    }

    /**
     * Startet den Job
     */
    @org.junit.jupiter.api.DisplayName("Startet den Job")
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(2)
    public void test002_startJob() {
        org.apache.hadoop.mapreduce.Job job = null;
        try {
            job = org.apache.hadoop.mapreduce.Job.getInstance(conf);
        } catch (final java.io.IOException e1) {
            log.info("Fehler beim Setzen der Job-Config.");
            e1.printStackTrace();
        }

        log.info("Job definiert.");
        this.debugConfig();

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

        log.info("Klassen für den Job gesetzt.");

        // Den Input-Pfad setzen wir diesmal im Code
        try {
            org.apache.hadoop.mapreduce.lib.input.FileInputFormat
                .addInputPath(job, new org.apache.hadoop.fs.Path(de.jofre.helper.HadoopProperties.get("hdfs_address") + "/hdfs/mr1/input"));
        } catch (final java.io.IOException e) {
            log.error("Fehler beim Setzen des Eingabepfades!" + e.getLocalizedMessage(), e);
        }

        log.info("Eingabepfad gesetzt auf: " + de.jofre.helper.HadoopProperties.get("hdfs_address") + "/hdfs/mr1/input");

        // Auch der Ausgabe-Pfad wird statisch gesetzt
        org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
            .setOutputPath(job,
                new org.apache.hadoop.fs.Path(
                    java.lang.String.format("%s%s", de.jofre.helper.HadoopProperties.get("hdfs_address"), MR_OUTPUT_DIR)));
        log
            .info(java.lang.String
                .format("Ausgabepfad gesetzt auf: %s%s", de.jofre.helper.HadoopProperties.get("hdfs_address"), MR_OUTPUT_DIR));

        boolean result = false;
        try {
            // Führe den Job aus und warte, bis er beendet wurde
            log.info("Führe Job aus...");
            result = job.waitForCompletion(true);
        } catch (final java.lang.Exception e) {
            log.error("Fehler beim Ausführen des Jobs!", e);
        }

        org.junit.jupiter.api.Assertions.assertTrue(result);
    }

    private void debugConfig() {
        final java.util.TreeMap<java.lang.String, java.lang.String> treeConfig = new java.util.TreeMap<>();
        for (final java.util.Map.Entry<java.lang.String, java.lang.String> entry : conf) {
            if (entry.getKey().contains("address") || entry.getKey().contains("mapreduce.framework.name")) {
                treeConfig.put(entry.getKey(), entry.getValue());
            }
        }
        for (final java.util.Map.Entry<java.lang.String, java.lang.String> entry : treeConfig.entrySet()) {
            log.info(java.lang.String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
    }
}
