package com.beanbox.beans.aware;

/**
 * @author: @zyz
 * 实现此接口，既能感知到所属的ClassLoader
 */
public interface BeanClassLoaderAware extends Aware{

	void setBeanClassLoader(ClassLoader classLoader);
}
