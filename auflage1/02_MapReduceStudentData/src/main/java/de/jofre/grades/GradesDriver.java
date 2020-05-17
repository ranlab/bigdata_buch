package de.jofre.grades;

/**
 * Einstiegspunkt in den Job
 *
 * @author promyx
 *
 */
public class GradesDriver extends org.apache.hadoop.conf.Configured implements org.apache.hadoop.util.Tool {

    private final static java.util.logging.Logger log = java.util.logging.Logger.getLogger(GradesDriver.class.getName());

    public static void main(final java.lang.String[] args) {
        int res = 1; // Wenn 1 nicht verändert wird, endet der Job nicht korrekt
        try {
            res = org.apache.hadoop.util.ToolRunner.run(new org.apache.hadoop.conf.Configuration(), new GradesDriver(), args);
        } catch (final java.lang.Exception e) {
            log.log(java.util.logging.Level.SEVERE, "Fehler beim Ausführen des Jobs!");
            e.printStackTrace();
        }
        java.lang.System.exit(res);
    }

    /*
     * Konstruiert und startet den Job.
     *
     * @see org.apache.hadoop.util.Tool#run(java.lang.String[])
     */
    @Override
    public int run(final java.lang.String[] args) {

        log.log(java.util.logging.Level.INFO, "Starte Map-Reduce-Job 'GradesDriver'... ");

        // Wenn Configured erweitert wird, kann die bestehende Konfiguration
        // per getConf abgerufen werden.
        final org.apache.hadoop.conf.Configuration conf = this.getConf();
        org.apache.hadoop.mapreduce.Job job = null;

        try {
            job = org.apache.hadoop.mapreduce.Job.getInstance(conf);
        } catch (final java.io.IOException e1) {
            log.log(java.util.logging.Level.SEVERE, "Fehler bei Instanziierung des Jobs!");
            e1.printStackTrace();
        }

        // Hadoop soll ein verfügbares JAR verwenden, das die Klasse
        // GradesDriver enthält.
        job.setJarByClass(GradesDriver.class);

        // Mapper- und Reducer-Klasse werden festgelegt, unsere eigenen Klassen aus dem Package
        job.setMapperClass(GradesMapper.class);
        job.setReducerClass(GradesReducer.class);

        // Ausgabetypen werden festgelegt
        job.setOutputKeyClass(org.apache.hadoop.io.IntWritable.class);
        job.setOutputValueClass(org.apache.hadoop.io.FloatWritable.class);
        job.setMapOutputKeyClass(org.apache.hadoop.io.IntWritable.class);
        job.setMapOutputValueClass(org.apache.hadoop.io.IntWritable.class);
        job.setInputFormatClass(org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat.class); // Eingabe: Parst Schlüssel-Wert-Paare aus einer Zeile (Seite 66)
        job.setOutputFormatClass(org.apache.hadoop.mapreduce.lib.output.TextOutputFormat.class); // Ausgabe: eine Zeile

        // Der Pfad, aus dem Hadoop die Eingabedateien list, wird als erstes Argument
        // beim Starten des JARs übergeben.
        try {
            org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath(job, new org.apache.hadoop.fs.Path(args[0]));
        } catch (final IllegalArgumentException e) {
            log.log(java.util.logging.Level.SEVERE, "Fehler (Argument) beim Setzen des Eingabepfades!");
            e.printStackTrace();
        } catch (final java.io.IOException e) {
            log.log(java.util.logging.Level.SEVERE, "Fehler (IO) beim Setzen des Eingabepfades!");
            e.printStackTrace();
        }

        // Der Ausgabeordner wird als zweites Argument übergeben
        org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job, new org.apache.hadoop.fs.Path(args[1]));
        boolean result = false;

        try {
            // Führe den Job aus und warte, bis er beendet wurde
            result = job.waitForCompletion(true);
        } catch (final java.lang.ClassNotFoundException e) {
            log.log(java.util.logging.Level.SEVERE, "Fehler (ClassNotFound) beim Ausführen des Jobs!");
            e.printStackTrace();
        } catch (final java.io.IOException e) {
            log.log(java.util.logging.Level.SEVERE, "Fehler (IOException) beim Ausführen des Jobs!");
            e.printStackTrace();
        } catch (final java.lang.InterruptedException e) {
            log.log(java.util.logging.Level.SEVERE, "Fehler (Interrupted) beim Ausführen des Jobs!");
            e.printStackTrace();
        }

        log.log(java.util.logging.Level.INFO, "Fertig!");
        return result ? 0 : 1;
    }
}
