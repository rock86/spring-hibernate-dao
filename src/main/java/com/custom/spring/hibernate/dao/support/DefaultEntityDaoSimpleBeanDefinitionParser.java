package com.custom.spring.hibernate.dao.support;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.custom.spring.hibernate.annotation.EntityDao;
import com.custom.spring.hibernate.common.DaoEntityBeanNameGenerator;
import com.custom.spring.hibernate.common.ScanClassUtils;
import com.custom.spring.hibernate.common.ScanClassUtils.AnnotationTypeFilter;
import com.custom.spring.hibernate.dao.impl.DefaultEntityDaoImpl;

/**
 * 
 * @author admin
 * @date 2016年1月10日 上午12:39:42
 */
public class DefaultEntityDaoSimpleBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {

		String basePackageAttribute = element.getAttribute("base-package");
		if (StringUtils.isEmpty(basePackageAttribute))
			return null;

		String[] basePackages = StringUtils.split(basePackageAttribute, ",");
		for (String basePackage : basePackages) {
			List<Class<Object>> entityClasses = ScanClassUtils.findClassByPkg(basePackage, new AnnotationTypeFilter(
					EntityDao.class));
			for (Class<Object> entityClass : entityClasses) {
				registerBeanDefinition(entityClass, parserContext);
			}
		}
		return null;
	}

	protected void registerBeanDefinition(Class<?> entityClass, ParserContext parserContext) {
		String beanName = DaoEntityBeanNameGenerator.generate(entityClass);
		String[] aliases = new String[0];
		AbstractBeanDefinition definition = parseInternal(entityClass, parserContext);
		BeanDefinitionHolder holder = new BeanDefinitionHolder(definition, beanName, aliases);
		BeanDefinitionReaderUtils.registerBeanDefinition(holder, parserContext.getRegistry());
	}

	protected final AbstractBeanDefinition parseInternal(Class<?> entityClass, ParserContext parserContext) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
		builder.getRawBeanDefinition().setBeanClass(DefaultEntityDaoImpl.class);
		if (parserContext.isNested()) {
			builder.setScope(parserContext.getContainingBeanDefinition().getScope());
		}
		if (parserContext.isDefaultLazyInit()) {
			builder.setLazyInit(true);
		}
		builder.addDependsOn("customHibernateDaoSupport");
		builder.addConstructorArgValue(entityClass);
		return builder.getBeanDefinition();
	}
}
