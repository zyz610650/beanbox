package com.beanbox.beans.factory.support;

import com.beanbox.beans.annotation.support.StringValueResolver;
import com.beanbox.beans.factory.ConfigurableListableBeanFactory;
import com.beanbox.beans.factory.FactoryBean;
import com.beanbox.beans.po.BeanDefinition;

import com.beanbox.beans.processor.BeanPostProcessor;
import com.beanbox.beans.registry.support.FactoryBeanRegistrySupport;
import com.beanbox.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: @zyz
 * 与Bean类有关方法的具体实现类
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableListableBeanFactory {

	// BeanPostProcessor容器 项目启动时扫描所有BeanPostProcessor加载到该容器中
	private final List < BeanPostProcessor> beanPostProcessors=new ArrayList <> ();

	/**
	 * 类加载器
	 */
	private final ClassLoader beanClassLoader= ClassUtils.getDefaultClassLoader ();

	/**
	 * 字符串解析器缓存
	 */
	private final List<StringValueResolver> embeddedValueResolvers =new ArrayList <> ();

	@Override
	public Object getBean (String name , Object... args) {
		return doGetBean (name,args);
	}

	@Override
	public < T > T getBean (String name , Class < T > requiredType) {
		return (T) getBean (name);
	}

	private <T> T doGetBean(final  String name,final Object[] args)
	{
		Object bean=getSingleton (name);
		if (bean!=null)
			return (T) getObjectForBeanInstance (bean,name);

		//懒加载 用到的时候才根据BeanDefinition创建具体的bean
		BeanDefinition beanDefinition=getBeanDefinition (name);
         bean = createBean (name, beanDefinition, args);


		return (T) getObjectForBeanInstance (bean,name);
	}

	/**
	 * 获得BeanDefinition
	 * @param beanName
	 * @return
	 */
	@Override
	public abstract  BeanDefinition getBeanDefinition (String beanName);


	/**
	 * 创建带参Bean对象
	 * @param name
	 * @param beanDefinition
	 * @return
	 */
	protected abstract Object createBean (String name , BeanDefinition beanDefinition , Object[] args);

	/**
	 * 注入Bean的属性
	 * @param beanName
	 * @param bean
	 * @param beanDefinition
	 */
	protected abstract void applyPropertyValues(String beanName,Object bean,BeanDefinition beanDefinition);

	@Override
	public void addBeanPostProcessor (BeanPostProcessor beanPostProcessor) {
		//防止beanPostProcessor重复添加 因为list不会判重
		beanPostProcessors.remove (beanPostProcessor);
		beanPostProcessors.add (beanPostProcessor);
	}

	public List<BeanPostProcessor> getBeanPostProcessors()
	{
		return this.beanPostProcessors;
	}

	public ClassLoader getBeanClassLoader()
	{
		return this.beanClassLoader;
	}

	private Object getObjectForBeanInstance(Object beanInstance, String beanName)
	{
		if (!(beanInstance instanceof FactoryBean))
		{
			return beanInstance;
		}

		Object object=getCachedObjectForFactoryBean (beanName);

		if (object == null)
		{
			FactoryBean<?> factoryBean= (FactoryBean < ? >) beanInstance;

			object=getObjectFromFactoryBean (factoryBean,beanName);

		}
		return object;
	}

	@Override
	public void addEmbeddedValueResolver (StringValueResolver valueResolver) {
			this.embeddedValueResolvers.add (valueResolver);
	}

	/***
	 * 依次用字符串解析器对字符进行处理
	 * @param value
	 * @return
	 */
	@Override
	public String resolveEmbeddedValue (String value) {
		String res=value;
		for (StringValueResolver resolver: this.embeddedValueResolvers){
			res=resolver.resolveStringValue (res);
		}
		return res;
	}


}
