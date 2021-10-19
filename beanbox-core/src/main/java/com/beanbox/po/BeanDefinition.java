package com.beanbox.po;

import com.beanbox.enums.ScopeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: @zyz
 * 保存解析的类
 */
@Data
@NoArgsConstructor
public class BeanDefinition {

	private Class beanClass;


	public BeanDefinition (Class beanClass) {
		this.beanClass = beanClass;
	}





}
