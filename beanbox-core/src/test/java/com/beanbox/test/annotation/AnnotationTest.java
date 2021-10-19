package com.beanbox.test.annotation;

import com.beanbox.test.Autowired;
import com.beanbox.test.Component;
import lombok.Value;

/**
 * @author: @zyz
 */
@Value
public class AnnotationTest {

	public static void main (String[] args) {
		Class<?> clazz= Autowired.class;
		System.out.println (clazz== Component.class);
		System.out.println (clazz== Autowired.class);
		String beanName= AnnotationTest.class.getSimpleName ().toLowerCase ();
		System.out.println (AnnotationTest.class.getSimpleName ());
		System.out.println (beanName);


	}
}
