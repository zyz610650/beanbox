package com.beanbox.beans.annotation.register;

import com.beanbox.beans.annotation.Bean;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: @zyz
 * 需要继承此类DefaultAnnotationRegistryFactory 并且在xml文件中配置 AnnotationRegistryFactory的实现类
 */
public abstract class AbstractDefaultAnnotationRegistryFactory implements AnnotationRegistryFactory{

	/**
	 * 缓存所有需要扫描的注解
	 */
	 Set <Class<? extends Annotation>> ANNOTATION_MAP;
	{
		ANNOTATION_MAP=new LinkedHashSet <> ();
		addAnnotation (new AnnotationType (Bean.class));

	}

	@Override
	public void addAnnotation (AnnotationType annotype) {
			ANNOTATION_MAP.add (annotype.getAnnotation ());
	}

	@Override
	public List < Class < ? extends Annotation > > getAllAnnotations () {
		return new ArrayList <> (ANNOTATION_MAP);
	}


}
