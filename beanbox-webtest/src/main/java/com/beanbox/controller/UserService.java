package com.beanbox.controller;

import com.beanbox.beans.annotation.Autowired;
import com.beanbox.beans.annotation.Bean;

/**
 * @author: @zyz
 */

@Bean
public class UserService {

	@Autowired
	private User user;

	@Autowired
	private Husband husband;

	@Autowired
	private Wife wife;

	public  void print()
	{
		System.out.println (user.toString ());
		System.out.println (husband.toString ());
		System.out.println (wife.toString ());
	}
}
