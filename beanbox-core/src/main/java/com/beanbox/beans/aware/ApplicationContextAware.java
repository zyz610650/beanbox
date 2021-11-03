package com.beanbox.beans.aware;

import com.beanbox.context.ApplicationContext;

/**
 * @author: @zyz
 *
 * 实现此接口即能感知到所属的ApplicationContext
 *
 */
public interface ApplicationContextAware extends Aware{

	void setApplicationContext(ApplicationContext applicationContext);
}
