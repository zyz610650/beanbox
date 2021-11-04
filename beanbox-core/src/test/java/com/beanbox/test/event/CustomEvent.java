package com.beanbox.test.event;

import com.beanbox.context.ApplicationContext;
import com.beanbox.event.ApplicationContextEvent;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: @zyz
 */
@Getter
@Setter
public class CustomEvent extends ApplicationContextEvent {

	private Long id;
	private String msg;

	public CustomEvent (Object source,Long id, String msg) {
		super (source);
		this.id=id;
		this.msg=msg;
	}
}
