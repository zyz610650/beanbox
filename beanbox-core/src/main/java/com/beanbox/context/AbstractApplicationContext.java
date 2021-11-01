package com.beanbox.context;


import com.beanbox.beans.factory.ConfigurableListableBeanFactory;
import com.beanbox.beans.processor.BeanDefinitionPostProcessor;
import com.beanbox.beans.processor.BeanPostProcessor;
import com.beanbox.io.loader.support.DefaultResourceLoader;

import java.util.Map;

/**
 * @author: @zyz
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

	@Override
	public void refresh () {

		// 创建BeanFactory，并加载BeanDefinition
		//扫描xml/注解 把配置的bean封装为BeanDefinition
		//其中会把配置的beanDefinition处理器和Bean处理器添加到BeanDefinitionMap容器中
		refreshBeanFactory ();

		//获取beanFactory
		ConfigurableListableBeanFactory beanFactory=getBeanFactory ();

		//执行定义的BeanDefinition处理器
		registerBeanDefinitionPostProcessors (beanFactory);

		//BeanPostProcessor 需要提前于其他 Bean 对象实例化之前加到Processors缓存中
		registerBeanPostProcessors (beanFactory);
	}

	/**
	 * 更新BeanDefinition容器
	 */
	protected abstract  void refreshBeanFactory();

	/**
	 * 获得BeanFactory
	 * @return
	 */
	protected abstract ConfigurableListableBeanFactory getBeanFactory();

	/**
	 * 注册配置的BeanDefinition处理器
	 * @param beanFactory
	 */
	private void registerBeanDefinitionPostProcessors(ConfigurableListableBeanFactory beanFactory)
	{
		Map < String, BeanDefinitionPostProcessor > beansOfType = beanFactory.getBeansOfType (BeanDefinitionPostProcessor.class);
		beansOfType.forEach ((name,beanDefinitionProcessor)->{
			beanDefinitionProcessor.postProcessBeanDefinition (beanFactory);
		});
	}

	/**
	 * 注册配置的Bean处理器
	 * @param beanFactory
	 */
	private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory)
	{
		Map < String, BeanPostProcessor > beansOfType = beanFactory.getBeansOfType (BeanPostProcessor.class);
		beansOfType.forEach ((name,processor)->{
			beanFactory.addBeanPostProcessor (processor);

		});
	}

	@Override
	public < T > Map < String, T > getBeansOfType (Class < T > type) {
		return getBeanFactory ().getBeansOfType (type);
	}

	@Override
	public String[] getBeanDefinitionNames () {
		return getBeanFactory ().getBeanDefinitionNames ();
	}

	@Override
	public Object getBean (String name , Object... args) {
		return getBeanFactory ().getBean (name,args);
	}

	@Override
	public < T > T getBean (String name , Class < T > requiredType) {
		return getBeanFactory ().getBean (name,requiredType);
	}

}
