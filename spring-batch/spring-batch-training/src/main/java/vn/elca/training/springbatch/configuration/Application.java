package vn.elca.training.springbatch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
    	ConfigurableApplicationContext springContext = new SpringApplication(Application.class).run(args);
		
		JobLauncher jobLauncher = springContext.getBean(JobLauncher.class);
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("aJobParameterKey", "aJobParameterValue")
				.toJobParameters();
		Job job = springContext.getBean(Job.class);
		jobLauncher.run(job, jobParameters);
    }
    
}
