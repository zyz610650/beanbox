package com.beanbox.context.suppport;


/**
 * @author: @zyz
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

	private String[] configLocations;

	public ClassPathXmlApplicationContext () {

	}

	public ClassPathXmlApplicationContext (String configLocations)
	{
		this(new String[]{configLocations});
	}

	public ClassPathXmlApplicationContext (String[] configLocations)
	{
		this.configLocations=configLocations;
		refresh ();
	}

	@Override
	protected String[] getConfigLocations () {
		return configLocations;
	}
}
