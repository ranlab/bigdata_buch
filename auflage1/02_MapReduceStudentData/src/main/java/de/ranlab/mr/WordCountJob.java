package de.ranlab.mr;

/**
 * Startet einen Map-Reduce von der Kommadozeile
 *
 * hdfs dfs -mkdir output
 * hdfs dfs -put /example_data/words.txt input
 * hadoop jar /example_jar/Example1.jar de/ranlab/mr/WordCountJob input/words.txt output/result_words
 *
 * @author promyx
 */
public class WordCountJob {

    private final static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
        .getLogger(de.ranlab.mr.WordCountJob.class);

    public static void main(final java.lang.String[] args)
        throws java.lang.Exception {
        final org.apache.hadoop.mapreduce.Job job = org.apache.hadoop.mapreduce.Job
            .getInstance(new org.apache.hadoop.conf.Configuration(), "Word Count");
        job.setJarByClass(WordCountJob.class);

        job.setMapperClass(WordCountMapper.class);
        // combiner use
        // job.setCombinerClass(WordCountReducer.class);
        job.setReducerClass(WordCountReducer.class);

        org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath(job, new org.apache.hadoop.fs.Path(args[0]));
        org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job, new org.apache.hadoop.fs.Path(args[1]));

        job.setOutputKeyClass(org.apache.hadoop.io.Text.class);
        job.setOutputValueClass(org.apache.hadoop.io.IntWritable.class);
        job.waitForCompletion(true);
    }
}
