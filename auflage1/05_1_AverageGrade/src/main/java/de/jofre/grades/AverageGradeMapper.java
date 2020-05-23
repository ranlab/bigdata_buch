package de.jofre.grades;

/**
 * Mapper sammelt alle Namen und Noten ein (Seite 102)
 * @author promyx
 */
// Eingabe-Key, Eingabe-Wert, Ausgabe-Key, Ausgabe-Wert
public class AverageGradeMapper extends
    org.apache.hadoop.mapreduce.Mapper<org.apache.hadoop.io.Text, org.apache.hadoop.io.Text, org.apache.hadoop.io.Text, org.apache.hadoop.io.FloatWritable> {

    @Override
    public void map(final org.apache.hadoop.io.Text key, final org.apache.hadoop.io.Text value, final Context context)
        throws java.io.IOException,
            java.lang.InterruptedException {

        // Formatieren der Gleitkommazahlen (Ersetzen der Kommata durch Punkte)
        final java.lang.String pointFloat = value.toString().replace(',', '.');
        final org.apache.hadoop.io.FloatWritable floatValue = new org.apache.hadoop.io.FloatWritable(
            java.lang.Float.parseFloat(pointFloat));

        // Hier müssen wir einfach nur die vorhandenen Daten in den Mapper einlesen
        context.write(key, floatValue);
    }
}
