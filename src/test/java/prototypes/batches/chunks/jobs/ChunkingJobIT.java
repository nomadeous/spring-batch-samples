package prototypes.batches.chunks.jobs;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/chunking/applicationContext.xml"})
public class ChunkingJobIT extends BatchJobIT {

    @Resource(name = "chunkingJob")
    private Job chunkingJob;

    @Test
    public void launchTest() {
        super.launchCompletingJob(chunkingJob);
    }
}
