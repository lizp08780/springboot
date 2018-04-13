package com.lizp.springboot.bean;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.lizp.springboot.bean.entity.Person;

@Configuration
@EnableBatchProcessing // 开启批处理
public class BatchConfig {
	@Bean
	public JobRepository jobRepository(PlatformTransactionManager transactionManager) throws Exception {
		MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean();
		mapJobRepositoryFactoryBean.setTransactionManager(transactionManager);
		return mapJobRepositoryFactoryBean.getObject();
	}

	@Bean
	public SimpleJobLauncher jobLauncher(JobRepository jobRepository, TaskExecutor taskExecutor) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(taskExecutor);// 线程池
		return jobLauncher;
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(5);
		threadPoolTaskExecutor.setMaxPoolSize(10);
		// threadPoolTaskExecutor.setKeepAliveSeconds(0);
		threadPoolTaskExecutor.setQueueCapacity(10);
		return threadPoolTaskExecutor;
	}

	@Bean
	public TaskletStep step(StepBuilderFactory stepBuilderFactory, ItemWriter<Person> writer, ItemReader<Person> reader,
			ItemProcessor<Person, Person> processor, TaskExecutor taskExecutor) {
		ThreadPoolTaskExecutor pool = ((ThreadPoolTaskExecutor) taskExecutor);
		return stepBuilderFactory.get("testStep").<Person, Person>chunk(1000) // 批处理每次提交1000条数据
				.reader(reader) // 给step绑定reader
				.processor(processor) // 给step绑定processor
				.writer(writer) // 给step绑定writer
				.taskExecutor(taskExecutor)// 在单个step中多线程执行任务
				.throttleLimit(pool.getCorePoolSize())// 线程数
				.build();
	}

	@Bean
	public Job job(JobBuilderFactory jobs, JobExecutionListener listenter, Step step) {
		return jobs.get("importJob").incrementer(new RunIdIncrementer()).flow(step) // 为Job指定Step
				.end().listener(listenter) // 绑定监听器
				.build();
	}
}
