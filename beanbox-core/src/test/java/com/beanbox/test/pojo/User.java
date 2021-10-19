package com.beanbox.test.pojo;

import lombok.Data;

/**
 * @author: @zyz
 */
@Data
public class User {


	String name;
	String school;
	HigherUser higherUser;
	int age;
	public User () {

	}

	public User (String name ) {
		this.name = name;

	}
	public User (String name , String school ) {
		this.name = name;
		this.school = school;

	}
	public User (String name , String school , int age) {
		this.name = name;
		this.school = school;
		this.age = age;
	}

	public User (String name , String school , HigherUser higherUser , int age) {
		this.name = name;
		this.school = school;
		this.higherUser = higherUser;
		this.age = age;
	}
}
