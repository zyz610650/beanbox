package com.beanbox.test.circleDependence;

import lombok.Data;

/**
 * @author: @zyz
 */

public class Husband {

	public Wife getWife () {
		return wife;
	}

	public String getName () {
		return name;
	}

	private Wife wife;
	private String name;

	public String queryWife() {
		return "wife name: " + wife.getName ();
	}
}
