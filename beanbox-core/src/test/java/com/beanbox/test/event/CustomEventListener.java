package com.beanbox.test.event;

import com.beanbox.event.listener.ApplicationListener;

import java.util.Date;

/**
 * @author: @zyz
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {

	@Override
	public void onApplicationEvent (CustomEvent event) {
		System.out.println("收到：" + event.getSource() + "消息;时间： " + new Date ());
		System.out.println("消息：" + event.getId() + ":" + event.getMsg ());
	}
}
