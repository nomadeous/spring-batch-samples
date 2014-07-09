package prototypes.batches.zipstreaming.tasklet;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CheckAvailableZipTasklet implements Tasklet, InitializingBean {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CheckAvailableZipTasklet.class);

    @Value("${batch.mesure.dossier.traitement}")
    private String filesPathToProcess;

    @Value(value = "${ucu.name}")
    private String ucuName;

    private String pathResources;

    private ContexteType contexte;

    @Resource(name = "multiResourcePartitioner")
    MultiResourcePartitioner multiResourcePartitioner;

    @Override
    public RepeatStatus execute(StepContribution stepContribution,
            ChunkContext chunkContext) throws Exception {

		// TODO ajouter ordonnancement par date du fichier
        // on initialise le reader avec les fichiers a traiter
        multiResourcePartitioner
                .setResources(new ClassPathXmlApplicationContext()
                        .getResources(pathResources));

        return RepeatStatus.FINISHED;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.contexte = CommonsUtils.getContexteType(this.ucuName);

    }

    public void setPathResources(String pathResources) {
        this.pathResources = pathResources;
    }

}
