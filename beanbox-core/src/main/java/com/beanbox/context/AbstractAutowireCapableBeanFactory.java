package com.beanbox.context;

import com.beanbox.beans.factory.AbstractBeanFactory;
import com.beanbox.beans.factory.AutowireCapableBeanFactory;
import com.beanbox.beans.instance.InstantiationService;
import com.beanbox.beans.instance.support.CglibInstantiationServiceSupprot;

/**
 * @author: @zyz
 */
public class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

	private InstantiationService instantiationService=new CglibInstantiationServiceSupprot ();

}
