package com.beanbox.test.aop;

import lombok.Data;
import lombok.Getter;

/**
 * @author: @zyz
 */
@Getter
public class UserService {

	private String uId;
	private String company;
	private String location;
	private IUserDao userDao;



	public String queryUserInfo() {
		return userDao.queryUserName(uId) + "," + company + "," + location;
	}
}
