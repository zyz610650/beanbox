package com.beanbox.context;

/**
 * @author: @zyz
 */
public interface ConfigurableApplicationContext extends ApplicationContext{

	/**
	 * 刷新容器
	 */
	void refresh();

	/**
	 * 添加自动销毁钩子
	 */
	void registerShutdownHook();

	/**
	 * 关闭容器
	 */
	void close();
}
