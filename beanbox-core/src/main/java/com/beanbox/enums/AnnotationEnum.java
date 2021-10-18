package com.beanbox.enums;


import com.beanbox.annotation.*;
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
	COMPONENT(Component.class),
	COMPONENTSCAN(ComponentScan.class),
	SCOPE(Scope.class);

	private final Class<? extends Annotation> value;

	public static List <Class < ? extends Annotation >> getAnnotations()
	{
		List <Class < ? extends Annotation >>list = new ArrayList <> ();

		list.add (AUTOWIRED.getValue ());
		list.add (COMPONENT.getValue ());
		list.add (COMPONENTSCAN.getValue ());
		list.add (SCOPE.getValue ());
		return list;
	}
}
