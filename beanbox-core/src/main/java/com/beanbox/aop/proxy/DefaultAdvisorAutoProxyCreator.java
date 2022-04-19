package com.beanbox.aop.proxy;

import com.beanbox.aop.advisor.Advisor;
import com.beanbox.aop.advisor.AspectJExpressionPointcutAdvisor;
import com.beanbox.aop.aspect.*;
import com.beanbox.aop.interceptor.AbstractAdviceInterceptor;
import com.beanbox.beans.aware.BeanFactoryAware;
import com.beanbox.beans.factory.BeanFactory;
import com.beanbox.beans.factory.support.DefaultListableBeanFactory;
import com.beanbox.beans.processor.InstantiationAwareBeanPostProcessor;
import com.beanbox.beans.sessions.PropertyValueSession;
import com.beanbox.exception.BeanException;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author: @zyz
 *
 * 一个 PostProcessor 用于对需要织入的类封装为代理类
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

	private DefaultListableBeanFactory beanFactory;

	/**
	 * 缓存已经生成过代理的对象 避免二次创建
	 */
	private final Set <Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet <Object> ());

	/**
	 * 在bean对象创建之前 处理beanDefinition 根据classFilter判断Class上是否符合aop的拦截表达式 生成相应的代理类
	 * @param beanClass
	 * @param beanName
	 * @return
	 */
	@Override
	public Object postProcessBeforeInstantiation (Class < ? > beanClass , String beanName) {


		return null;
	}

	@Override
	public PropertyValueSession postProcessPropertyValues (PropertyValueSession propertyValueSession , Object bean , String beanName) {
		return propertyValueSession;
	}

	@Override
	public boolean postProcessAfterInstantiation (String beanName , Object bean) {
		return true;
	}

	@Override
	public Object postProcessBeforeInitialization (Object bean , String beanName) {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization (Object bean , String beanName) {

		if(!earlyProxyReferences.contains (beanName))
		{
			return wrapIfNecessary (bean,beanName);
		}
		return bean;
	}

	protected Object wrapIfNecessary(Object bean, String beanName)
	{

		Class<?> beanClass =bean.getClass ();

		//如果是那几个用于创建代理类的对象，则不用提前处理
		if (isInfrastractureClass (beanClass)) return bean;

		//获取所有切面 依次判断该类是否符合该切面中定义的表达式
		Collection< AspectJExpressionPointcutAdvisor> advisors=beanFactory.getBeansOfType (AspectJExpressionPointcutAdvisor.class).values ();
		// advice链
		AdvisedSupport adviceSupportChain=new AdvisedSupport();
		for (AspectJExpressionPointcutAdvisor advisor: advisors)
		{
			ClassFilter classFilter = advisor.getPointcut ().getClassFilter ();
			if (!classFilter.matches (beanClass)) continue;

			// 封装adviseSupport
			AdvisedSupport advisedSupport=new AdvisedSupport ();

			TargetSource targetSource=new TargetSource (bean);

			//设置目标类 增强方法 代理类方式
			advisedSupport.setTargetSource (targetSource);
			advisedSupport.setMethodInterceptor ((AbstractAdviceInterceptor) advisor.getAdvice ());
			//JDK 和 Cglib 组合使用实现多重代理
			advisedSupport.setProxyTargetClass (true);
			advisedSupport.setMethodMatcher (advisor.getPointcut ().getMethodMatcher ());
			adviceSupportChain.appendNextAdviceSupport(advisedSupport);

		}
		//返回代理
		if (adviceSupportChain.next()!=null)
		return new ProxyFactory (adviceSupportChain.next()).getProxy ();
		// 返回原始类额
		return bean;
	}

	@Override
	public void setBeanFactory (BeanFactory beanFactory) {
		this.beanFactory= (DefaultListableBeanFactory) beanFactory;
	}

	private boolean isInfrastractureClass(Class<?> beanClass)
	{
		return Advice.class.isAssignableFrom (beanClass)|| Pointcut.class.isAssignableFrom (beanClass)|| Advisor.class.isAssignableFrom (beanClass);
	}

	@Override
	public Object getEarlyBeanReference (Object bean , String beanName) {
		earlyProxyReferences.add (beanName);
		return wrapIfNecessary (bean,beanName);
	}
}
