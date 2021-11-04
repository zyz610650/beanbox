package com.beanbox.event;

import java.util.EventObject;

/**
 * @author: @zyz
 */
public abstract class ApplicationEvent extends EventObject {

	public ApplicationEvent (Object source) {
		super(source);
	}
}
