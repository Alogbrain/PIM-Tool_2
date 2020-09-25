package vn.elca.training.springbatch.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BatchTestConfig.class/*, BatchConfiguration.class your BatchConfiguration class here*/ })
public class ExampleSpringBatchTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void testBusinessStepOk() throws Exception {
		//TODO write your unit test here
//		Assert.assertEquals(BatchStatus.COMPLETED, status);

	}

	@Test
	public void testBusinessStepFail() throws Exception {
		//TODO write your unit test here
//		Assert.assertEquals(BatchStatus.FAILED, status);

	}
}