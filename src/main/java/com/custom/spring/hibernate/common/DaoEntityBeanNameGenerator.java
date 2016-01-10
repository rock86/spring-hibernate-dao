package com.custom.spring.hibernate.common;

import org.apache.commons.lang3.StringUtils;

import com.custom.spring.hibernate.annotation.EntityDao;

/**
 * 
 * @author admin
 * @date 2016年1月10日 上午12:40:29
 */
public class DaoEntityBeanNameGenerator {
	public static String generate(Class<?> entityClass) {
		EntityDao dao = entityClass.getAnnotation(EntityDao.class);
		if (dao == null || "".equals(dao.value())) {
			return StringUtils.uncapitalize(entityClass.getSimpleName()) + "Dao";
		} else {
			return dao.value();
		}
	}
}
