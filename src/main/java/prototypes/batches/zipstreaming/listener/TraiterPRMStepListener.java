package prototypes.batches.zipstreaming.listener;

import java.io.File;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.listener.StepListenerSupport;

import com.google.common.base.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TraiterPRMStepListener extends StepListenerSupport {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TraiterPRMStepListener.class);

	private StepExecution stepExecution;

	@BeforeStep
	public void beforeStepTraiterMesure(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
		LOGGER.debug("##### launching " + stepExecution.getStepName());

	}

	@AfterStep
	public void afterStepTraiterMesure(StepExecution stepExecution) {
		// recuperation du nom de fichier
		String fileName = (String) stepExecution.getExecutionContext().get(
				"fileName");
		if (!Strings.isNullOrEmpty(fileName))
			fileName = new File(fileName).getName();
		LOGGER.debug("##### afterStepTraiterMesure - filename : " + fileName);
		LOGGER.debug("##### fin de la " + stepExecution.getStepName());
	}

	@Override
	public void onReadError(Exception ex) {
		String fileName = (String) stepExecution.getExecutionContext().get(
				"fileName");
		if (!Strings.isNullOrEmpty(fileName))
			fileName = new File(fileName).getName();
		LOGGER.debug("### Le fichier {0} en entrée n'est pas valide!",
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
