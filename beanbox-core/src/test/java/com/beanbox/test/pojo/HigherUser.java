package com.beanbox.test.pojo;

import lombok.Data;

/**
 * @author: @zyz
 */
@Data
public class HigherUser {
	String str="ad";
	int a=2;
	private String name;
		public static void main(String[] args) {

	HigherUser higherUser=new HigherUser();
	higherUser.f();
	}
	public void f()
	{


		TeacherService teacherService=new TeacherService() {
			@Override
			public void queryUserInfo() {
	;
				System.out.println(a);
				a=3;
				System.out.println(str);
			}
		};
		str="mzd";
		a=4;
		teacherService.queryUserInfo();
		System.out.println(a);

	}

}
