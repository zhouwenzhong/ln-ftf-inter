package com.fymod.ftf.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.fymod.ftf.config.PageModel;
import com.fymod.ftf.util.CommonUtil;

public abstract class AbstractDAO<T> {
	
	static Logger logger = Logger.getLogger(AbstractDAO.class);
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
	//事务必须是开启的，否则获取不到
		return sessionFactory.getCurrentSession();
	} 

    private Class<T> entityClass;
    
    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        this.entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    public void save(T o){
        getSession().save(o);
    }
    
    public void update(T o){
        getSession().update(o);
    }
    
    public void saveOrUpdate(T o){
        getSession().saveOrUpdate(o);
    }
    
    public void delete(Serializable id){
        getSession().delete(findById(id));
    }
    
    @SuppressWarnings("unchecked")
	public T findById(Serializable id){
        return (T) getSession().get(entityClass, id);
    }
    
    @SuppressWarnings("unchecked")
    public  List<T> searchPaginatedResult(){
    	return searchPaginatedResult( null, null, null,  -1,  0).getDatas();
    }
    
    @SuppressWarnings("unchecked")
	public List<T> searchPaginatedResult(String where,String order,Map<String, Object> paramsMap){
    	return searchPaginatedResult( where, order, paramsMap,  -1,  0).getDatas();
    }
    
    public PageModel searchPaginatedResult(Integer pageIndex, Integer pagesize){
    	return searchPaginatedResult( null, null, null,  pageIndex,  pagesize);
    }
    
    public PageModel searchPaginatedResult(String where,String order,Map<String, Object> paramsMap, Integer pageIndex, Integer pagesize){
    	StringBuffer hql = new StringBuffer("from "+ this.entityClass.getName());
    	if(CommonUtil.notEmpty(where)){
    		Integer andIndex = where.indexOf("and");
    		if(andIndex == -1) andIndex = where.indexOf("AND");
    		if(-1 < andIndex && andIndex < 2) where = where.substring(andIndex+3);
    		hql.append(" where " + where);
    	}if(CommonUtil.notEmpty(order)){
    		hql.append(" order by "+StringEscapeUtils.escapeSql(order));
    	}
    	else hql.append(" order by id desc");
    	logger.debug("hql:"+hql.toString());
    	return searchPaginatedResult( hql.toString(),  paramsMap,  pageIndex,  pagesize);
    }
    
    @SuppressWarnings("unchecked")
	public  List<T> searchPaginatedResult(String hql, Map<String, Object> paramsMap){
    	return searchPaginatedResult( hql,  paramsMap,  -1,  0).getDatas();
    }
    
    public PageModel searchPaginatedResult(String hql, Map<String, Object> paramsMap, Integer pageIndex, Integer pagesize){
    	if (!CommonUtil.greaterThanZero(pageIndex))
    		pageIndex = 1;
		Integer offset = -1;
		PageModel pm = new PageModel();
    	if (CommonUtil.greaterThanZero(pagesize)){
    		offset = (pageIndex -1)*pagesize;
	    	pm.setTotal(getRecordsNumber(hql.toString(), paramsMap));
	        pm.setDatas(getRecords(hql.toString(), paramsMap, offset, pagesize));
	        pm.setMaxItems(pagesize);
    	}else{
    		List<?> list = getRecords(hql.toString(), paramsMap, -1, 0);
    		pm.setDatas(list);
    		pm.setTotal(list.size());
    		if(list.size() == 0) pm.setMaxItems(1);
    		else pm.setMaxItems(list.size());
		}
        return pm;
    }
    
    public Query setUpQuery(String where, String order, Map<String, Object> params){
    	StringBuffer hql = new StringBuffer("from "+ this.entityClass.getName());
    	if(CommonUtil.notEmpty(where)){
    		Integer andIndex = where.indexOf("and");
    		if(andIndex == -1) andIndex = where.indexOf("AND");
    		if(-1 < andIndex && andIndex < 2) where = where.substring(andIndex+3);
    		hql.append(" where " + where);
    	}if(CommonUtil.notEmpty(order)) hql.append(" order by "+order);
    	else hql.append(" order by id desc");
    	return setUpQuery( hql.toString(), params);
    }
    
    public Query setUpQuery(String hql, Map<String, Object> params){
    	if(hql.indexOf("from ") == -1 && hql.indexOf("update ") ==-1 && hql.indexOf("delete ") ==-1){
    		hql = "from "+ this.entityClass.getName() +" where "+hql;
    	}
        Query query = getSession().createQuery(hql);
        if(params != null && params.keySet().size() > 0){
            Iterator<String> it = params.keySet().iterator();
            while(it.hasNext()){
                String key = it.next();
                if(params.get(key) instanceof Collection ){
                    query.setParameterList(key, (Collection<?>)params.get(key));
                }else{
                    query.setParameter(key, params.get(key));
                }
            }
        }
        return query;
    }
    
    /** 获取总数据条数  **/
    public int getRecordsNumber(String hql, Map<String, Object> params){
        String countHql = getCountQuery(hql);
        Query query = setUpQuery(countHql, params);
        return ((Long) query.uniqueResult()).intValue();
    }
    
    private String getCountQuery(String hql){
        int index = hql.indexOf("from");
        if(index != -1){
            return "select count(*) " + hql.substring(index);
        }
        throw new RuntimeException("invalid hql");
    }
    
    private List<?> getRecords(String hql, Map<String, Object> params, Integer offset, Integer pagesize){
        Query query = setUpQuery(hql, params);
        if(offset >= 0){
        	query.setFirstResult(offset);
        	query.setMaxResults(pagesize);
        }
        return query.list();
    }
    
    @Transactional
    public T findOne(String hql, Object[] param) {
        Session sess = getSession();
        Query query = sess.createQuery(hql);
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                Object obj = param[i];
                if (obj instanceof Collection<?>) {
                    query.setParameterList(String.valueOf(i), (Collection<?>) obj);
                } else if (obj instanceof Object[]) {
                    query.setParameterList(String.valueOf(i), (Object[]) obj);
                } else {
                    query.setParameter(String.valueOf(i), obj);
                }
            }
        }
        List<T> list = query.setFirstResult(0).setMaxResults(1).list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }
    
    @Transactional
    public Long count(String hql, Object[] param) {
        Long numLong = 0l;
        Session sess = getSession();
        if (param == null) {
            numLong = (Long) sess.createQuery(hql).uniqueResult();
        } else {
            Query query = sess.createQuery(hql);
            for (int i = 0; i < param.length; i++) {
                Object obj = param[i];
                if (obj instanceof Collection<?>) {
                    query.setParameterList(String.valueOf(i), (Collection<?>) obj);
                } else if (obj instanceof Object[]) {
                    query.setParameterList(String.valueOf(i), (Object[]) obj);
                } else {
                    query.setParameter(String.valueOf(i), obj);
                }
            }
            numLong = (Long) query.uniqueResult();
        }
        return numLong;
    }
}
