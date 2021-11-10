package com.beanbox.beans.factory;

import com.beanbox.beans.annotation.support.StringValueResolver;
import com.beanbox.beans.registry.SingletonBeanRegistry;

/**
 * @author: @zyz
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

	/**
	 * 单例
	 */
	String SCOPE_SINGLETON="singleton";
	/**
	 * 原型
	 */
	String SCOPE_PROTOTYPE="prototype";

	/**
	 * 销毁单例对象
	 */
	void destorySingletons();

	/**
	 * 为嵌入值（如注释属性）添加字符串解析器。
	 * @param valueResolver
	 */
	void addEmbeddedValueResolver(StringValueResolver valueResolver);

	/**
	 * 解析给定的嵌入值，例如注释属性。
	 * @param value
	 * @return
	 */
	String resolveEmbeddedValue(String value);
}
