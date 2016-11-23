package com.fymod.ftf.dao;

import org.springframework.stereotype.Repository;

import com.fymod.ftf.domain.ClientUser;

@Repository("clientUserDAO")
public class ClientUserDAO extends AbstractDAO<ClientUser> {

	public ClientUser search(String mobile) {
		return (ClientUser) getSession().createQuery("from ClientUser where name=:mobile")
		.setParameter("mobile", mobile)
		.setMaxResults(1)
		.uniqueResult();
	}
	
	public ClientUser searchByDeviceId(String deviceId) {
		return (ClientUser) getSession().createQuery("from ClientUser where imei=:deviceId and type=2")
		.setParameter("deviceId", deviceId)
		.setMaxResults(1)
		.uniqueResult();
	}
	
	public ClientUser search(String mobile, String password) {
		return (ClientUser) getSession().createQuery("from ClientUser where name=:mobile and password=:password")
		.setParameter("mobile", mobile)
		.setParameter("password", password)
		.setMaxResults(1)
		.uniqueResult();
	}
	
	public String search() {
		return (String) getSession().createQuery("select max(name) from ClientUser where type=2")
		.setMaxResults(1)
		.uniqueResult();
	}
	
}
