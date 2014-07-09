package prototypes.batches.zipstreaming.tasklet;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.base.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteFileTasklet implements Tasklet, InitializingBean {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(DeleteFileTasklet.class);

    @Resource(name = "executionContext")
    private ExecutionContext context;

    @Value("${path.file.Ko}")
    private String koFileDirectory;

    @Value("${xml.path}")
    private String inputDirectory;

    @Override
    public void afterPropertiesSet() throws Exception {
        // this.context = ExecutionContextBatch.getInstance();
    }

    @Override
    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
            throws Exception {

        String filePath = context.getString("FILE");

        if (!Strings.isNullOrEmpty(filePath)) {

            try {
                // si y'a au moin un KO on deplace le fichier
                if ((Boolean) context.get("KO")) {
                    LOGGER.debug("SupprimerFichierTasklet - deplacer dans un autre repertoire: "
                            + koFileDirectory);
                    // deplacer le fichier dans le repertoire des fichiers KO
                    String koFileFrom = inputDirectory
                            + new File(filePath).getName();
                    String koFileDest = koFileDirectory
                            + new File(filePath).getName();
                    FileUtils
                            .rename(new File(koFileFrom), new File(koFileDest));

                } else {
                    // on supprime le fichier source
                    LOGGER.debug("SupprimerFichierTasklet.execute - supprimer fichier");
                    FileUtils.deleteDirectory(filePath);
                }

            } catch (Exception e) {
                LOGGER.error("Error", e);
                LOGGER.debug("SupprimerFichierTasklet - erreur dans la suppression du fichier  "
                        + filePath);
            }

        }

        // ! important: delete context before continuing batch process
        context.clearDirtyFlag();
        return RepeatStatus.FINISHED;

    }

}
