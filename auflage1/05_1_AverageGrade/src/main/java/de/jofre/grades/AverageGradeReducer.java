package de.jofre.grades;

/**
 * Wertet für jeden Studierenden den Durchschnitt
 * @author promyx
 */
// Eingabe-Key, Eingabe-Wert, Ausgabe-Key, Ausgabe-Wert
public class AverageGradeReducer extends
    org.apache.hadoop.mapreduce.Reducer<org.apache.hadoop.io.Text, org.apache.hadoop.io.FloatWritable, org.apache.hadoop.io.Text, org.apache.hadoop.io.FloatWritable> {

    private final static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
        .getLogger(AverageGradeReducer.class.getName());

    @Override
    protected void reduce(final org.apache.hadoop.io.Text key,
        final java.lang.Iterable<org.apache.hadoop.io.FloatWritable> values,
        final Context context)
        throws java.io.IOException,
            java.lang.InterruptedException {

        // Summiere alle Noten eines Studierenden auf...
        float sum = 0;
        float count = 0;
        for (final org.apache.hadoop.io.FloatWritable val : values) {
            sum += val.get();
            count += 1;
        }

        // Und bilde den Durchschnitt
        final float result = sum / count;

        log.info("Name: " + key + " Note: " + result);

        // Schreibe den Durschnitt für den Studierenden in key
        context.write(key, new org.apache.hadoop.io.FloatWritable(result));
    }
}
