package de.jofre.grades;

/**
 * Driver übernimmt die Koordination des Jobs (Seite 101)
 * @author promyx
 *
 */
public class AverageGradeDriver extends org.apache.hadoop.conf.Configured implements org.apache.hadoop.util.Tool {

    private final static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
        .getLogger(AverageGradeDriver.class.getName());

    /**
     * Startpunkt.
     * @param args
     */
    public static void main(final java.lang.String[] args) {
        int res = 1; // Wenn 1 nicht verändert wird, endet der Job nicht korrekt
        try {
            res = org.apache.hadoop.util.ToolRunner.run(new org.apache.hadoop.conf.Configuration(), new AverageGradeDriver(), args);
        } catch (final java.lang.Exception e) {
            log.error("Fehler beim Ausführen des Jobs!");
            e.printStackTrace();
        }
        java.lang.System.exit(res);
    }

    public int run(final java.lang.String[] args)
        throws java.lang.Exception {

        log.info("Starte Map-Reduce-Job 'GradesDriver'... ");

        // Wenn Configured erweitert wird, kann die bestehende Konfiguration
        // per getConf abgerufen werden.
        final org.apache.hadoop.conf.Configuration conf = this.getConf();
        org.apache.hadoop.mapreduce.Job job = null;

        try {
            job = org.apache.hadoop.mapreduce.Job.getInstance(conf);
        } catch (final java.io.IOException e1) {
            log.error("Fehler bei Instanziierung des Jobs!");
            e1.printStackTrace();
        }

        // Hadoop soll ein verfügbares JAR verwenden, das die Klasse
        // GradesDriver enthält.
        job.setJarByClass(AverageGradeDriver.class);

        // Mapper- und Reducer-Klasse werden festgelegt
        job.setMapperClass(AverageGradeMapper.class);
        job.setReducerClass(AverageGradeReducer.class);

        // Ausgabetypen werden festgelegt
        job.setOutputKeyClass(org.apache.hadoop.io.Text.class);
        job.setOutputValueClass(org.apache.hadoop.io.FloatWritable.class);
        job.setMapOutputKeyClass(org.apache.hadoop.io.Text.class);
        job.setMapOutputValueClass(org.apache.hadoop.io.FloatWritable.class);
        job.setInputFormatClass(org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat.class);
        job.setOutputFormatClass(org.apache.hadoop.mapreduce.lib.output.TextOutputFormat.class);

        // Der Pfad, aus dem Hadoop die Eingabedateien list, wird als erstes Argument
        // beim Starten des JARs übergeben.
        try {
            org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath(job, new org.apache.hadoop.fs.Path(args[0]));
        } catch (final java.lang.Exception e) {
            log.error("Fehler beim Setzen des Eingabepfades!");
            e.printStackTrace();
        }

        // Der Ausgabeordner wird als zweites Argument übergeben
        org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job, new org.apache.hadoop.fs.Path(args[1]));
        boolean result = false;

        try {
            // Führe den Job aus und warte, bis er beendet wurde
            result = job.waitForCompletion(true);
        } catch (final java.lang.Exception e) {
            log.error("Fehler beim Ausführen des Jobs!");
            e.printStackTrace();
        }

        log.info("Fertig!");
        return result ? 0 : 1;
    }
}
