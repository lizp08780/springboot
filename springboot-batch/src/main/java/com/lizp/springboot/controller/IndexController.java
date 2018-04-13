package com.lizp.springboot.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;

	@RequestMapping(value = "/index")
	public String index() throws Exception {
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addLong("time", System.currentTimeMillis());
		jobLauncher.run(job, builder.toJobParameters());
		return "index";
	}

}
