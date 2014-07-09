package prototypes.batches.zipstreaming.tasklet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import fr.erdf.nsge.sgel.batch.commons.DirectoryUtils;
import fr.erdf.nsge.sgel.fwk.log.SGELLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RechercherFichiersTasklet implements Tasklet, InitializingBean {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RechercherFichiersTasklet.class);

    @Value("${xml.path}")
    private String xmlFilePath;

    @Value("${file.extension}")
    private String[] extension;

    @Value("${file.regex}")
    private String xmlRegex;

    @Value("${last.word.xml.file}")
    private String lastWordXmlFile;

    private String[] xmlFiles;

    @Resource(name = "executionContext")
    private ExecutionContext context;

    @Override
    public RepeatStatus execute(StepContribution stepContribution,
            ChunkContext chunkContext) throws Exception {

        LOGGER.debug(
                "RechercherFichiersTasklet - Debut traitement des fichiers xml depuis le repertoire: {0}",
                xmlFilePath);

        context.put("KO", false);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

        try {
			// recuperer tous les fichiers d'entree en verifiant les noms des
            // fichiers et leurs extensions
            xmlFiles = DirectoryUtils.searchFileRegex(xmlFilePath, extension,
                    xmlRegex);

            if (xmlFiles != null && xmlFiles.length != 0) {

                LOGGER.debug("RechercherFichiersTasklet - Nombre de fichier recuperer : "
                        + xmlFiles.length);

				// PRM.RGD.102
                // recuperation de la date dans le nom du fichier, stocker dans
                // une map et trier en fonction de cette date
                Map<Date, String> fileMap = new TreeMap<Date, String>();

                for (String fileName : xmlFiles) {

                    // PRM.RGD.232
                    int lastIndex = fileName.lastIndexOf(lastWordXmlFile);
                    String date = (String) fileName.subSequence(lastIndex - 14,
                            lastIndex);
                    fileMap.put(dateFormat.parse(date), fileName);
                }

                Map<Date, String> fileMapSorted = new TreeMap(fileMap);

                LOGGER.debug("RechercherFichiersTasklet - Nombre de fichier recuperer : "
                        + fileMapSorted.size());

                for (Entry<Date, String> entry : fileMapSorted.entrySet()) {
                    LOGGER.debug("RechercherFichiersTasklet - fichier a traiter : "
                            + entry.getValue());
                    context.putString("FILE", entry.getValue());
                    context.put("DATE", entry.getKey());
                    break;
                }

                return RepeatStatus.FINISHED;
            } else {
                LOGGER.debug("RechercherFichiersTasklet - Aucun fichier n'est conforme.");
                context.put("KO", true);
            }
        } catch (Exception e) {
            LOGGER.error("Error", e);
            context.put("KO", true);
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // context = ExecutionContextBatch.getInstance();
    }
}
