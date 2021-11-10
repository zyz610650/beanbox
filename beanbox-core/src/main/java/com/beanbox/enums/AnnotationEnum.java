package com.beanbox.enums;


import com.beanbox.beans.annotation.Autowired;

import com.beanbox.beans.annotation.Bean;
import com.beanbox.beans.annotation.BeanScan;
import com.beanbox.beans.annotation.Scope;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: @zyz
 */
@AllArgsConstructor
@Getter
public enum AnnotationEnum {


	AUTOWIRED(Autowired.class),
	BEAN(Bean.class),
	BEANSCAN(BeanScan.class),
	SCOPE(Scope.class);

	private final Class<? extends Annotation> value;

	public static List <Class < ? extends Annotation >> getAnnotations()
	{
		List <Class < ? extends Annotation >>list = new ArrayList <> ();

		list.add (AUTOWIRED.getValue ());
		list.add (BEAN.getValue ());
		list.add (BEANSCAN.getValue ());
		list.add (SCOPE.getValue ());
		return list;
	}
}
