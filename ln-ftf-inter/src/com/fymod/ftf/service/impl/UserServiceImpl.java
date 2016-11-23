package com.fymod.ftf.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fymod.ftf.config.ResultBase;
import com.fymod.ftf.dao.ClientUserDAO;
import com.fymod.ftf.domain.ClientUser;
import com.fymod.ftf.service.UserService;
import com.fymod.ftf.util.CommonUtil;

@Service("userService") @Transactional
public class UserServiceImpl implements UserService {

	@Autowired private ClientUserDAO clientUserDAO;
	
	@Override
	public ResultBase registe(String imei, String mobile, String nickname, String password,
			Integer type, String uid, String tid, String token) {
		ResultBase ret = new ResultBase();
		ClientUser userH = clientUserDAO.search(mobile);
		if(userH == null) {
			ClientUser user = new ClientUser();
			user.setImei(imei);
			user.setName(mobile);
			user.setNickname(nickname);
			user.setPassword(password);
			user.setType(type);
			user.setUid(uid);
			user.setTid(tid);
			user.setToken(token);
			try {
				ret.setResult(ResultBase.RESULT_SUCC);
				clientUserDAO.save(user);
			} catch(Exception e) {
				ret.setResult(ResultBase.RESULT_FAIL);
				ret.setMessage("注册失败：" + e.getMessage());
			}
		} else {
			ret.setResult(ResultBase.RESULT_FAIL);
			ret.setMessage("注册失败：该手机号已存在");
		}
		return ret;
	}

	@Override
	public ResultBase login(String mobile, String password) {
		ResultBase ret = new ResultBase();
		ClientUser clientUser = clientUserDAO.search(mobile, password);
		if(clientUser == null) {
			ret.setResult(ResultBase.RESULT_FAIL);
			ret.setMessage("用户名或密码不正确");
		} else {
			JSONObject json = new JSONObject();
			json.put("tid", clientUser.getTid());
			json.put("token", clientUser.getToken());
			json.put("uid", clientUser.getUid());
			json.put("id", clientUser.getId());
			ret.setObj(json);
			ret.setResult(ResultBase.RESULT_SUCC);
		}
		return ret;
	}

	@Override @Transactional
	public ResultBase updateToken(Long id, String token) {
		ResultBase ret = new ResultBase();
		ClientUser user = clientUserDAO.findById(id);
		if(user == null) {
			ret.setResult(ResultBase.RESULT_FAIL);
			ret.setMessage("用户不存在");
		} else {
			ret.setResult(ResultBase.RESULT_SUCC);
			user.setToken(token);
			clientUserDAO.update(user);
		}
		return ret;
	}

	@Override
	public ResultBase getUserInfo(Long id) {
		ResultBase ret = new ResultBase();
		ClientUser user = clientUserDAO.findById(id);
		ret.setResult(ResultBase.RESULT_SUCC);
		ret.setObj(user);
		return ret;
	}
	
	@Override
	public ResultBase getUserInfoByMobile(String mobile) {
		ResultBase ret = new ResultBase();
		ClientUser user = clientUserDAO.search(mobile);
		if(user == null) {
			ret.setResult(ResultBase.RESULT_FAIL);
		} else {
			ret.setResult(ResultBase.RESULT_SUCC);
			ret.setObj(user);
		}
		return ret;
	}

	@Override
	public ResultBase updatePwd(String mobile, String newPassword) {
		ResultBase ret = new ResultBase();
		ClientUser user = clientUserDAO.search(mobile);
		if(user == null) {
			ret.setResult(ResultBase.RESULT_FAIL);
			ret.setMessage("用户不存在");
		} else {
			user.setPassword(newPassword);
			clientUserDAO.update(user);
			ret.setResult(ResultBase.RESULT_SUCC);
		}
		return ret;
	}

	@Override
	public ResultBase sendMessage(String mobile) {
		ResultBase ret = new ResultBase();
		ClientUser users = clientUserDAO.search(mobile);
		if(users == null) {
			ret.setResult(ResultBase.RESULT_FAIL);
			ret.setMessage("用户不存在");
		} else {
			//不真的发送验证码了，没钱
//			TemplateSMS.send(mobile, "1", new String[]{"123456", "10"});
			ret.setResult(ResultBase.RESULT_SUCC);
			ret.setObj("123456");
		}
		return ret;
	}

	@Override
	public ResultBase getContact(Long id) {
		List<ClientUser> users = clientUserDAO.searchPaginatedResult();
		ResultBase ret = new ResultBase();
		ret.setResult(ResultBase.RESULT_SUCC);
		ret.setObj(users);
		return ret;
	}

	@Override
	public ResultBase getBoxNum(String deviceId) {
		ResultBase ret = new ResultBase();
		ClientUser clientUser = clientUserDAO.searchByDeviceId(deviceId);
		if(clientUser == null) { //没有这个用户，需要注册
			ret.setResult(ResultBase.RESULT_FAIL);
			String boxNum = clientUserDAO.search();
			if(!CommonUtil.notEmpty(boxNum) || Long.parseLong(boxNum) < 10000010) {
				boxNum = "10000010";
			} else {
				boxNum = (Long.parseLong(boxNum) + 1) + "";
			}
			ret.setObj(boxNum);
		} else {
			ret.setResult(ResultBase.RESULT_SUCC);
			ret.setObj(clientUser.getName());
		}
		return ret;
	}

}
