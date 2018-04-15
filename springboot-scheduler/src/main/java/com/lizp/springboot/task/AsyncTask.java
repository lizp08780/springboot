package com.lizp.springboot.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {
	/**
	 * 放在单独的类中，防止不起效。@Async("指定线程池")<br>
	 * 自定义线程池可以实现AsyncConfigurer或继承AsyncConfigurerSupport
	 */
	@Async
	public void task1() {
		long begin = System.currentTimeMillis();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("task1任务耗时:" + (end - begin) + "ms");
	}
}
