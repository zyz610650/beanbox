package com.beanbox.annotation.scanner;

import cn.hutool.core.util.ClassUtil;
import com.beanbox.annotation.Bean;
import com.beanbox.beans.po.BeanDefinition;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author: @zyz
 * 基类 提供扫描指定包下的所有带Bean注解的类
 */
public class ClassPathScanningCandidateBeanProvider {

	/**
	 * 扫描basePackage包下的所有带Bean注解的类
	 * @param basePackage
	 * @return
	 */
	public Set < BeanDefinition> findCandidateBeans(String basePackage)
	{
		Set<BeanDefinition> candidates=new LinkedHashSet <> ();
		//扫描basePackage包下的所有带Bean注解的类
		Set<Class<?>> classes =ClassUtil.scanPackageByAnnotation (basePackage, Bean.class);
		for (Class<?> clazz : classes){
			candidates.add (new BeanDefinition ());
		}
		return candidates;
	}
}
