package com.custom.spring.hibernate.dao.support;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
/**
 * 
 * @author admin
 * @date 2016年1月10日 上午12:39:26
 */
public class DefaultEntityDaoNameSpaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("default-entity-dao-scan", new DefaultEntityDaoSimpleBeanDefinitionParser());
	}

}
