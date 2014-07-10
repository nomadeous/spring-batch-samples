package prototypes.batches.chunks.jobs;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class ParallelChunkingJobIT {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ParallelChunkingJobIT.class);

    @Resource(name = "jobLauncher")
    private JobLauncher jobLauncher;

    @Resource(name = "chunksJob")
    private Job chunksJob;

    @Test
    public void testLaunch() {
        // Prepare parameters for the job
        Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
        parameters.put("currentDate", new JobParameter(Calendar.getInstance()
                .getTime()));

        // Launch the job
        try {
            JobParameters jobParameters = new JobParameters(parameters);
            LOGGER.debug("Launch Batch chunksJob for test");

            JobExecution jobExecution = jobLauncher.run(
                    chunksJob, jobParameters);

            Assert.assertTrue("Job execution status should be " + BatchStatus.COMPLETED,
                    BatchStatus.COMPLETED.equals(jobExecution.getStatus()));

        } catch (JobExecutionAlreadyRunningException e) {
            LOGGER.error("Job Already Running", e);
        } catch (JobRestartException e) {
            LOGGER.error("Job Restart", e);
        } catch (JobInstanceAlreadyCompleteException e) {
            LOGGER.error("Job Already Complete", e);
        } catch (JobParametersInvalidException e) {
            LOGGER.error("Job Invalid Parameters", e);
        }
    }
}
