package prototypes.batches.listeners.listener;


import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.listener.StepListenerSupport;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleStepListener extends StepListenerSupport<Object, Object> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SimpleStepListener.class);

	private StepExecution stepExecution;

	@BeforeStep
	public void beforeStepProcess(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
		LOGGER.debug("Launching " + stepExecution.getStepName());
	}

	@AfterStep
	public void afterStepProcess(StepExecution stepExecution) {
		LOGGER.debug("End of " + stepExecution.getStepName());
	}

	@Override
	public void onReadError(Exception ex) {
            LOGGER.error("Error reading", ex);
	}

	@Override
	public void onWriteError(Exception ex, List<? extends Object> items) {
            LOGGER.error("Error writing items", ex);
	}

}
