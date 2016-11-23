package com.fymod.ftf.service;

import com.fymod.ftf.config.ResultBase;

public interface UserService {

	/**
	 * @param imei
	 * @param mobile 手机号 机顶盒id
	 * @param password 密码
	 * @param type 类型 1安卓手机 2机顶盒
	 */
	public ResultBase registe(String imei, String mobile, String nickname, String password, Integer type, String uid, String tid, String token);
	
	public ResultBase login(String mobile, String password);
	
	public ResultBase updateToken(Long id, String token);
	
	public ResultBase getUserInfo(Long id);
	
	public ResultBase getUserInfoByMobile(String mobile);
	
	public ResultBase updatePwd(String mobile, String newPassword);
	
	public ResultBase sendMessage(String mobile);
	
	public ResultBase getContact(Long id);
	
	public ResultBase getBoxNum(String deviceId);
	
}
