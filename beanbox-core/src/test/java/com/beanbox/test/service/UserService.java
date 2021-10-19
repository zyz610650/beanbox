package com.beanbox.test.service;

import com.beanbox.test.pojo.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: @zyz
 */
@Slf4j
@Data
public class UserService {
	private User user;
	public void queryUserInfo()
	{
		log.debug ("查询用户信息");
	}

}
