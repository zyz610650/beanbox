package com.beanbox.aop.aspect;

import com.beanbox.utils.ClassUtils;

/**
 * @author: @zyz
 */
public class TargetSource {

	/**
	 * 被代理对象
	 */
	private final Object target;

	public TargetSource (Object target) {
		this.target = target;
	}

	/**
	 * 返回目标类继承的接口
	 * @return
	 */
	public Class < ? >[] getTargerInterface(){
		return this.target.getClass ().getInterfaces ();
	}

	/**
	 * 返回目标类
	 * @return
	 */
	public Object getTarget()
	{
		return this.target;
	}

	/**
	 * 获得目标类实现的接口
	 * @return
	 */
	public Class<?>[] getTargetClass()
	{
		Class<?> clazz=this.target.getClass ();
		//防止该类是动态代理生成进行处理
		clazz= ClassUtils.isCglibProxyClass (clazz) ? clazz.getSuperclass ():clazz;
		return clazz.getInterfaces ();
	}
}
