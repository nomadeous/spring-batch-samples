package prototypes.batches.zipstreaming.tasklet;

import java.io.IOException;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CleanDirectoryTasklet implements Tasklet {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CleanDirectoryTasklet.class);

    @Value(value = "${batch.prm.dossier.traitement}")
    private String workDirectory;

    @Override
    public RepeatStatus execute(StepContribution contribution,
            ChunkContext chunkContext) throws Exception {
        LOGGER.debug(" #### cleaning directory {0}", workDirectory);
        try {
            DirectoryUtils.cleanDirectory(workDirectory);
        } catch (IOException e) {
            LOGGER.debug(" #### cleaning directory {0} failed!!!",
                    workDirectory);
            LOGGER.error("Error", e);
        }
        return null;
    }
}
