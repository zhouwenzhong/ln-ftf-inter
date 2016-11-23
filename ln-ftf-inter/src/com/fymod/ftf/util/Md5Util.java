package com.fymod.ftf.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class Md5Util {
	
	public static String md5(String username,String password){
		if(username == null || password == null)
			return null;
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(password,username);
	}
	
	public static String md5Others(String password){
		if(password == null)
			return null;
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(password,null);
	}
	
}
