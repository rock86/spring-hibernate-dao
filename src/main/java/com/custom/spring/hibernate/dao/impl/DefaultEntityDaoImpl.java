package com.custom.spring.hibernate.dao.impl;

import java.io.Serializable;

/**
 * 
 * @author admin
 * @date 2016年1月10日 上午12:39:54 
 * @param <T>
 * @param <ID>
 */
public class DefaultEntityDaoImpl<T, ID extends Serializable> extends AbstractEntityDao<T, ID> {

	public DefaultEntityDaoImpl(Class<T> entityClass) {
		super(entityClass);
	}

	@Override
	public String getGenricEntityName() {
		return null;
	}

}
