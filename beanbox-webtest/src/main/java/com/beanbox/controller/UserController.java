package com.beanbox.controller;

import com.beanbox.beans.annotation.Autowired;

/**
 * @author: @zyz
 */
public class UserController {

	@Autowired
	private UserService userService;

	public  void print()
	{
		userService.print ();
	}
}
