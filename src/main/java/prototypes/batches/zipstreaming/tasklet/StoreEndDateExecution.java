package prototypes.batches.zipstreaming.tasklet;

import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import prototypes.batches.zipstreaming.ctx.ExecutionContextBatch;

public class StoreEndDateExecution implements Tasklet, InitializingBean {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(StoreEndDateExecution.class);

    private ExecutionContextBatch context;

    @Value("${batch.prm.duree.execution}")
    private String dureeExecution;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.context = ExecutionContextBatch.getInstance();
    }

    @Override
    public RepeatStatus execute(StepContribution stepCOntribution,
            ChunkContext chunkContext) throws Exception {
        int dureeExecutionInt;
        LOGGER.debug(" >> Start Date Execution of the batch >> "
                + new Date());
        Calendar executionEndDate = Calendar.getInstance();
        executionEndDate.setTime(new Date());

        // par defaut = une journee
        try {
            dureeExecutionInt = Integer.parseInt(dureeExecution);
        } catch (java.lang.NumberFormatException e) {
            LOGGER.debug(" ## Aucune durée d'execution n'est specifiée -> init a 1 Jour");
            dureeExecutionInt = 1440;
        }

        executionEndDate.add(Calendar.MINUTE, dureeExecutionInt);

        context.setPreviousEndDate(executionEndDate.getTime());

        LOGGER.debug(" >> Previous End Date Execution of the batch >> "
                + context.getPreviousEndDate());

        return RepeatStatus.FINISHED;
    }
}
