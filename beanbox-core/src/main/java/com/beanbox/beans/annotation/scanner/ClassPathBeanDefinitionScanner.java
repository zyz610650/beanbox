package com.beanbox.beans.annotation.scanner;

import cn.hutool.core.util.StrUtil;
import com.beanbox.beans.annotation.Bean;
import com.beanbox.beans.annotation.Scope;
import com.beanbox.beans.annotation.register.AnnotationRegistryFactory;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.beans.processor.support.AutowiredAndValueAnnotationBeanPostProcessor;
import com.beanbox.beans.registry.BeanDefinitionRegistry;
import com.beanbox.enums.ScopeEnum;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author: @zyz
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateBeanProvider{

	private BeanDefinitionRegistry registry;
	private String ANNOTATION_REGISTRY_FACTORY_NAME="annotationRegistryFactory";

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

			 // 添加需要扫描的注解

			BeanDefinition annotationRegistryFactoryBeanDefinition = registry.getBeanDefinition (ANNOTATION_REGISTRY_FACTORY_NAME);
			List < Class < ? extends Annotation > > allAnnotations=null;
			if(annotationRegistryFactoryBeanDefinition!=null)
			{
				try {
					AnnotationRegistryFactory annotationRegistryFactory=(AnnotationRegistryFactory) annotationRegistryFactoryBeanDefinition.getBeanClass ().newInstance ();
					annotationRegistryFactory.registAnnotations ();
					allAnnotations= annotationRegistryFactory.getAllAnnotations ();
				} catch (InstantiationException | IllegalAccessException e) {
					throw new RuntimeException ("AnnotationRegistryFactory failed to be registered: ",e);
				}
			}
          // 如果没有配置 则添加Bean.class注解
			if (allAnnotations==null) allAnnotations=new LinkedList <> (Collections.singleton (Bean.class));
			Set < BeanDefinition > candidates=findCandidateBeans (basePackage,allAnnotations);
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

		// 注册@Autowired 和@Value注解处理Processor: AutowiredAndValueAnnotationBeanPostProcessor 到BeanDefinition IOC容器中
		registry.registerBeanDefinition ("com.beanbox.beans.processor.support.AutowiredAndValueAnnotationBeanPostProcessor",new BeanDefinition (AutowiredAndValueAnnotationBeanPostProcessor.class));

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
		String value=null;

		Bean beanAnnotation = beanClass.getAnnotation (Bean.class);
		if(beanAnnotation!=null)
		{
			value=beanAnnotation.value ();
		}
		if (StrUtil.isEmpty (value))
		{
			value=StrUtil.lowerFirst (beanClass.getSimpleName ());
		}
		return value;
	}
}
