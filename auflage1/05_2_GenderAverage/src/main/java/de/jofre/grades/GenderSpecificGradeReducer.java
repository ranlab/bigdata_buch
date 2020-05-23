package de.jofre.grades;

/**
 * Der Reducer wertet die oten nach Geschlecht aus.
 * @author promyx
 *
 */
// Eingabe-Key, Eingabe-Wert, Ausgabe-Key, Ausgabe-Wert
public class GenderSpecificGradeReducer extends
    org.apache.hadoop.mapreduce.Reducer<org.apache.hadoop.io.Text, org.apache.hadoop.io.FloatWritable, org.apache.hadoop.io.Text, org.apache.hadoop.io.FloatWritable> {

    private final static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
        .getLogger(GenderSpecificGradeReducer.class.getName());

    @Override
    protected void reduce(final org.apache.hadoop.io.Text key,
        final java.lang.Iterable<org.apache.hadoop.io.FloatWritable> values,
        final Context context)
        throws java.io.IOException,
            java.lang.InterruptedException {

        // Summiere alle Noten eines Geschlechts auf...
        float sum = 0;
        float count = 0;
        for (final org.apache.hadoop.io.FloatWritable val : values) {
            sum += val.get();
            count += 1;
        }

        // Und bilde den Durchschnitt
        final float result = sum / count;

        log.info("Schreibe Geschlecht: " + key + " Note: " + result);

        // Schreibe den Durschnitt für jedes Geschlecht auf
        context.write(key, new org.apache.hadoop.io.FloatWritable(result));

    }
}
