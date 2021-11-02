package com.beanbox.beans.po;

import com.beanbox.beans.sessions.PropertyValueSession;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.cglib.beans.BeanMap;

/**
 * @author: @zyz
 * 保存解析的类
 * beanClass: 类的类型
 * propertyValueSession: 该Bean所依赖的类集合
 */
@Data
@NoArgsConstructor
public class BeanDefinition {

	/**
	 * bean的Class对象
	 */
	private Class beanClass;
	/**
	 * bean中的依赖属性
	 */
	private PropertyValueSession propertyValueSession;
	/**
	 * bean所对应的初始化方法
	 */
	private String initMethodName;
	/**
	 * bean销毁时所调用的方法
	 */
	private String destroyMethodName;


	public BeanDefinition (Class beanClass) {
		this.beanClass = beanClass;
		this.propertyValueSession=new PropertyValueSession ();
	}

	public BeanDefinition (Class beanClass , PropertyValueSession propertyValueSession) {
		this.beanClass = beanClass;
		this.propertyValueSession = propertyValueSession!=null?propertyValueSession:new PropertyValueSession ();
	}

	public BeanDefinition (Class beanClass , PropertyValueSession propertyValueSession , String initMethodName , String destroyMethodName) {
		this.beanClass = beanClass;
		this.propertyValueSession = propertyValueSession;
		this.initMethodName = initMethodName;
		this.destroyMethodName = destroyMethodName;
	}
}
