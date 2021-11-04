package com.beanbox.event.broadcast;

import com.beanbox.beans.factory.ConfigurableBeanFactory;
import com.beanbox.event.ApplicationEvent;
import com.beanbox.event.listener.ApplicationListener;

/**
 * @author: @zyz
 * 事件广播器，用于通知监听某事件的监听者
 */
public class SimpleApplicationEventBroadcaster extends AbstractApplicationEventBroadcaster{

	public SimpleApplicationEventBroadcaster (ConfigurableBeanFactory beanFactory) {
		setBeanFactory (beanFactory);
	}

	@Override
	public void broadcastEvent (ApplicationEvent event) {
		for (ApplicationListener listener: getApplicationListeners (event)){
				listener.onApplicationEvent (event);
		}
	}
}
