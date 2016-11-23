package com.fymod.ftf.dao;

import org.springframework.stereotype.Repository;

import com.fymod.ftf.domain.SysParam;

@Repository("paramDAO")
public class ParamDAO extends AbstractDAO<SysParam>{
    public SysParam searchParam(int code) {
        return (SysParam) getSession().createQuery("from SysParam where code=:code")
        .setParameter("code", code)
        .setMaxResults(1)
        .uniqueResult();
    }
}
