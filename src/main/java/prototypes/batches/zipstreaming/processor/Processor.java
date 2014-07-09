package prototypes.batches.zipstreaming.processor;

import javax.xml.bind.JAXBElement;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Processor implements
		ItemProcessor<JAXBElement<T>, String> {

	private static final String VIRGULE = ",";

	   private static final Logger LOGGER = LoggerFactory
            .getLogger(Processor.class);

	@Value(value = "${ucu.name}")
	private String ucuName;

	private org.springframework.core.io.Resource filePath;

	@Override
	public String process(JAXBElement<T> item) throws Exception {

		T mes = item.getValue();
		
		// dans le cas ou le nom du fichier contient des espaces
		return filePath.getFilename().replaceAll("%20", " ");
	}

}
