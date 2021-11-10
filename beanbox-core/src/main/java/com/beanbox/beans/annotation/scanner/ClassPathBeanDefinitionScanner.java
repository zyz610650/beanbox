package com.beanbox.beans.annotation.scanner;

import cn.hutool.core.util.StrUtil;
import com.beanbox.beans.annotation.Bean;
import com.beanbox.beans.annotation.Scope;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.beans.registry.BeanDefinitionRegistry;
import com.beanbox.enums.ScopeEnum;

import java.util.Set;

/**
 * @author: @zyz
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateBeanProvider{

	private BeanDefinitionRegistry registry;

	public ClassPathBeanDefinitionScanner (BeanDefinitionRegistry registry) {
		this.registry = registry;
	}

	/**
	 * 扫描并注册BeanDefinition
	 * @param basePackages
	 */
	public void doScan(String... basePackages){
		for (String basePackage : basePackages)
		{
			Set < BeanDefinition > candidates=findCandidateBeans (basePackage);
			for (BeanDefinition beanDefinition: candidates){
				//解析BeanDefinition的作用域
				ScopeEnum scopeEnum = resolveBeanScope (beanDefinition);
				// 默认单例
				if (scopeEnum==null) scopeEnum=ScopeEnum.SINGLETON;
				beanDefinition.setScope (scopeEnum.getTypes ());

				// 将BeanDefinition注入IOC容器
				registry.registerBeanDefinition (determineBeanName (beanDefinition),beanDefinition);
			}
		}

	}

	/**
	 * 解析BeanDefinition的Scope
	 * @param beanDefinition
	 * @return
	 */
	private ScopeEnum resolveBeanScope(BeanDefinition beanDefinition)
	{
		Class<?> beanClass = beanDefinition.getBeanClass ();
		Scope scopeAnnotation = beanClass.getAnnotation (Scope.class);
		if (scopeAnnotation==null) return null;
		return scopeAnnotation.value ();
	}

	/**
	 * 获得Bean的名字
	 * @param beanDefinition
	 * @return
	 */
	private String determineBeanName(BeanDefinition beanDefinition)
	{
		Class<?> beanClass = beanDefinition.getBeanClass ();
		String value=beanClass.getAnnotation (Bean.class).value ();
		if (StrUtil.isEmpty (value))
		{
			value=StrUtil.lowerFirst (beanClass.getSimpleName ());
		}
		return value;
	}
}
