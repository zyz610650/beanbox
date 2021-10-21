package com.beanbox.context;

/**
 * @author: @zyz
 */
public interface ConfigurableApplicationContext extends ApplicationContext{

	/**
	 * 刷新容器
	 */
	void refresh();
}
