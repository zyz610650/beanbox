package com.beanbox.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: @zyz
 */
@AllArgsConstructor
@Getter
public enum ScopeEnum {

	/**
	 * εδΎ
	 */
	PROTOTYPE("prototype"),
	/**
	 * εε
	 */
	SINGLETON("singleton");

	private String types;

}
