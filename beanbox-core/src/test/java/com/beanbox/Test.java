package com.beanbox;

import com.annotation.Autowired;
import com.annotation.Component;
import lombok.Value;

/**
 * @author: @zyz
 */
@Value
public class Test {

	public static void main (String[] args) {
		Class<?> clazz= Autowired.class;
		System.out.println (clazz== Component.class);
		System.out.println (clazz== Autowired.class);
		String beanName=Test.class.getSimpleName ().toLowerCase ();
		System.out.println (Test.class.getSimpleName ());
		System.out.println (beanName);


	}
}
