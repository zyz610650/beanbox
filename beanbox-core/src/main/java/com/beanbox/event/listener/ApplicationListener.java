package com.beanbox.event.listener;

import com.beanbox.event.ApplicationEvent;

import java.util.EventListener;

/**
 * @author: @zyz
 */
public interface ApplicationListener<E extends ApplicationEvent > extends EventListener {

	void onApplicationEvent(E event);
}
