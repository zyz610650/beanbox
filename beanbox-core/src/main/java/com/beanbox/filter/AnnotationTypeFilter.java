package com.beanbox.filter;

import com.beanbox.enums.AnnotationEnum;
import com.beanbox.utils.ConcurrentHashSet;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 *
 * @author: @zyz
 */
public class AnnotationTypeFilter {

	/**
	 * save all annotations
	 */
	private static Set<Class < ? extends Annotation >> ANNOTATION_MAP=new ConcurrentHashSet <> ();

	public AnnotationTypeFilter () {
		ANNOTATION_MAP.addAll (AnnotationEnum.getAnnotations ());
	}

	/**
	 * 添加需要扫描的Annotation
	 * @param annotation
	 */
	public static void addAnnotationType(Class < ? extends Annotation > annotation)
	{
		ANNOTATION_MAP.add (annotation);
	}

	/**
	 * 获得需要扫描的Annotation
	 * @return
	 */
	public static Set < Class < ? extends Annotation > > getAllAnnotationTypes()
	{
			return ANNOTATION_MAP;
	}
}
