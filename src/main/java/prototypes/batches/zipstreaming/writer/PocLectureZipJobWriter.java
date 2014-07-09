package prototypes.batches.zipstreaming.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PocLectureZipJobWriter implements ItemWriter<String> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PocLectureZipJobWriter.class);

	@Value(value = "${ucu.name}")
	private String ucuName;

	@Override
	public void write(List<? extends String> items) throws Exception {
		LOGGER.debug("TODO : Do the job for " + this.ucuName);
	}
}
