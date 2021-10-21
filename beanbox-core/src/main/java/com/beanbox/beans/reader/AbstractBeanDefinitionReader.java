package com.beanbox.beans.reader;

import com.beanbox.beans.registry.BeanDefinitionRegistry;
import com.beanbox.io.loader.ResourceLoader;
import com.beanbox.io.loader.support.DefaultResourceLoader;


/**
 * @author: @zyz
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

	/**
	 * BeanDefinition注册器 用于注册BeanDefinition
	 */
	private final BeanDefinitionRegistry registry;

	/**
	 * 资源加载器 用于从file url classPath 加载资源
	 */
	private ResourceLoader resourceLoader;

	protected AbstractBeanDefinitionReader (BeanDefinitionRegistry registry) {

		this(registry,new DefaultResourceLoader ());


	}

	public AbstractBeanDefinitionReader (BeanDefinitionRegistry registry , ResourceLoader resourceLoader) {
		this.registry = registry;
		this.resourceLoader = resourceLoader;
	}

	@Override
	public BeanDefinitionRegistry getRegistry () {
		return registry;
	}

	@Override
	public ResourceLoader getResourceLoader () {
		return resourceLoader;
	}
}
