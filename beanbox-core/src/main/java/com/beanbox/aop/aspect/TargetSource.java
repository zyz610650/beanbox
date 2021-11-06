package com.beanbox.aop.aspect;

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
}
