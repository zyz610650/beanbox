package com.beanbox.test.event;

import com.beanbox.event.ContextRefreshedEvent;
import com.beanbox.event.listener.ApplicationListener;

/**
 * @author: @zyz
 */
public class ContextRefreshedEventListener implements ApplicationListener< ContextRefreshedEvent > {
	@Override
	public void onApplicationEvent (ContextRefreshedEvent event) {
		System.out.println("刷新事件：" + this.getClass().getName());
	}
}
