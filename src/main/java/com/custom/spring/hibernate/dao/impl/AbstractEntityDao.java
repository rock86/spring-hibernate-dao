package com.custom.spring.hibernate.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.custom.spring.hibernate.common.GenericsUtils;
import com.custom.spring.hibernate.dao.EntityDao;

/**
 * 
 * @author admin
 * @date 2016年1月10日 上午12:40:02 
 * @param <T>
 * @param <ID>
 */
@Repository
@SuppressWarnings({ "unchecked" })
public abstract class AbstractEntityDao<T, ID extends Serializable> implements EntityDao<T, ID> {

	@Autowired
	@Qualifier("customHibernateDaoSupport")
	private HibernateDaoSupport daoSupport;

	private Class<T> entityClass;

	public AbstractEntityDao() {
	}

	public AbstractEntityDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public Class<T> getEntityClass() {
		if (entityClass == null) {
			entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
		}
		return entityClass;
	}

	protected HibernateTemplate getHibernateTemplate() {
		return daoSupport.getHibernateTemplate();
	}

	public SessionFactory getSessionFactory() {
		return daoSupport.getSessionFactory();
	}

	public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	@Override
	public Serializable save(Object entity) {
		return getHibernateTemplate().save(entity);
	}

	@Override
	public void delete(final ID id) {
		if (id instanceof String || id instanceof Long) {
			getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
				@Override
				public Integer doInHibernate(Session session) throws HibernateException {
					String sql = "delete from " + getTableName() + " where " + getIdName() + " = :id";
					return session.createSQLQuery(sql).setParameter("id", id).executeUpdate();
				}
			});
		} else {
			getHibernateTemplate().delete(this.get(id));
		}
	}

	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(getEntityClass());
		return meta.getIdentifierPropertyName();
	}

	public String getTableName() {
		AbstractEntityPersister meta = (AbstractEntityPersister) getSessionFactory().getClassMetadata(getEntityClass());
		return meta.getTableName();
	}

	public void batchDelete(ID[] ids) {
		final StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (ID id : ids) {
			sb.append("'").append(id).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");

		getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				return session.createSQLQuery("delete from " + getTableName() + " where " + getIdName() + " in " + sb)
						.executeUpdate();
			}
		});
	}

	public T get(ID id) {
		return getHibernateTemplate().get(this.getEntityClass(), id);
	}

	public List<T> getAll() {
		String className = getEntityClass().getSimpleName();
		return new ArrayList<T>(new LinkedHashSet<T>((List<T>) getHibernateTemplate().find("from " + className)));
	}

	@Override
	public T findUniqueBy(final Criterion... criterions) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(getEntityClass());
				if (ArrayUtils.isNotEmpty(criterions)) {
					for (Criterion c : criterions) {
						criteria.add(c);
					}
				}
				return (T) criteria.uniqueResult();
			}
		});
	}

	public List<T> findBy(final Criterion... criterions) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(getEntityClass());
				if (ArrayUtils.isNotEmpty(criterions)) {
					for (Criterion c : criterions) {
						criteria.add(c);
					}
				}
				return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			}
		});
	}

	public List<T> findBy(final Criterion[] criterions, final Order... orders) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(getEntityClass());
				if (ArrayUtils.isNotEmpty(criterions)) {
					for (Criterion c : criterions) {
						criteria.add(c);
					}
				}

				if (ArrayUtils.isNotEmpty(orders)) {
					for (Order order : orders) {
						criteria.addOrder(order);
					}
				}
				return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			}
		});
	}

	public List<T> findBy(final int offset, final int length, final Criterion... criterions) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(getEntityClass());
				if (ArrayUtils.isNotEmpty(criterions)) {
					for (Criterion c : criterions) {
						criteria.add(c);
					}
				}
				return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setFirstResult(offset)
						.setMaxResults(length).list();
			}
		});
	}

	public List<T> findBy(final int offset, final int length, final Criterion[] criterions, final Order... orders) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(getEntityClass());
				if (ArrayUtils.isNotEmpty(criterions)) {
					for (Criterion c : criterions) {
						criteria.add(c);
					}
				}

				if (ArrayUtils.isNotEmpty(orders)) {
					for (Order order : orders) {
						criteria.addOrder(order);
					}
				}
				return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setFirstResult(offset)
						.setMaxResults(length).list();
			}
		});
	}

	@Override
	public long count() {
		String className = getEntityClass().getSimpleName();
		String hql = "select count(1) from " + className;
		List<Long> list = (List<Long>) this.getHibernateTemplate().find(hql);
		return list.get(0);
	}

	@Override
	public void saveOrUpdate(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void flush() {
		getHibernateTemplate().flush();
	}

	@Override
	public void clear() {
		getHibernateTemplate().clear();
	}

	@Override
	public void update(Object entity) {
		getHibernateTemplate().update(entity);
	}
}
