package com.beanbox.aop.aspect;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * @author: @zyz
 * 封装了被代理对象的参数
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

	// 目标对象 被代理对象
	private  final Object target;

	// 方法
	private final Method method;

	//参数
	private final Object[] arguments;


//	// 责任链 解决多重代理
//	private ReflectiveMethodInvocation next;
//	private ReflectiveMethodInvocation head;
//	//	ReflectiveMethodInvocation tail;
	public ReflectiveMethodInvocation (Object target , Method method , Object[] arguments) {
		this.target = target;
		this.method = method;
		this.arguments = arguments;

	}

//	public ReflectiveMethodInvocation getNext()
//	{
//		return this.next;
//	}
//	public ReflectiveMethodInvocation appendNextMethodInvocation(ReflectiveMethodInvocation next)
//	{
//		this.next=next;
//		return this;
//	}

	@Override
	public Method getMethod () {
		return method;
	}

	@Override
	public Object[] getArguments () {
		return arguments;
	}

	/**
	 * 反射执行方法
	 * @return
	 * @throws Throwable
	 */
	@Override
	public Object proceed () throws Throwable {
		return method.invoke (target,arguments);
	}

	@Override
	public Object getThis () {
		return target;
	}

	@Override
	public AccessibleObject getStaticPart () {
		// AccessibleObject是Method、Field、Constructor类的基类
		return method;
	}
}
