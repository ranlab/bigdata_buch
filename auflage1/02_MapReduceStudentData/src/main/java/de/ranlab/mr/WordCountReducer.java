package de.ranlab.mr;

/**
 * Zählt die Worte
 * @author promyx
 */
public class WordCountReducer extends
    org.apache.hadoop.mapreduce.Reducer<org.apache.hadoop.io.Text, org.apache.hadoop.io.IntWritable, org.apache.hadoop.io.Text, org.apache.hadoop.io.IntWritable> {

    @Override
    public void reduce(final org.apache.hadoop.io.Text key, final Iterable<org.apache.hadoop.io.IntWritable> values, final Context context)
        throws java.io.IOException,
            InterruptedException {
        int sum = 0;
        for (final org.apache.hadoop.io.IntWritable value : values) {
            sum += value.get();
        }
        context.write(key, new org.apache.hadoop.io.IntWritable(sum));
    }
}
