package com.beanbox.controller;



/**
 * @author: @zyz
 */

public class Wife {

	private String name;
	private Husband husband;
	public String getName () {
		return name;
	}

	public Husband getHusband () {
		return husband;
	}

	public String queryHusband() {
		return "husband name: " + husband.getName ();
	}
}
