package com.beanbox.event.broadcast;

import com.beanbox.event.listener.ApplicationListener;

/**
 * @author: @zyz
 */
public interface ApplicationEventBroadcaster {

	/**
	 * 添加一个监听器 用于监听所有事件
	 * @param listener
	 */
	void addApplicationListener(ApplicationListener <?> listener );


	/**
	 * 移除一个监听器
	 * @param listener
	 */
	void removeApplicationListener(ApplicationListener<?> listener);


	/**
	 * 将事件通知给对应的监听器
	 * @param event
	 */
	void broadcastEvent(ApplicationListener event);
}
