package de.jofre.grades;

/**
 * Reducer berechnet den Duschschnitt der Noten pro Jahr.
 *
 * @see Big Data Buch, Seite 62
 * @author promyx
 *
 */
public class GradesReducer extends
    org.apache.hadoop.mapreduce.Reducer<org.apache.hadoop.io.IntWritable, org.apache.hadoop.io.IntWritable, org.apache.hadoop.io.IntWritable, org.apache.hadoop.io.FloatWritable> {

    private final static java.util.logging.Logger log = java.util.logging.Logger.getLogger(GradesMapper.class.getName());

    @Override
    protected void reduce(final org.apache.hadoop.io.IntWritable key,
        final java.lang.Iterable<org.apache.hadoop.io.IntWritable> values,
        final Context context)
        throws java.io.IOException,
            java.lang.InterruptedException {

        // Summiere alle Noten eines Jahres auf...
        float sum = 0;
        float count = 0;
        for (final org.apache.hadoop.io.IntWritable val : values) {
            sum += val.get();
            count += 1;
        }

        // Und bilde den Durchschnitt
        float result = sum / count;

        // Am Ende soll eine Note nach dem Schema x,x herauskommen
        result /= 10;

        log.log(java.util.logging.Level.INFO, "Schreibe Jahr: " + key + " und Ergebnis: " + result);

        // Schreibe den Durschnitt für das Jahr in key
        context.write(key, new org.apache.hadoop.io.FloatWritable(result));

    }
}
