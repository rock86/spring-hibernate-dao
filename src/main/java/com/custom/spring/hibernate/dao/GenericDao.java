package com.custom.spring.hibernate.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

/**
 * 
 * @author admin
 * @date 2016年1月9日 上午11:19:11
 */
public interface GenericDao {
	public Session getSession();

	public <T> List<T> findByHql(final String hql, final Object... values);

	public <T> List<T> findByHql(final String hql, final Map<?, ?> values);

	public <T> List<T> findByHql(final String hql, final int offset, final int length, final Object... values);

	public <T> List<T> findByHql(final String hql, final int offset, final int length, final Map<?, ?> valueMap);

	public <K, V> List<Map<K, V>> findBySql(final String sql, final int offset, final int length,
			final Map<?, ?> valueMap);

	public <K, V> List<Map<K, V>> findBySql(final String sql, final Map<?, ?> valueMap);

	public <T> T findUniqueByHql(final String hql, final Object... values);

	public <T> T findUniqueByHql(final String hql, final Map<?, ?> valueMap);

	public <K, V> Map<K, V> findUniqueBySql(final String sql, final Map<?, ?> valueMap);

	public long countByHql(final String hql, final Object... values);

	public long countByHql(final String hql, final Map<?, ?> valueMap);

	public long countBySql(final String sql, final Map<?, ?> valueMap);

	public <T> List<T> findByNamedQuery(final String queryName, final Map<?, ?> valueMap);

	public <T> List<T> findByNamedQuery(final String queryName, final int offset, final int length,
			final Map<?, ?> valueMap);

	public long countByNamedQuery(final String queryName, final Map<?, ?> valueMap);

	public int batchExecuteByHql(final String hql, final Object... values);

	public int batchExecuteByHql(final String hql, final Map<?, ?> valueMap);

	public int batchExecuteBySql(final String sql, final Map<?, ?> valueMap);

	public int batchExecuteByNamedQuery(final String queryName, final Map<?, ?> valueMap);
}
