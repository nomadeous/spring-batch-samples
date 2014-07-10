package prototypes.batches.chunking.processor;


import org.springframework.batch.item.ItemProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Processor implements
        ItemProcessor<Object, Object> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(Processor.class);

    @Override
    public Object process(Object item) throws Exception {
        LOGGER.debug("Process item " + item);
        return item;
    }
}
