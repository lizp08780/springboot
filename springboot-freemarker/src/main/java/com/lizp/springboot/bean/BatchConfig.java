package com.lizp.springboot.bean;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.DatabaseType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.lizp.springboot.listener.TestJobListener;
import com.lizp.springboot.processor.TestItemProcessor;
import com.lizp.springboot.reader.TestItemReader;
import com.lizp.springboot.writer.TestItemWriter;

@Configuration
@EnableBatchProcessing // 开启批处理
public class BatchConfig {
	@Bean
	public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager)
			throws Exception {
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(dataSource);
		jobRepositoryFactoryBean.setTransactionManager(transactionManager);
		jobRepositoryFactoryBean.setDatabaseType(DatabaseType.MYSQL.name());
		return jobRepositoryFactoryBean.getObject();
	}

	public SimpleJobLauncher jobLauncher(DataSource dataSource, PlatformTransactionManager transactionManager)
			throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(this.jobRepository(dataSource, transactionManager));
		return jobLauncher;
	}

	@Bean
	public Job importJob(JobBuilderFactory jobs, Step step) {
		return jobs.get("importJob").incrementer(new RunIdIncrementer()).flow(step) // 为Job指定Step
				.end().listener(testJobListener()) // 绑定监听器
				.build();
	}

	@Bean
	public Step testStep(StepBuilderFactory stepBuilderFactory, ItemReader<String> reader, ItemWriter<String> writer,
			ItemProcessor<String, String> processor) {
		return stepBuilderFactory.get("testStep").<String, String>chunk(10) // 批处理每次提交5000条数据
				.reader(reader) // 给step绑定reader
				.processor(processor) // 给step绑定processor
				.writer(writer) // 给step绑定writer
				.build();
	}

	@Bean
	public TestJobListener testJobListener() {
		return new TestJobListener();
	}

	@Bean
	public ItemReader<String> reader() {
		TestItemReader reader = new TestItemReader();
		return reader;
	}

	@Bean
	public ItemProcessor<String, String> processor() {
		TestItemProcessor processor = new TestItemProcessor();
		return processor;
	}

	@Bean
	public ItemWriter<String> writer() {
		TestItemWriter<String> writer = new TestItemWriter<String>();
		return writer;
	}

}
