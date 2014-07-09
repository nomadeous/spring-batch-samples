package prototypes.batches.zipstreaming.decider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import prototypes.batches.zipstreaming.ctx.ExecutionContextBatch;

public class CheckHaveMoreZipFile implements JobExecutionDecider,
        InitializingBean {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CheckHaveMoreZipFile.class);

    @Value("${batch.prm.dossier.entree}")
    private String inputFilePath;

    @Value("${file.extension}")
    private String[] extension;

    private String[] zipFiles;

    @Value("${file.regex}")
    private String zipRegex;

    private ExecutionContextBatch context;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution,
            StepExecution stepExecution) {

        LOGGER.debug(">> CheckHaveMoreZipFile >> " + inputFilePath);

        /**
         * On compte les fichiers pour voir si le repertoire est vide
         */
        int fileNumber = 0;

        try {
            fileNumber = DirectoryUtils.getFilesCount(inputFilePath);

        } catch (Exception e) {
            LOGGER.debug(">> CheckHaveMoreZipFile Error exception >> "
                    + e.getMessage());
            LOGGER.error("Error", e);
            jobExecution.setExitStatus(ExitStatus.FAILED);
            return null;
        }

        if (fileNumber > 0) {
            LOGGER.debug(">> CheckHaveMoreZipFile - One or More Xml files are present >>  ");

            zipFiles = DirectoryUtils.searchFileRegex(inputFilePath, extension,
                    zipRegex);
            if (zipFiles != null && zipFiles.length > 0) {
                String contextFileToBeLoad = zipFiles[0];

                context.setFile(contextFileToBeLoad);
                return new FlowExecutionStatus("OK");
            } else {
                LOGGER.debug(
                        "CheckHaveMoreZipFile.decide - No more file to process, batch {0}",
                        FlowExecutionStatus.COMPLETED);
                return FlowExecutionStatus.COMPLETED;
            }
        } else {

            LOGGER.debug(">> CheckHaveMoreZipFile - No fragment present >> ");
            return FlowExecutionStatus.COMPLETED;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.context = ExecutionContextBatch.getInstance();
    }
}
