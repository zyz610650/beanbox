package com.beanbox.aop.support;

/**
 * @author: @zyz
 */
public class TargetSource {

	private final Object target;

	public TargetSource (Object target) {
		this.target = target;
	}

	/**
	 * 返回目标类继承的接口
	 * @return
	 */
	public Class < ? >[] getTargerClass(){
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
