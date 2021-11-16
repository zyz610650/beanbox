package com.beanbox.controller;

import com.beanbox.beans.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: @zyz
 */
@Service
public class AccountServiceImpl implements AccountService {
	List<Account> list=new ArrayList <> ();
	@Override
	public void save(Account account) {
		list.add (account);
		System.out.println ("save account info: "+ account.toString ());
	}
	@Override
	public List <Account> findAll() {
		System.out.println ("查找用户信息");
		return list;
	}
}