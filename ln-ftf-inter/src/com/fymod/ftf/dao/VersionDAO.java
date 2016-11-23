package com.fymod.ftf.dao;

import org.springframework.stereotype.Repository;

import com.fymod.ftf.domain.Version;

@Repository("versionDAO")
public class VersionDAO extends AbstractDAO<Version> {

	public Version search(String type) {
		return (Version) getSession().createQuery("from Version where type=:type")
		.setParameter("type", type)
		.setMaxResults(1)
		.uniqueResult();
	}

}
