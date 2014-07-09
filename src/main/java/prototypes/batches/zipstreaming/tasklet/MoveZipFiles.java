package prototypes.batches.zipstreaming.tasklet;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import prototypes.batches.zipstreaming.ctx.ExecutionContextBatch;

public class MoveZipFiles implements Tasklet, InitializingBean {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MoveZipFiles.class);

    @Value("${batch.prm.nombre.fichier.move}")
    private int filesToProcess;

    @Value("${batch.prm.dossier.traitement}")
    private String filesPathToProcess;

    @Value("${batch.prm.dossier.entree}")
    private String inputFilePath;

    private ExecutionContextBatch context;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.context = ExecutionContextBatch.getInstance();
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution,
            ChunkContext chunkContext) throws Exception {
        LOGGER.debug(">> Move Zip Files from {0} to {1} >> ", inputFilePath,
                filesPathToProcess);

        // recuperer tous les fichiers du repertoire de depot
        File sourceFiles = new File(inputFilePath);

        File[] fileNames = sourceFiles.listFiles();

        // Classer les fichier par ordre de reception
        Arrays.sort(fileNames, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.valueOf(f1.lastModified()).compareTo(
                        f2.lastModified());
            }
        });

        LOGGER.debug(">> List of files on directory >> " + fileNames.length);

        int setOfFiles;
        // Deplacer les fichiers dans le repertoire de traitement
        if (fileNames.length < filesToProcess) {
            setOfFiles = fileNames.length;
        } else {
            setOfFiles = filesToProcess;
        }

        LOGGER.debug(">> setOfFiles to process  >> " + setOfFiles);
        for (int length = 0; length < setOfFiles; length++) {

            File file = fileNames[length];

            String zipFileInput = inputFilePath + file.getName();
            String zipFileprocess = filesPathToProcess + file.getName();
            LOGGER.debug(">> Debut du deplacement du fichier vers le repertoire de traitement : ### "
                    + zipFileInput + "  >>>>  " + zipFileprocess);
            FileUtils.rename(new File(zipFileInput), new File(zipFileprocess));

            LOGGER.debug(">> SUCCES : deplacement du fichier vers le repertoire de traitement : ### "
                    + zipFileInput + "  >>>>  " + zipFileprocess);
        }
        return null;
    }
}
