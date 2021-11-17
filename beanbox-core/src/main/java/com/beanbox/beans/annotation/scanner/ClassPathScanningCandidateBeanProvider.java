package com.beanbox.beans.annotation.scanner;

import cn.hutool.core.util.ClassUtil;
import com.beanbox.beans.annotation.Bean;
import com.beanbox.beans.annotation.register.DefaultAnnotationRegistryFactory;
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

	DefaultAnnotationRegistryFactory defaultAnnotationRegistryFactory=new DefaultAnnotationRegistryFactory ();

	/**
	 * 扫描basePackage包下的所有带Bean注解的类
	 * @param basePackage
	 * @return
	 */
	public Set < BeanDefinition> findCandidateBeans(String basePackage)
	{
		Set<BeanDefinition> candidates=new LinkedHashSet <> ();
		/**
		 * 获取所有需要扫描的注解
		 */
		List<Class<? extends Annotation >> annotionList=defaultAnnotationRegistryFactory.getAllAnnotations ();

		//扫描basePackage包下的所有带Bean注解的类
		for (Class < ? extends Annotation > annotation:annotionList)
		{
			Set<Class<?>> classes =ClassUtil.scanPackageByAnnotation (basePackage,annotation);
			for (Class<?> clazz : classes){
				candidates.add (new BeanDefinition (clazz));
			}
		}
		return candidates;
	}
}
