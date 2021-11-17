package com.beanbox.web;

import com.beanbox.context.ConfigurableApplicationContext;
import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.exception.BeanException;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;

/**
 * @author: @zyz
 */
@Slf4j
public class ContextLoader {


	private ConfigurableApplicationContext context;

	private String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE="configurableContext";

	private String BEANBOX_XML_LOCATION="classpath:beanbox.xml";

	/**
	 * 初始化框架
	 * @param servletContext
	 * @return
	 */
	public ConfigurableApplicationContext initWebApplicationContext(ServletContext servletContext)
	{
		try {
			if (servletContext.getAttribute (ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE)!=null)
			{
				throw new IllegalStateException("Cannot initialize context because there is already a root application context present - check whether you have multiple ContextLoader* definitions in your web.xml!");
			}
			servletContext.log("Initializing BeanBox ");

			if (this.context == null)
			{
					this.context=new ClassPathXmlApplicationContext (BEANBOX_XML_LOCATION);
			}

			servletContext.setAttribute (ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,this.context);
		} catch (IllegalStateException e) {

			throw new BeanException ("Beanbox initialization failed, the reason is :" +e);
		}

		return this.context;
	}


	/**
	 * 关闭框架
	 * @param servletContext
	 */
	public void closeWebApplicationContext(ServletContext servletContext)
	{
		servletContext.log("Closing BeanBox");
		this.context.close ();
		servletContext.removeAttribute (ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

	}
}
