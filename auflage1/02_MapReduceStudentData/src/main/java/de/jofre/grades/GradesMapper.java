package de.jofre.grades;

/**
 * Mapper zur Notenauswertung.
 *
 * Verarbeitet die Textdatei zeilenweise und liest Jahr und Note in eine Hashtable aus
 * Schlüssel-Wert-Paaren (Eintrag pro Jahr Note).
 *
 * @see Big Data Buch, Seite 61
 *
 * @author promyx
 */
public class GradesMapper extends
    org.apache.hadoop.mapreduce.Mapper<org.apache.hadoop.io.Text, org.apache.hadoop.io.Text, org.apache.hadoop.io.IntWritable, org.apache.hadoop.io.IntWritable> {

    private final static java.util.logging.Logger log = java.util.logging.Logger.getLogger(GradesMapper.class.getName());

    private org.apache.hadoop.io.IntWritable year_int = null;
    private org.apache.hadoop.io.IntWritable grade_int = null;

    @Override
    public void map(final org.apache.hadoop.io.Text key, final org.apache.hadoop.io.Text value, final Context context)
        throws java.io.IOException,
            java.lang.InterruptedException {

        // Auslesen des Jahres und der Note aus einem String wie "2853972308201319"
        if (key.toString().length() == 16) {
            final java.lang.String year_str = key.toString().substring(10, 14);
            final java.lang.String grade_str = key.toString().substring(14, 16);

            this.year_int = new org.apache.hadoop.io.IntWritable(java.lang.Integer.parseInt(year_str));
            this.grade_int = new org.apache.hadoop.io.IntWritable(java.lang.Integer.parseInt(grade_str));

            // Sammeln der Ergebnisse
            context.write(this.year_int, this.grade_int);
        } else {
            log.log(java.util.logging.Level.INFO, "Ungültige Datensatzlänge entdeckt (" + key.toString().length() + ").");
        }
    }
}
