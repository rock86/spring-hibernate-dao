package com.custom.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
/**
 * 
 * @author admin
 * @date 2016年1月10日 上午12:40:13 
 * @param <T>
 * @param <ID>
 */
public interface EntityDao<T, ID extends Serializable> {
	public Class<T> getEntityClass();

	public String getGenricEntityName();

	public Session getSession();

	public SessionFactory getSessionFactory();

	public Serializable save(Object entity);

	public void delete(ID id);

	public void batchDelete(ID[] ids);

	public T get(ID id);

	public List<T> getAll();

	public T findUniqueBy(final Criterion... criterions);

	public List<T> findBy(final Criterion... criterions);

	public List<T> findBy(final Criterion[] criterions, final Order... orders);

	public List<T> findBy(final int offset, final int length, final Criterion... criterions);

	public List<T> findBy(final int offset, final int length, final Criterion[] criterions, final Order... orders);

	public long count();

	public void saveOrUpdate(Object entity);

	public void flush();

	public void clear();

	public void update(Object entity);
}