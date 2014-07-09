package fr.erdf.nsge.sgel.prm.batch.test;

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


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml",
		"classpath:batch/spring-batch-test.xml" })
public class PocLectureZipParallelJobTestIT {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PocLectureZipParallelJobTestIT.class);

	@Resource(name = "jobLauncher")
	private JobLauncher jobLauncher;

	@Resource(name = "pocLectureZipParallelJob")
	private Job pocLectureZipParallelJob;

	@Test
	public void pocLectureZipJobTest() {
		// Initialisation des paramètres
		Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
		parameters.put("currentDate", new JobParameter(Calendar.getInstance()
				.getTime()));

		// Lancement du job
		try {
			JobParameters jobParameters = new JobParameters(parameters);
			LOGGER.debug("Launch Batch pocLectureZipParallelJob for test");

			JobExecution jobExecution = jobLauncher.run(
					pocLectureZipParallelJob, jobParameters);

			Assert.assertTrue("Le batch c'est terminer avec une erreur.",
					BatchStatus.COMPLETED.equals(jobExecution.getStatus()));

		} catch (JobExecutionAlreadyRunningException e) {
			LOGGER.error("JOB Already Running", e);
		} catch (JobRestartException e) {
			LOGGER.error("JOB Restart", e);
		} catch (JobInstanceAlreadyCompleteException e) {
			LOGGER.error("JOB Already Complete", e);
		} catch (JobParametersInvalidException e) {
			LOGGER.error("JOB Invalid Parameters", e);
		}
	}
}
