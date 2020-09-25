package vn.elca.training.springbatch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vn.elca.training.springbatch.step.tasklet.SimpleTasklet;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() throws Exception {
        return jobBuilderFactory.get("simpleJob")
        		.start(taskletStep())
                .build();
    }

    
    @Bean
    public Step taskletStep(){
        return stepBuilderFactory.get("taskletStep")
                .tasklet(tasklet())
                .build();
    }
     
    @Bean
    public Tasklet tasklet() {
        return new SimpleTasklet();
    }

}
