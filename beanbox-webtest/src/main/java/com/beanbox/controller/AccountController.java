package com.beanbox.controller;


import com.beanbox.beans.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: @zyz
 */
@Controller
public class AccountController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserService userService;
	@RequestMapping ("/save")
	@ResponseBody
	public String save(Account account){
		userService.print ();
		accountService.save(account);
		return "save success";
	}
	@RequestMapping("/findAll")
	public ModelAndView findAll(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("accountList");
		modelAndView.addObject("accountList",accountService.findAll());
		return modelAndView;
	}
}