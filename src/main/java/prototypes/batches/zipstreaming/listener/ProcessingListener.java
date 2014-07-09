package prototypes.batches.zipstreaming.listener;

import java.io.File;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.listener.StepListenerSupport;

import com.google.common.base.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessingListener extends StepListenerSupport {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProcessingListener.class);

	private StepExecution stepExecution;

	@BeforeStep
	public void beforeStepProcess(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
		LOGGER.debug("##### launching " + stepExecution.getStepName());

	}

	@AfterStep
	public void afterStepProcess(StepExecution stepExecution) {
		// recuperation du nom de fichier
		String fileName = (String) stepExecution.getExecutionContext().get(
				"fileName");
		if (!Strings.isNullOrEmpty(fileName))
			fileName = new File(fileName).getName();
		LOGGER.debug("##### afterStepTraiterMesure - filename : " + fileName);
		LOGGER.debug("##### End of " + stepExecution.getStepName());
	}

	@Override
	public void onReadError(Exception ex) {
		String fileName = (String) stepExecution.getExecutionContext().get(
				"fileName");
		if (!Strings.isNullOrEmpty(fileName))
			fileName = new File(fileName).getName();
		LOGGER.debug("### Incorrect input file {0} !",
				fileName);
		LOGGER.error(fileName, ex);
	}

	// @Override
	// public void onWriteError(Exception e, List<? extends String> items) {
	// LOGGER.traceDev("#### Writer of {0} error on item {1} {2}",
	// stepExecution.getStepName(), items.toString(), stepExecution);
	// LOGGER.erreurTechnique(e);
	//
	// }

}
