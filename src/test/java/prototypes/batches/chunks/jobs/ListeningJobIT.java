package prototypes.batches.chunks.jobs;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/listening/applicationContext.xml"})
public class ListeningJobIT extends BatchJobIT {

    @Resource(name = "listeningJob")
    private Job listeningJob;

    @Test
    public void launchTest() {
        super.launchCompletingJob(listeningJob);
    }
}
