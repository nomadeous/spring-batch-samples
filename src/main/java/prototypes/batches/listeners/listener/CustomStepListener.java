/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypes.batches.listeners.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class CustomStepListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CustomStepListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.info("StepExecutionListener - beforeStep");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("StepExecutionListener - afterStep");
        return null;
    }

}
