package com.lizp.springboot.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements Job {
	private static Logger _log = LoggerFactory.getLogger(HelloJob.class);

	public HelloJob() {
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		_log.info("Hello World! - " + new Date());
	}
}
