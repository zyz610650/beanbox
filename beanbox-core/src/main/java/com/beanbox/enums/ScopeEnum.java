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
	 * 单例
	 */
	PROTOTYPE("prototype"),
	/**
	 * 原型
	 */
	SINGLETON("singleton");

	private String types;

}
