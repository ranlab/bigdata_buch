package de.jofre.grades;

/**
 * Der Mapper sammelt die Noten gemäß des Geschlechts des Studenten.
 * @author promyx
 */
// Eingabe-Key, Eingabe-Wert, Ausgabe-Key, Ausgabe-Wert
public class GenderSpecificGradeMapper extends
    org.apache.hadoop.mapreduce.Mapper<org.apache.hadoop.io.Text, org.apache.hadoop.io.Text, org.apache.hadoop.io.Text, org.apache.hadoop.io.FloatWritable> {

    @Override
    public void map(final org.apache.hadoop.io.Text key, final org.apache.hadoop.io.Text value, final Context context)
        throws java.io.IOException,
            java.lang.InterruptedException {

        final org.apache.hadoop.io.Text male = new org.apache.hadoop.io.Text("maennlich");
        final org.apache.hadoop.io.Text female = new org.apache.hadoop.io.Text("weiblich");

        // Extrahiere den Vornamen
        final java.lang.String names[] = key.toString().split(" ");

        // Ist der Name in zwei Teile zerlegbar?
        if (names.length > 0) {

            final java.lang.String pointFloat = value.toString(); //.replace(',','.');
            final org.apache.hadoop.io.FloatWritable floatValue = new org.apache.hadoop.io.FloatWritable(
                java.lang.Float.parseFloat(pointFloat));

            // Ist es ein männlicher Vorname?
            if (NamesByGender.getGender(names[0]).equals("male")) {
                context.write(male, floatValue);
            }

            // ... oder ein weiblicher?
            if (NamesByGender.getGender(names[0]).equals("female")) {
                context.write(female, floatValue);
            }

        }

    }
}
