package com.beanbox.test.pojo;

import lombok.Getter;
import lombok.SneakyThrows;

/**
 * @author: @zyz
 */
@Getter
public class UserService extends HigherUser implements IUserService{

	private String uId;
	private String company;
	private String location;
	private IUserDao userDao;



	@SneakyThrows
	@Override
	public String queryUserInfo() {
		System.out.println ("进入到 queryUserInfo()");
		Thread.sleep (2000);
		System.out.println ("执行 queryUserInfo()");
		return "执行 queryUserInfo()";
//		return userDao.queryUserName(uId) + "," + company + "," + location;
	}
}
