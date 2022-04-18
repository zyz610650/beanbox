package com.beanbox.test.pojo;

import com.beanbox.beans.annotation.Bean;
import lombok.Getter;
import lombok.SneakyThrows;

/**
 * @author: @zyz
 */
@Bean("userService")
@Getter
public class UserService  implements IUserService{

	private String uId;
	private String company;
	private String location;
//	private IUserDao userDao;
	private String token;



	@SneakyThrows
	@Override
	public String queryUserInfo() {
//		System.out.println ("进入到 queryUserInfo()");
//		Thread.sleep (2000);
//		System.out.println ("token: "+token);
//		System.out.println ("执行 queryUserInfo()");
//		return "执行 queryUserInfo()";
//	return userDao.queryUserName(uId) + "," + company + "," + location;

//		return uId + "," + company + "," + location+" token: "+token;
		return "hello, 我需要被代理";
	}
}
