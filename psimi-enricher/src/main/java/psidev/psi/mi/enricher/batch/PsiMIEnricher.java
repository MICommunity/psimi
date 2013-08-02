package psidev.psi.mi.enricher.batch;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The psi mi enricher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/08/13</pre>
 */

public class PsiMIEnricher {

    private static final Log log = LogFactory.getLog(PsiMIEnricher.class);

    @Resource(name = "batchJobLauncher")
    private JobLauncher jobLauncher;

    @Resource(name = "jobRepository")
    private JobRepository jobRepository;

    @Resource(name = "jobOperator")
    private JobOperator jobOperator;

    @Autowired
    private ApplicationContext applicationContext;

    private String indexingId;

    public PsiMIEnricher() {
    }

    public static void main(String[] args) throws JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobRestartException, JobExecutionAlreadyRunningException, NoSuchJobExecutionException, NoSuchJobException, NoSuchJobInstanceException {

        // loads the spring context defining beans and jobs
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"/META-INF/enricher-spring.xml"});

        PsiMIEnricher rm = (PsiMIEnricher)
                context.getBean("psiMiEnricher");

        if ( args.length == 1){
            rm.setIndexingId(args[0]);
            rm.resumeEnrichment();
        }
        else {
            rm.startEnrichment();
        }
    }

    public void resumeEnrichment() throws JobInstanceAlreadyCompleteException, JobParametersInvalidException, NoSuchJobExecutionException, JobRestartException, NoSuchJobException, NoSuchJobInstanceException {
        resumeJob("interactionEnricherJob");
    }

    public void resumeJob(String jobName) throws JobInstanceAlreadyCompleteException, JobParametersInvalidException, NoSuchJobExecutionException, JobRestartException, NoSuchJobException, NoSuchJobInstanceException {
        Long executionId = findJobId(indexingId, jobName);

        if (executionId == null) {
            throw new IllegalStateException("Indexing Id not found: "+indexingId);
        }

        jobOperator.restart(executionId);
    }

    private Long findJobId(String indexingId, String jobName) throws NoSuchJobException, NoSuchJobExecutionException, NoSuchJobInstanceException {
        final List<Long> jobIds = jobOperator.getJobInstances(jobName, 0, Integer.MAX_VALUE);

        for (Long jobId : jobIds) {
            for (Long executionId : jobOperator.getExecutions(jobId)) {
                final String params = jobOperator.getParameters(executionId);

                if (params.contains(indexingId)) {
                    return executionId;
                }
            }
        }

        return null;
    }

    public void startEnrichment() throws JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobRestartException, JobExecutionAlreadyRunningException {
        startJob("interactionEnricherJob");
    }

    public void startJob(String jobName) throws JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobRestartException, JobExecutionAlreadyRunningException {
        String indexingId = "psi_enricher_"+System.currentTimeMillis();
        setIndexingId(indexingId);

        if (log.isInfoEnabled()) log.info("Starting enrichment : "+indexingId);

        FileWriter indexingWriter = null;
        try {
            String fileName = "target/"+jobName+"_"+System.currentTimeMillis();
            if (log.isInfoEnabled()) log.info("The enricher id is stored in : "+fileName +". This enricher id is necessary for restarting an enrichment job from where it failed.");

            indexingWriter = new FileWriter(fileName);

            indexingWriter.write(indexingId);
        } catch (IOException e) {
            log.error("Impossible to create the file containing the enricher id for this job.", e);
        }
        finally {
            try {
                indexingWriter.close();
            } catch (IOException e) {
                log.error("Impossible to close the file writer writing the enricher id for this job.", e);
            }
        }

        JobExecution execution = runJob(jobName, indexingId);

        while (execution.isRunning()){
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (execution.getExitStatus().equals(ExitStatus.FAILED)){
            log.error("Indexing job has failed.");
            for (Throwable e : execution.getAllFailureExceptions()){
                log.error(ExceptionUtils.getFullStackTrace(e));
            }
        }
    }

    protected JobExecution runJob(String jobName, String indexingId) throws JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobRestartException, JobExecutionAlreadyRunningException {
        if (log.isInfoEnabled()) log.info("Starting job: "+jobName);
        Job job = (Job) applicationContext.getBean(jobName);

        JobParametersBuilder jobParamBuilder = new JobParametersBuilder();
        jobParamBuilder.addString("indexingId", indexingId).toJobParameters();

        log.info("starting job " + indexingId);

        return jobLauncher.run(job, jobParamBuilder.toJobParameters());
    }

    public void setIndexingId(String indexingId) {
        this.indexingId = indexingId;
    }

    public String getIndexingId() {
        return indexingId;
    }
}
