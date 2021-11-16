package com.beanbox.controller;

import java.util.List;

/**
 * @author: @zyz
 */
public interface AccountService {

	void save(Account account); //保存账户数据
	List <Account> findAll(); //查询账户数据
}
