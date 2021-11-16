package com.beanbox.controller;

/**
 * @author: @zyz
 */
public class Account {
	private int id;
	private String name;
	private double money;
	//省略getter和setter方法


	public int getId () {
		return id;
	}

	public void setId (int id) {
		this.id = id;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public double getMoney () {
		return money;
	}

	public void setMoney (double money) {
		this.money = money;
	}
}