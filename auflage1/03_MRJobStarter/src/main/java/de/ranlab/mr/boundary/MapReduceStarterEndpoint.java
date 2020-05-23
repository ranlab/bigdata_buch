package de.ranlab.mr.boundary;

/**
 * Startet einen Map-Reduce-Job
 * Die Endpunkte sind mit OpenAPI dokumentiert und können mit "localhost:8080/openapi" abgefragt werden.
 *
 * @see https://download.eclipse.org/microprofile/microprofile-open-api-1.1.2/microprofile-openapi-spec.html#_annotations
 * @see https://stackoverflow.com/questions/34581522/specify-username-and-group-in-hadoop-java-api
 * @author randrae
 */
@javax.ws.rs.Path("/mr")
public class MapReduceStarterEndpoint extends de.ranlab.mr.boundary.AbstractEndpoint {

    // Verzeichnis, in das die Ausgabe des Jobs geschrieben wird (im HDFS)
    private final static java.lang.String MR_OUTPUT_DIR = "/hdfs/mr2/output";

    @javax.inject.Inject
    private de.ranlab.mr.application.Logger logger;

    /**
     * Startet den Map-Reduce-Job.
     * Alles statisch verdrahtet.
     * @return
     */
    @org.eclipse.microprofile.openapi.annotations.Operation(summary = "StartJob", description = "Startet den Map-Reduce-Job über die Hadoop API")
    @org.eclipse.microprofile.openapi.annotations.responses.APIResponse(description = "Kontosystem Status", //
        responseCode = "200", content = { @org.eclipse.microprofile.openapi.annotations.media.Content(mediaType = "application/json", //
            schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(implementation = de.ranlab.mr.api.JobStartResponse.class)) })
    @javax.ws.rs.GET
    @javax.ws.rs.Path("/start")
    @javax.ws.rs.Produces({ javax.ws.rs.core.MediaType.APPLICATION_JSON, javax.ws.rs.core.MediaType.APPLICATION_XML })
    public javax.ws.rs.core.Response startJob() {

        final de.ranlab.mr.api.JobStartResponse response = new de.ranlab.mr.api.JobStartResponse();

        // Lass den Job Remote unter dem Benutzer "hduser" laufen
        try {

            final org.apache.hadoop.conf.Configuration conf = MapReduceStarterEndpoint.this.getConfiguration();
            conf.set("hadoop.job.ugi", "hduser");
            MapReduceStarterEndpoint.this.logger.info("Konfiguration initialisiert.");

            // Initialisieren des FileSystem-Zugriffs.
            final org.apache.hadoop.fs.FileSystem fs = org.apache.hadoop.fs.FileSystem.get(conf);
            MapReduceStarterEndpoint.this.logger.info("FileSystem-Zugriff initialisiert.");

            // Löschen des Ausgabeverzeichnisses
            fs.delete(new org.apache.hadoop.fs.Path(de.jofre.helper.HadoopProperties.get("hdfs_address") + MR_OUTPUT_DIR), true);
            MapReduceStarterEndpoint.this.logger.info("Ausgabeverzeichnis gelöscht.");

            final org.apache.hadoop.mapreduce.Job job = org.apache.hadoop.mapreduce.Job.getInstance(conf);
            MapReduceStarterEndpoint.this.logger.info("Job definiert.");

            // Hadoop soll ein verfügbares JAR verwenden, das die Klasse
            // GradesDriver enthält.
            job.setJarByClass(de.jofre.grades.GradesDriver.class);

            // Mapper- und Reducer-Klasse werden festgelegt
            job.setMapperClass(de.jofre.grades.GradesMapper.class);
            job.setReducerClass(de.jofre.grades.GradesReducer.class);

            // Ausgabetypen werden festgelegt
            job.setOutputKeyClass(org.apache.hadoop.io.IntWritable.class);
            job.setOutputValueClass(org.apache.hadoop.io.FloatWritable.class);
            job.setMapOutputKeyClass(org.apache.hadoop.io.IntWritable.class);
            job.setMapOutputValueClass(org.apache.hadoop.io.IntWritable.class);
            job.setInputFormatClass(org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat.class);
            job.setOutputFormatClass(org.apache.hadoop.mapreduce.lib.output.TextOutputFormat.class);
            MapReduceStarterEndpoint.this.logger.info("Klassen für den Job gesetzt.");

            // Eingabe-Pfad setzen wir statisch im Code
            org.apache.hadoop.mapreduce.lib.input.FileInputFormat
                .addInputPath(job, new org.apache.hadoop.fs.Path(de.jofre.helper.HadoopProperties.get("hdfs_address") + "/hdfs/mr1/input"));

            MapReduceStarterEndpoint.this.logger
                .info("Eingabepfad gesetzt auf: " + de.jofre.helper.HadoopProperties.get("hdfs_address") + "/hdfs/mr1/input");

            // Auch der Ausgabe-Pfad wird statisch gesetzt
            org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
                .setOutputPath(job,
                    new org.apache.hadoop.fs.Path(
                        java.lang.String.format("%s%s", de.jofre.helper.HadoopProperties.get("hdfs_address"), MR_OUTPUT_DIR)));
            MapReduceStarterEndpoint.this.logger
                .info(java.lang.String
                    .format("Ausgabepfad gesetzt auf: %s%s", de.jofre.helper.HadoopProperties.get("hdfs_address"), MR_OUTPUT_DIR));

            // Führe den Job aus und warte, bis er beendet wurde
            final boolean result = job.waitForCompletion(true);

            response.getHeader().setRc(0);
            response.getHeader().setRcText(java.lang.String.format("Job-Result %b", result));

            return javax.ws.rs.core.Response.ok(response).build();
        } catch (final java.io.IOException ioex) {
            response.getHeader().setRc(-1);
            response.getHeader().setRcText(ioex.getLocalizedMessage());

            return javax.ws.rs.core.Response.serverError().entity(response).build();
        } catch (final java.lang.InterruptedException iex) {
            response.getHeader().setRc(-2);
            response.getHeader().setRcText(iex.getLocalizedMessage());

            return javax.ws.rs.core.Response.serverError().entity(response).build();
        } catch (final java.lang.ClassNotFoundException clex) {
            response.getHeader().setRc(-3);
            response.getHeader().setRcText(clex.getLocalizedMessage());

            return javax.ws.rs.core.Response.serverError().entity(response).build();
        }
    }

    /**
     * Startet den Map-Reduce-Job.
     * Alles statisch verdrahtet.
     * @return
     */
    @org.eclipse.microprofile.openapi.annotations.Operation(summary = "StartJob", description = "Startet den Map-Reduce-Job über die Hadoop API")
    @org.eclipse.microprofile.openapi.annotations.responses.APIResponse(description = "Kontosystem Status", //
        responseCode = "200", content = { @org.eclipse.microprofile.openapi.annotations.media.Content(mediaType = "application/json", //
            schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(implementation = de.ranlab.mr.api.JobStartResponse.class)) })
    @javax.ws.rs.GET
    @javax.ws.rs.Path("/startHduser")
    @javax.ws.rs.Produces({ javax.ws.rs.core.MediaType.APPLICATION_JSON, javax.ws.rs.core.MediaType.APPLICATION_XML })
    public javax.ws.rs.core.Response startJobWithHduser() {

        final de.ranlab.mr.api.JobStartResponse response = new de.ranlab.mr.api.JobStartResponse();

        // Lass den Job Remote unter dem Benutzer "hduser" laufen
        try {

            final org.apache.hadoop.security.UserGroupInformation ugi = org.apache.hadoop.security.UserGroupInformation
                .createRemoteUser("hduser");
            final java.lang.Boolean jobResult = ugi.doAs(new java.security.PrivilegedExceptionAction<java.lang.Boolean>() {

                @Override
                public java.lang.Boolean run()
                    throws java.lang.Exception {

                    final org.apache.hadoop.conf.Configuration conf = MapReduceStarterEndpoint.this.getConfiguration();
                    conf.set("hadoop.job.ugi", "hduser");
                    MapReduceStarterEndpoint.this.logger.info("Konfiguration initialisiert.");

                    // Initialisieren des FileSystem-Zugriffs.
                    final org.apache.hadoop.fs.FileSystem fs = org.apache.hadoop.fs.FileSystem.get(conf);
                    MapReduceStarterEndpoint.this.logger.info("FileSystem-Zugriff initialisiert.");

                    // Löschen des Ausgabeverzeichnisses
                    fs.delete(new org.apache.hadoop.fs.Path(de.jofre.helper.HadoopProperties.get("hdfs_address") + MR_OUTPUT_DIR), true);
                    MapReduceStarterEndpoint.this.logger.info("Ausgabeverzeichnis gelöscht.");

                    final org.apache.hadoop.mapreduce.Job job = org.apache.hadoop.mapreduce.Job.getInstance(conf);
                    MapReduceStarterEndpoint.this.logger.info("Job definiert.");

                    // Hadoop soll ein verfügbares JAR verwenden, das die Klasse
                    // GradesDriver enthält.
                    job.setJarByClass(de.jofre.grades.GradesDriver.class);

                    // Mapper- und Reducer-Klasse werden festgelegt
                    job.setMapperClass(de.jofre.grades.GradesMapper.class);
                    job.setReducerClass(de.jofre.grades.GradesReducer.class);

                    // Ausgabetypen werden festgelegt
                    job.setOutputKeyClass(org.apache.hadoop.io.IntWritable.class);
                    job.setOutputValueClass(org.apache.hadoop.io.FloatWritable.class);
                    job.setMapOutputKeyClass(org.apache.hadoop.io.IntWritable.class);
                    job.setMapOutputValueClass(org.apache.hadoop.io.IntWritable.class);
                    job.setInputFormatClass(org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat.class);
                    job.setOutputFormatClass(org.apache.hadoop.mapreduce.lib.output.TextOutputFormat.class);
                    MapReduceStarterEndpoint.this.logger.info("Klassen für den Job gesetzt.");

                    // Eingabe-Pfad setzen wir statisch im Code
                    org.apache.hadoop.mapreduce.lib.input.FileInputFormat
                        .addInputPath(job,
                            new org.apache.hadoop.fs.Path(de.jofre.helper.HadoopProperties.get("hdfs_address") + "/hdfs/mr1/input"));

                    MapReduceStarterEndpoint.this.logger
                        .info("Eingabepfad gesetzt auf: " + de.jofre.helper.HadoopProperties.get("hdfs_address") + "/hdfs/mr1/input");

                    // Auch der Ausgabe-Pfad wird statisch gesetzt
                    org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
                        .setOutputPath(job,
                            new org.apache.hadoop.fs.Path(
                                java.lang.String.format("%s%s", de.jofre.helper.HadoopProperties.get("hdfs_address"), MR_OUTPUT_DIR)));
                    MapReduceStarterEndpoint.this.logger
                        .info(java.lang.String
                            .format("Ausgabepfad gesetzt auf: %s%s", de.jofre.helper.HadoopProperties.get("hdfs_address"), MR_OUTPUT_DIR));

                    // Führe den Job aus und warte, bis er beendet wurde
                    final boolean result = job.waitForCompletion(true);

                    return result;
                }
            });

            response.getHeader().setRc(0);
            response.getHeader().setRcText(java.lang.String.format("Job-Result %b", jobResult));

            return javax.ws.rs.core.Response.ok(response).build();
        } catch (final java.io.IOException ioex) {
            response.getHeader().setRc(-1);
            response.getHeader().setRcText(ioex.getLocalizedMessage());

            return javax.ws.rs.core.Response.serverError().entity(response).build();
        } catch (final java.lang.InterruptedException iex) {
            response.getHeader().setRc(-2);
            response.getHeader().setRcText(iex.getLocalizedMessage());

            return javax.ws.rs.core.Response.serverError().entity(response).build();
        }
    }

    private org.apache.hadoop.conf.Configuration getConfiguration() {
        final org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set("yarn.resourcemanager.scheduler.address", de.jofre.helper.HadoopProperties.get("scheduler_address"));
        conf.set("yarn.resourcemanager.address", de.jofre.helper.HadoopProperties.get("resourcemgr_address"));
        conf.set("yarn.resourcemanager.resource-tracker.address", de.jofre.helper.HadoopProperties.get("task_tracker_address"));
        conf.set("fs.defaultFS", de.jofre.helper.HadoopProperties.get("hdfs_address"));
        //
        //  conf.set("mapreduce.framework.name", "yarn");

        return conf;
    }
}
