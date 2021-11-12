package com.beanbox.test.circleDependence;

import lombok.Data;

/**
 * @author: @zyz
 */
@Data
public class Wife {
	private String name;
	private Husband husband;

	public String queryHusband() {
		return "husband name: " + husband.getName ();
	}
}
