package com.beanbox.test.pojo;

import lombok.Getter;

/**
 * @author: @zyz
 */
@Getter
public class UserService implements IUserService{

	private String uId;
	private String company;
	private String location;
	private IUserDao userDao;



	@Override
	public String queryUserInfo() {
		System.out.println ("执行 queryUserInfo()");
		return "执行 queryUserInfo()";
//		return userDao.queryUserName(uId) + "," + company + "," + location;
	}
}
