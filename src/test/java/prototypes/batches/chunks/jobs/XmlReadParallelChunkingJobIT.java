package prototypes.batches.chunks.jobs;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/parallelchunking/applicationContext.xml"})
public class XmlReadParallelChunkingJobIT extends BatchJobIT {

    @Resource(name = "xmlReadParallelChunkingJob1")
    private Job xmlReadParallelChunkingJob1;
    @Resource(name = "xmlReadParallelChunkingJob2")
    private Job xmlReadParallelChunkingJob2;

    @Test
    public void launchTest1() {
        super.launchCompletingJob(xmlReadParallelChunkingJob1);
    }
    @Test
    public void launchTest2() {
        super.launchFailingJob(xmlReadParallelChunkingJob2);
    }
}
