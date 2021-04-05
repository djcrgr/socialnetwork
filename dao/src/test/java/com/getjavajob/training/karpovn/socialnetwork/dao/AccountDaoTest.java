package com.getjavajob.training.karpovn.socialnetwork.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ApplicationContextDao.xml", "classpath:applicationContextDaoOver.xml"})
public class AccountDaoTest {

    private Connection connection;
    @Autowired
	private AccountDao accountDao;
    @Autowired
    private DataSource dataSource;
    private InputStream inputStream;


    /*@Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("Db.properties"));
        inputStream = this.getClass().getClassLoader().getResourceAsStream("15.png");
        String create = properties.getProperty("database.createAcc");
        String createPhones = properties.getProperty("database.createPhones");
        String fill = properties.getProperty("database.fill");
        String createFriendsInDb = properties.getProperty("database.createFriends");
        this.connection = dataSource.getConnection();
        Statement statement = this.connection.createStatement();
        statement.execute(create);
//        statement.execute(fill);
        statement.execute(createFriendsInDb);
        statement.execute(createPhones);
        this.connection.close();
    }


    @After
    public void tearDown() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("Db.properties"));
        String clean = properties.getProperty("database.cleanAcc");
        String cleanPhones = properties.getProperty("database.cleanPhones");
        String cleanFriendsTable = properties.getProperty("database.cleanFriends");
        this.connection = dataSource.getConnection();
        Statement statement = this.connection.createStatement();
        statement.execute(clean);
        statement.execute(cleanPhones);
        statement.execute(cleanFriendsTable);
        this.connection.close();
    }*/


    @Sql(value = "classpath:createTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:fillTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:dropTables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    @Test
    public void createAccount() throws SQLException {
        Account user = new Account();
        user.setName("Petr");
        accountDao.createAccount(user);
        assertEquals("Petr", accountDao.readAccountById(2).getName());
    }

    @Sql(value = "classpath:createTables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:dropTables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @Transactional
    public void getImageFromDb() throws IOException, SQLException {
        accountDao.loadPicture(1, inputStream);
        assertFalse(accountDao.getImageFromDb(2).isEmpty());
    }

    @Test
    @Transactional
    public void checkForLogin() throws SQLException {
        Account account = new Account();
        account.setName("Nickolay");
        assertEquals(account.getName(), accountDao.checkForLogin("l@l.ru", "123").getName());
    }

    @Test
    @Transactional
    public void updateAccount() throws SQLException {

        assertEquals("Maks", accountDao.readAccountById(2).getName());
    }

    @Test (expected = EmptyResultDataAccessException.class)
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

    @Test
    @Transactional
    public void readAccountById() {
        Account user = new Account();
        user.setId(2);
        user.setName("Petr");
        accountDao.createAccount(user);
        assertEquals("Petr", accountDao.readAccountById(2).getName());
    }

    @Test
    @Transactional
    public void loadPicture() throws SQLException, IOException {
        accountDao.loadPicture(1, inputStream);
        assertFalse(accountDao.getImageFromDb(1).isEmpty());
    }

    @Test
    @Transactional
    public void showAllAccounts() throws SQLException {
        assertEquals(accountDao.readAccountById(1).getName(), accountDao.showAllAccounts().get(0).getName());
    }
}
