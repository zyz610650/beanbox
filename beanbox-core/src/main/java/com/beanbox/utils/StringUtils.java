package com.beanbox.utils;

/**
 * @author: @zyz
 */
public class StringUtils {

	/**
	 * 使字符串首字母小写
	 * @param str
	 * @return
	 */
	public static String toLowFirstChar(String str)
	{
		return str.replace (str.charAt (0),str.substring (0,1).toLowerCase ().charAt (0));
	}
}
