package com.beanbox.event.publisher;

import com.beanbox.event.ApplicationEvent;

/**
 * @author: @zyz
 */
public interface ApplicationEventPublisher {

	/**
	 * 用于发布监听事件
	 * @param event
	 */
	void publishEvent(ApplicationEvent event);
}
