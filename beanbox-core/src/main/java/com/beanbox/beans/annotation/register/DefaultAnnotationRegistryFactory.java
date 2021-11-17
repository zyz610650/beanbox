package com.beanbox.beans.annotation.register;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: @zyz
 */
public  class DefaultAnnotationRegistryFactory implements AnnotationRegistryFactory{

	/**
	 * 缓存所有需要扫描的注解
	 */
	Set <Class<? extends Annotation>> ANNOTATION_MAP=new LinkedHashSet <> ();

	@Override
	public void registAnnotation (AnnotationType annotype) {
			ANNOTATION_MAP.add (annotype.getAnnotation ());
	}

	@Override
	public List < Class < ? extends Annotation > > getAllAnnotations () {
		return new ArrayList <> (ANNOTATION_MAP);
	}
}
