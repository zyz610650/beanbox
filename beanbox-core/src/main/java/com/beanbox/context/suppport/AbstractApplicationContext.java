package com.beanbox.context.suppport;


import com.beanbox.aop.proxy.DefaultAdvisorAutoProxyCreator;
import com.beanbox.beans.factory.BeanFactory;
import com.beanbox.beans.factory.ConfigurableBeanFactory;
import com.beanbox.beans.factory.ConfigurableListableBeanFactory;
import com.beanbox.beans.processor.BeanDefinitionPostProcessor;
import com.beanbox.beans.processor.BeanPostProcessor;
import com.beanbox.beans.processor.support.ApplicationContextAwareProcessor;
import com.beanbox.context.ConfigurableApplicationContext;
import com.beanbox.event.ApplicationEvent;
import com.beanbox.event.ContextClosedEvent;
import com.beanbox.event.ContextRefreshedEvent;
import com.beanbox.event.broadcast.ApplicationEventBroadcaster;
import com.beanbox.event.broadcast.SimpleApplicationEventBroadcaster;
import com.beanbox.event.listener.ApplicationListener;
import com.beanbox.io.loader.support.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * @author: @zyz
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

	public static final String APPLICATION_EVENT_BROADCASTER_BEAN_NAME="applicationEventBroadcaster";

	private ApplicationEventBroadcaster applicationEventBroadcaster;
	@Override
	public void refresh () {

		// 创建BeanFactory，并加载BeanDefinition
		//扫描xml/注解 把配置的bean封装为BeanDefinition
		//其中会把配置的beanDefinition处理器和Bean处理器添加到BeanDefinitionMap容器中
		refreshBeanFactory ();

		//获取beanFactory
		ConfigurableListableBeanFactory beanFactory=getBeanFactory ();

		// 添加需要用到的Processor
		addPreBeanPostProcessor(beanFactory);

		//注册并执行执行定义的BeanDefinition处理器
		registerBeanDefinitionPostProcessors (beanFactory);

		//BeanPostProcessor 需要提前于其他 Bean 对象实例化之前加到Processors缓存中
		registerBeanPostProcessors (beanFactory);


		//初始化事件通知器
		initApplicationEventBroadcaster ();

		// 注册事件监听器
		registerListeners ();

		//提前实例化单例Bean对象
		beanFactory.preInstantiateSingletons ();

		//发布容器刷新完成事件
		finshRefresh ();
	}

	/**
	 * 提前添加用到的Processor到IOC
	 * @param beanFactory
	 */
	public void addPreBeanPostProcessor(ConfigurableListableBeanFactory beanFactory)
	{
		// 添加ApplicationContextAwareProcessor,让继承自ApplicationContextAware的Bean对象都能感知所属的ApplicationContext
		beanFactory.addBeanPostProcessor (new ApplicationContextAwareProcessor (this));
//		// 添加Aop Processor
//		beanFactory.addBeanPostProcessor (new DefaultAdvisorAutoProxyCreator ());
	}

	/**
	 * 初始化事件通知器
	 */
	private void initApplicationEventBroadcaster(){
		ConfigurableListableBeanFactory beanFactory=getBeanFactory ();
		applicationEventBroadcaster =new SimpleApplicationEventBroadcaster (beanFactory);
		//注册全局单例通知器
		beanFactory.registerSingleton (APPLICATION_EVENT_BROADCASTER_BEAN_NAME,applicationEventBroadcaster);
	}

	/**
	 * 向通知器里添加监听器
	 */
	private void registerListeners()
	{
		//获得所有注册的监听器
		Collection< ApplicationListener > applicationListeners =getBeansOfType (ApplicationListener.class).values ();

		//监听器添加到缓存中 用于后续事件发生时通知器通知坚挺者
		for (ApplicationListener listener : applicationListeners)
		{
			applicationEventBroadcaster.addApplicationListener (listener);
		}

	}

	/**
	 * 框架初始化完成后 通知所有监听器
	 */
	private  void finshRefresh(){
		publishEvent (new ContextRefreshedEvent (this));
	}

	/**
	 * 发布事件
	 * @param event
	 */
	@Override
	public void publishEvent (ApplicationEvent event) {
		applicationEventBroadcaster.broadcastEvent (event);
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
	public void registerShutdownHook () {
		//添加关闭线程
		Runtime.getRuntime ().addShutdownHook (new Thread (this::close,"destoryThread"));
	}

	@Override
	public void close () {

		// 发布容器关闭事件
		publishEvent (new ContextClosedEvent (this));

		//执行销毁单例bean的销毁方法
		getBeanFactory ().destorySingletons ();
	}

	@Override
	public Object getBean (String name , Object... args) {
		return getBeanFactory ().getBean (name,args);
	}

	@Override
	public < T > T getBean (String name , Class < T > requiredType) {
		return getBeanFactory ().getBean (name,requiredType);
	}

	@Override
	public Object getBean (String name) {
		return getBeanFactory().getBean (name);
	}

	@Override
	public <T> T getBean(Class<T> requiredType)   {
		return getBeanFactory().getBean(requiredType);
	}
}
