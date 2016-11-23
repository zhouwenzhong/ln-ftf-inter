package com.fymod.ftf.controller;

import java.io.IOException;

import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) throws IOException {
		
//		String url =  "http://192.168.1.4:8080/ftf-inter/login.json";
		String url =  "http://www.fymod.com:8080/ftf-inter/login.json";
		JSONObject param = new JSONObject();
		param.put("mobile", "13500000000");
		param.put("password", "1");
//		param.put("id", "2");
		String params = param.toString();
		String result = HttpJson.testPostJson(url, params);
		System.out.println(result);
		
		
//		List list = new ArrayList();
//		list.add("");
//		list.add("");
//		
//		list.add(1, "3232");
//		
//		System.out.println(list);
		
//		String s = "{\"rsc\":{\"user\":{\"name\":\"15010563146\",\"uid\":\"15010563146_oBNDTesLS9\",\"aor\":\"15010563146_oBNDTesLS9@chinamobile.com\",\"appid\":\"oBNDTesLS9\",\"tm\":{\"tid\":\"15010563146_oBNDTesLS9_1\",\"ttype\":\"1\",\"needact\":0,\"token\":\"rEnzQ\",\"expire\":\"1458712055\"}},\"st\":{\"rspcode\":200,\"rspreason\":\"True\"}}}";
//		JSONObject json = JSONObject.fromObject(s);
//		System.out.println(json.toString());
	}
}
