package com.beanbox.beans.annotation.scanner;

import cn.hutool.core.util.ClassUtil;
import com.beanbox.beans.po.BeanDefinition;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
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
	public Set < BeanDefinition> findCandidateBeans(String basePackage,  	List < Class < ? extends Annotation > > annotationList)
	{
		Set<BeanDefinition> candidates=new LinkedHashSet <> ();

		//扫描basePackage包下的所有带注解的类 包括@Bean 和自定义注解
		for (Class < ? extends Annotation > annotation:annotationList)
		{
			Set<Class<?>> classes =ClassUtil.scanPackageByAnnotation (basePackage,annotation);
			for (Class<?> clazz : classes){
				candidates.add (new BeanDefinition (clazz));
			}
		}
		return candidates;
	}
}
