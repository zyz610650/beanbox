package com.beanbox.beans.annotation.register;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author: @zyz
 *
 *  添加的注解和@Bean的作用一致
 */
public interface AnnotationRegistryFactory {

	/**
	 * 对外提供添加需要扫描的注解的接口
	 * @param annotype
	 */
	void registAnnotation(Class<? extends Annotation > annotype);


	/**
	 * 获得所有需要扫描的注解
	 * @return
	 */
	List<Class<? extends Annotation>> getAllAnnotations();
}