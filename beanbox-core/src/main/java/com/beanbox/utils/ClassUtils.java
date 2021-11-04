package com.beanbox.utils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: @zyz
 */
public class ClassUtils {
	/**
	 * 创建Bean
	 * @param clazz
	 * @return
	 */
	public static Object createBean(Class<?> clazz)
	{
		try {
			return clazz.getDeclaredConstructor ().newInstance ();
		} catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException ("Failed to inject object "+clazz.getCanonicalName ());
		}

	}

	/**
	 * 获得beanName
	 * beanName 为类首字母小写
	 * @param clazz
	 * @return
	 */
	public static String getBeanName(Class<?> clazz)
	{
		return StringUtils.toLowFirstChar (clazz.getSimpleName ());
	}

	/**
	 * 获得类的默认类加载器
	 * @return
	 */
	public static ClassLoader getDefaultClassLoader(){
			return Thread.currentThread ().getContextClassLoader ();

	}

	/**
	 * 判断该类是否是CGLIB生成的代理类
	 * @param clazz
	 * @return
	 */
	public static boolean isCglibProxyClass(Class<?> clazz)
	{
		if (clazz==null&&!(clazz.getName ().contains ("$$"))) return false;
		 return true;

	}
}
