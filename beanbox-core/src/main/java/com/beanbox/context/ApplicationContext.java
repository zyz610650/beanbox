package com.beanbox.context;

import com.beanbox.beans.factory.HierarchicalBeanFactory;
import com.beanbox.beans.factory.ListableBeanFactory;
import com.beanbox.event.publisher.ApplicationEventPublisher;
import com.beanbox.io.loader.ResourceLoader;

/**
 * @author: @zyz
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
