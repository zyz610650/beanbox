package com.beanbox.context;

/**
 * @author: @zyz
 * 初始化Bean
 */
public interface InitializingBean {

	/**
	 * Bean属性填充后调用
	 */
	void afterPropertiesSet();
}
