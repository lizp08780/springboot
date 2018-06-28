package com.lizp.springboot.scheduler;

import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lizp.springboot.job.SimpleJob;

@Configuration
public class SimpleTriggerScheduler {
	Logger log = LoggerFactory.getLogger(SimpleTriggerScheduler.class);

	@Bean
	public Scheduler scheduler() throws Exception {
		log.info("------- Initializing ----------------------");
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();
		log.info("------- Initialization Complete -----------");

		Date startTime = DateBuilder.nextGivenSecondDate(null, 15);
		log.info("------- Scheduling Job  -------------------");

		// 任务1
		JobDetail job = JobBuilder.newJob(SimpleJob.class).withIdentity("job1", "group1").build();
		// 触发条件1
		SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
				.startAt(startTime).build();
		// 任务1加入调度器中
		Date ft = scheduler.scheduleJob(job, trigger);
		log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
				+ trigger.getRepeatInterval() / 1000 + " seconds");

		// 任务2
		job = JobBuilder.newJob(SimpleJob.class).withIdentity("job2", "group1").build();
		// 触发条件2
		trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger2", "group1").startAt(startTime)
				.build();
		// 任务2加入调度器中
		ft = scheduler.scheduleJob(job, trigger);
		log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
				+ trigger.getRepeatInterval() / 1000 + " seconds");

		// 任务3
		job = JobBuilder.newJob(SimpleJob.class).withIdentity("job3", "group1").build();
		// 触发条件3 运行11次，没10秒重复一次
		trigger = TriggerBuilder.newTrigger().withIdentity("trigger3", "group1").startAt(startTime)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10))
				.build();
		ft = scheduler.scheduleJob(job, trigger);
		log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
				+ trigger.getRepeatInterval() / 1000 + " seconds");

		// 同任务，不同触发器
		trigger = TriggerBuilder.newTrigger().withIdentity("trigger3", "group2").startAt(startTime)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(2))
				.forJob(job).build();
		ft = scheduler.scheduleJob(trigger);
		log.info(job.getKey() + " will [also] run at: " + ft + " and repeat: " + trigger.getRepeatCount()
				+ " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

		// 4
		job = JobBuilder.newJob(SimpleJob.class).withIdentity("job4", "group1").build();
		trigger = TriggerBuilder.newTrigger().withIdentity("trigger4", "group1").startAt(startTime)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(5))
				.build();
		ft = scheduler.scheduleJob(job, trigger);
		log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
				+ trigger.getRepeatInterval() / 1000 + " seconds");

		// 5
		job = JobBuilder.newJob(SimpleJob.class).withIdentity("job5", "group1").build();
		trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger5", "group1")
				.startAt(DateBuilder.futureDate(5, IntervalUnit.MINUTE)).build();
		ft = scheduler.scheduleJob(job, trigger);
		log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
				+ trigger.getRepeatInterval() / 1000 + " seconds");

		// 6
		job = JobBuilder.newJob(SimpleJob.class).withIdentity("job6", "group1").build();
		trigger = TriggerBuilder.newTrigger().withIdentity("trigger6", "group1").startAt(startTime)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();
		ft = scheduler.scheduleJob(job, trigger);
		log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
				+ trigger.getRepeatInterval() / 1000 + " seconds");

		log.info("------- Starting Scheduler ----------------");

		// 开启
		scheduler.start();
		log.info("------- Started Scheduler -----------------");

		// 7
		job = JobBuilder.newJob(SimpleJob.class).withIdentity("job7", "group1").build();
		trigger = TriggerBuilder.newTrigger().withIdentity("trigger7", "group1").startAt(startTime)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).withRepeatCount(20))
				.build();
		ft = scheduler.scheduleJob(job, trigger);
		log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
				+ trigger.getRepeatInterval() / 1000 + " seconds");

		// 8
		job = JobBuilder.newJob(SimpleJob.class).withIdentity("job8", "group1").storeDurably().build();
		scheduler.addJob(job, true);
		log.info("'Manually' triggering job8...");
		scheduler.triggerJob(JobKey.jobKey("job8", "group1"));

		log.info("------- Waiting 30 seconds... --------------");
		try {
			// wait 33 seconds to show job
			Thread.sleep(30L * 1000L);
			// executing...
		} catch (Exception e) {
			//
		}

		// 7
		log.info("------- Rescheduling... --------------------");
		trigger = TriggerBuilder.newTrigger().withIdentity("trigger7", "group1").startAt(startTime)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).withRepeatCount(20))
				.build();
		ft = scheduler.rescheduleJob(trigger.getKey(), trigger);
		log.info("job7 rescheduled to run at: " + ft);

		log.info("------- Waiting five minutes... ------------");
		try {
			// wait five minutes to show jobs
			Thread.sleep(300L * 1000L);
			// executing...
		} catch (Exception e) {
			//
		}

		log.info("------- Shutting Down ---------------------");
		scheduler.shutdown(true);
		log.info("------- Shutdown Complete -----------------");

		SchedulerMetaData metaData = scheduler.getMetaData();
		log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
		return scheduler;
	}
}
