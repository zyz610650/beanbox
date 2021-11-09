package com.beanbox.test.proxy;

import com.beanbox.test.proxy.IUserDao;
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



	public String queryUserInfo() {
//		return userDao.queryUserName(uId) + "," + company + "," + location;
//		System.out.println ("I am queryUserInfo");
		return "queryUserInfo";
	}

	@Override
	public String register (String userName) {
		return null;
	}
}
