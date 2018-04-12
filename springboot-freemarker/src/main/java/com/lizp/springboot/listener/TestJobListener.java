package com.lizp.springboot.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class TestJobListener implements JobExecutionListener {
	private long startTime;
	private long endTime;

	@Override
	public void afterJob(JobExecution arg0) {
		startTime = System.currentTimeMillis();
		System.err.println("任务处理开始...");
	}

	@Override
	public void beforeJob(JobExecution arg0) {
		endTime = System.currentTimeMillis();
		System.err.println("任务处理结束...");
		System.err.println("耗时:" + (endTime - startTime) + " ms");
	}

}
