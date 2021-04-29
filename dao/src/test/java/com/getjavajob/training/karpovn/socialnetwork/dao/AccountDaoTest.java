/*
package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ApplicationContextDao.xml", "classpath:applicationContextDaoOver.xml"})
public class AccountDaoTest {

    @Autowired
	private AccountDao accountDao;

    @Sql(value = "classpath:createTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:fillTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:dropTables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    @Test
    public void createAccount() {
        Account user = new Account();
        user.setName("Petr");
        accountDao.createAccount(user);
        assertEquals("Petr", accountDao.readAccountById(2).getName());
    }

    @Sql(value = "classpath:createTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:fillTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:dropTables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @Transactional
    public void getImageFromDb() throws IOException, SQLException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("15.png");
        accountDao.loadPicture(1, inputStream);
        assertFalse(accountDao.getImageFromDb(1).isEmpty());
    }

    @Sql(value = "classpath:createTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:fillTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:dropTables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @Transactional
    public void checkForLogin() throws SQLException {
        Account account = new Account();
        account.setName("ivan");
        assertNotEquals(account.getName(), accountDao.checkForLogin("list@list.ru", "123").getName());
    }

    @Sql(value = "classpath:createTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:fillTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:dropTables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @Transactional
    public void updateAccount() throws SQLException {
        Account account = accountDao.readAccountById(1);
        account.setName("Maks");
        accountDao.updateAccount(account);
        assertEquals("Maks", accountDao.readAccountById(1).getName());
    }

    @Sql(value = "classpath:createTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:fillTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:dropTables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test (expected = PersistenceException.class)
    @Transactional
    public void deleteById() {
        Account user = new Account();
        user.setId(2);
        user.setName("Petr");
        accountDao.createAccount(user);
        assertEquals("Petr", accountDao.readAccountById(2).getName());
        accountDao.deleteById(2);
        assertNull(accountDao.readAccountById(2));
    }

   */
/* @Sql(value = "classpath:createTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:fillTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:dropTables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)*//*

    @Test
    @Transactional
    public void readAccountById() {
        assertEquals("ivan", accountDao.readAccountById(1).getName());
    }

    @Sql(value = "classpath:createTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:fillTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:dropTables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @Transactional
    public void loadPicture() throws SQLException, IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("15.png");
        accountDao.loadPicture(1, inputStream);
        assertFalse(accountDao.getImageFromDb(1).isEmpty());
    }

    @Sql(value = "classpath:createTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:fillTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:dropTables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @Transactional
    public void showAllAccounts() throws SQLException {
        assertEquals("ivan", accountDao.showAllAccounts().get(0).getName());
    }
}*/
