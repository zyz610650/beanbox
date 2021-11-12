package com.beanbox.test.circleDependence;

import lombok.Data;

/**
 * @author: @zyz
 */
@Data
public class Husband {

	private Wife wife;
	private String name;

	public String queryWife() {
		return "wife name: " + wife.getName ();
	}
}
