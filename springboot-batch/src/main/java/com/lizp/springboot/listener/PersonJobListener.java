package com.lizp.springboot.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class PersonJobListener implements JobExecutionListener {
	private static Logger logger = LoggerFactory.getLogger(PersonJobListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("任务处理开始...{}", jobExecution);
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.info("任务处理结束...{}", jobExecution);
	}

}
