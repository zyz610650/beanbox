package com.beanbox.test.event;

import com.beanbox.event.ContextClosedEvent;
import com.beanbox.event.listener.ApplicationListener;

public class ContextClosedEventListener implements ApplicationListener < ContextClosedEvent > {

	@Override
	public void onApplicationEvent (ContextClosedEvent event) {
		System.out.println("关闭事件：" + this.getClass().getName());
	}
}
