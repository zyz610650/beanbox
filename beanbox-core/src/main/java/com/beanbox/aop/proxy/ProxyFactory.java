package com.beanbox.aop.proxy;

import com.beanbox.aop.aspect.AdvisedSupport;
import com.beanbox.aop.proxy.support.CglibAopProxy;
import com.beanbox.aop.proxy.support.JdkDynamicAopProxy;

/**
 * @author: @zyz
 *
 * 解决的 JDK 和 Cglib 两种代理的选择问题
 */
public class ProxyFactory {

	private AdvisedSupport advisedSupport;

	public ProxyFactory (AdvisedSupport advisedSupport) {
		this.advisedSupport = advisedSupport;
	}

	public Object getProxy()
	{
		return createAopProxy ().getProxy ();
	}

	/**
	 * 创建动态代理对象
	 * @return
	 */
	private AopProxy createAopProxy(){
		if (advisedSupport.isProxyTargetClass ())
		{
			return new CglibAopProxy (advisedSupport);
		}

		return new JdkDynamicAopProxy (advisedSupport);
	}
}
