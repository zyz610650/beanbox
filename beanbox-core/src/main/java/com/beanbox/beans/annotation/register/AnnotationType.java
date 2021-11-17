package com.beanbox.beans.annotation.register;

import java.lang.annotation.Annotation;

/**
 * @author: @zyz
 *
 */
public class AnnotationType {
	private Class < ? extends Annotation >  annotation;

	public AnnotationType (Class < ? extends Annotation >  annotation) {
		this.annotation = annotation;
	}

	public Class < ? extends Annotation >  getAnnotation () {
		return annotation;
	}
}
