package com.beanbox.test.annotation;

import com.beanbox.beans.annotation.Scope;
import com.beanbox.enums.ScopeEnum;

/**
 * @author: @zyz
 */
@Scope
public class ScopeTest {
	public static void main (String[] args) {

		Class clazz= ScopeTest.class;
		Scope scopeAnnotation = (Scope) clazz.getAnnotation (Scope.class);
		ScopeEnum value = scopeAnnotation.value ();
		System.out.println (value);

		String beanName=ScopeTest.class.getSimpleName ();
		System.out.println (beanName.substring (0,1).toLowerCase ().charAt (0));
		beanName=beanName.replace (beanName.charAt (0),beanName.substring (0,1).toLowerCase ().charAt (0));
		System.out.println (ScopeTest.class.getSimpleName ());
		System.out.println (beanName);
	}
}
