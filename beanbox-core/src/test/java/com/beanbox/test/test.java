package com.beanbox.test;

import com.beanbox.test.service.ApiTest;

/**
 * @author: @zyz
 */
public class test {
	public static void main (String[] args) {
		Class < ApiTest > testClass = ApiTest.class;
		System.out.println (testClass.getSimpleName ());

	}
}
