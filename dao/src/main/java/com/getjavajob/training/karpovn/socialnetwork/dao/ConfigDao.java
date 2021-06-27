package com.getjavajob.training.karpovn.socialnetwork.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.getjavajob.training.karpovn.socialnetwork.dao")
public class ConfigDao {

	@Bean
	DataSource dataSource() throws NamingException {
		Context context = new InitialContext();
		Context envContext = (Context) context.lookup("java:/comp/env");
		return (DataSource) envContext.lookup("jdbc/webapp");
	}

	@Bean(name = "entityManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean factoryBean =  new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPersistenceXmlLocation("classpath:persistence.xml");
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan("com.getjavajob.training.karpovn.socialnetwork.common");
		factoryBean.setJpaVendorAdapter(vendorAdaptor());
		return factoryBean;
	}

	private HibernateJpaVendorAdapter vendorAdaptor() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		vendorAdapter.setShowSql(true);
		return vendorAdapter;
	}
}
