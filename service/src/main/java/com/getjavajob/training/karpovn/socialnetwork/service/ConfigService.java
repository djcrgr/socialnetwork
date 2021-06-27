package com.getjavajob.training.karpovn.socialnetwork.service;

import com.getjavajob.training.karpovn.socialnetwork.dao.ConfigDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.getjavajob.training.karpovn.socialnetwork.service")
@Import(ConfigDao.class)
public class ConfigService {

	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	/*@Bean
	public AccountService accountService() {
		return new AccountService(accountDao, phoneDao);
	}

	@Bean
	public GroupService groupService() {
		return new GroupService(groupDao);
	}

	@Bean
	public MessageService messageService() {
		return new MessageService(messageDao);
	}*/
}
