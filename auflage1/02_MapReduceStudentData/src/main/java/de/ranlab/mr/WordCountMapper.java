package de.ranlab.mr;

/**
 * Splittet den eingabetext auf einzelne Worte
 * @see http://torlone.dia.uniroma3.it/bigdata/E1-Hadoop.pdf
 * @author promyx
 */
public class WordCountMapper extends
    org.apache.hadoop.mapreduce.Mapper<org.apache.hadoop.io.LongWritable, org.apache.hadoop.io.Text, org.apache.hadoop.io.Text, org.apache.hadoop.io.IntWritable> {
    private static final org.apache.hadoop.io.IntWritable one = new org.apache.hadoop.io.IntWritable(1);
    private final org.apache.hadoop.io.Text word = new org.apache.hadoop.io.Text();

    @Override
    public void map(final org.apache.hadoop.io.LongWritable key, final org.apache.hadoop.io.Text value, final Context context)
        throws java.io.IOException,
            InterruptedException {
        final java.lang.String line = value.toString();
        final java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(line);
        while (tokenizer.hasMoreTokens()) {
            this.word.set(tokenizer.nextToken());
            context.write(this.word, one);
        }
    }
}
