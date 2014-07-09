package prototypes.batches.zipstreaming.decider;

import prototypes.batches.zipstreaming.ctx.ExecutionContextBatch;
import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.InitializingBean;

import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckEndExecutionBatch implements JobExecutionDecider,
        InitializingBean {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CheckEndExecutionBatch.class);

    private ExecutionContextBatch context;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution,
            StepExecution stepExecution) {
        LOGGER.debug(">> CheckEndExecutionBatch >>End Date {0}",
                context.getPreviousEndDate().toString());

        Date today = Calendar.getInstance().getTime();
        if (today.after(context.getPreviousEndDate())) {
            LOGGER.error("Obsolete date", new RuntimeException());
            return FlowExecutionStatus.COMPLETED;
        } else {
            return new FlowExecutionStatus("OK");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.context = ExecutionContextBatch.getInstance();
    }

}
