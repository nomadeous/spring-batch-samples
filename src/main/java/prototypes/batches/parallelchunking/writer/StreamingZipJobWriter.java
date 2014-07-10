package prototypes.batches.parallelchunking.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamingZipJobWriter implements ItemWriter<String> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StreamingZipJobWriter.class);

	@Value(value = "${ucu.name}")
	private String ucuName;

	@Override
	public void write(List<? extends String> items) throws Exception {
		LOGGER.debug("TODO : Do the job for " + this.ucuName);
	}
}
