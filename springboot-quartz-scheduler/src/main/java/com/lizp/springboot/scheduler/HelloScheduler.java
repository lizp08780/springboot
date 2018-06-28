package com.lizp.springboot.scheduler;

import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lizp.springboot.job.HelloJob;

//@Configuration
public class HelloScheduler {
	Logger log = LoggerFactory.getLogger(HelloScheduler.class);

	@Bean
	public Scheduler scheduler() throws Exception {
		log.info("------- Initializing ----------------------");
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();
		log.info("------- Initialization Complete -----------");

		Date runTime = DateBuilder.evenMinuteDate(new Date());
		log.info("------- Scheduling Job  -------------------");

		// 任务
		JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1", "group1").build();

		// 触发条件
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();

		// 任务加入调度器中
		scheduler.scheduleJob(job, trigger);
		log.info(job.getKey() + " will run at: " + runTime);

		// 开启
		scheduler.start();
		log.info("------- Started Scheduler -----------------");

		log.info("------- Waiting 65 seconds... -------------");
		try {
			// wait 65 seconds to show job
			Thread.sleep(65L * 1000L);
			// executing...
		} catch (Exception e) {
			//
		}

		log.info("------- Shutting Down ---------------------");
		scheduler.shutdown(true);
		log.info("------- Shutdown Complete -----------------");
		return scheduler;
	}
}
