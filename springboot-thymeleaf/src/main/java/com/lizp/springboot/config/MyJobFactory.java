package com.lizp.springboot.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

@Component
public class MyJobFactory extends AdaptableJobFactory {
	@Autowired
	private AutowireCapableBeanFactory capableBeanFactory;

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		Object job = super.createJobInstance(bundle);
		// 把Job交给Spring来管理，这样Job就能使用由Spring产生的Bean了
		capableBeanFactory.autowireBean(job);
		return job;
	}

}
