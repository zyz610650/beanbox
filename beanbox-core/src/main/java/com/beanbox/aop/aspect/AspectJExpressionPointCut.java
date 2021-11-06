package com.beanbox.aop.aspect;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: @zyz
 *
 *  切面
 */
public class AspectJExpressionPointCut implements Pointcut, ClassFilter, MethodMatcher {

	private static final Set < PointcutPrimitive > SUPPORTED_PRIMITIVES=new HashSet <> ();
	static {
		SUPPORTED_PRIMITIVES.add (PointcutPrimitive.EXECUTION);
	}

	private final PointcutExpression pointcutExpression;

	public AspectJExpressionPointCut (String expression) {
		// 获得支持SUPPORTED_PRIMITIVES中所有表达式的切点解析器
		PointcutParser pointcutParser=PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution (SUPPORTED_PRIMITIVES,this.getClass ().getClassLoader ());
		// //根据expression表达式生成一个切点表达式
		this.pointcutExpression = pointcutParser.parsePointcutExpression (expression);
	}

	/**
	 * ClassFilter的mathces
	 * @param clazz
	 * @return
	 */
	@Override
	public boolean matches (Class < ? > clazz) {
		//检查clazz是否匹配表达式
		return pointcutExpression.couldMatchJoinPointsInType (clazz);
	}

	/**
	 * MethodMatcher的matches
	 * @param method
	 * @param targetClass
	 * @return
	 */
	@Override
	public boolean matches (Method method , Class < ? > targetClass) {
		// 匹配方法 matchesMethodExecution(method)
		// alwaysMatches() 是否匹配到方法
		return pointcutExpression.matchesMethodExecution (method).alwaysMatches ();

	}

	@Override
	public ClassFilter getClassFilter () {
		return null;
	}

	@Override
	public MethodMatcher getMethodMatcher () {
		return null;
	}
}
