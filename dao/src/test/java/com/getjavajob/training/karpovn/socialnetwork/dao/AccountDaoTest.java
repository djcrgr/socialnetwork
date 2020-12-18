package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class AccountDaoTest {

    private Connection connection;
    private AccountDao accountDao;
    private InputStream inputStream;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("Db.properties"));
        inputStream = this.getClass().getClassLoader().getResourceAsStream("15.png");
        String create = properties.getProperty("database.createAcc");
        String fill = properties.getProperty("database.fill");
        String createFriendsInDb = properties.getProperty("database.createFriends");
        this.connection = ConnectionPool.getInstance().getConnection();
        Statement statement = this.connection.createStatement();
        statement.execute(create);
        statement.execute(fill);
        statement.execute(createFriendsInDb);
        accountDao = new AccountDao(connection);
        ConnectionPool.getInstance().free(connection);
    }

    @After
    public void tearDown() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("Db.properties"));
        String clean = properties.getProperty("database.cleanAcc");
        String cleanFriendsTable = properties.getProperty("database.cleanFriends");
        this.connection = ConnectionPool.getInstance().getConnection();
        Statement statement = this.connection.createStatement();
        statement.execute(clean);
        statement.execute(cleanFriendsTable);
        ConnectionPool.getInstance().free(connection);
    }

    @Test
    public void getIdForNew() throws SQLException {
        assertEquals(2, accountDao.getIdForNew());
    }

    @Test
    public void getImageFromDb() throws IOException, SQLException {
        accountDao.loadPicture(1, inputStream);
        assertFalse(accountDao.getImageFromDb(1).isEmpty());
    }

    @Test
    public void checkForLogin() throws SQLException {
        Account account = new Account();
        account.setName("ivan");
        assertEquals(account.getName(), accountDao.checkForLogin("list@list.ru", "123").getName());
    }

    @Test
    public void createAccount() {
        Account user = new Account();
        user.setId(2);
        user.setName("Petr");
        accountDao.createAccount(user);
        assertEquals(user.getId(), accountDao.readAccountById(2).getId());
    }

    @Test
    public void updateAccount() throws SQLException {
        Account user = new Account();
        user.setId(2);
        user.setName("Petr");
        accountDao.createAccount(user);
        assertEquals("Petr", accountDao.readAccountById(2).getName());
        user.setName("Maks");
        accountDao.updateAccount(user);
        assertEquals("Maks", accountDao.readAccountById(2).getName());
    }

    @Test
    public void deleteById() throws SQLException {
        Account user = new Account();
        user.setId(2);
        user.setName("Petr");
        accountDao.createAccount(user);
        assertEquals("Petr", accountDao.readAccountById(2).getName());
        accountDao.deleteById(2);
        assertNull(accountDao.readAccountById(2));
    }

    @Test
    public void readAccountById() {
        Account user = new Account();
        user.setId(2);
        user.setName("Petr");
        accountDao.createAccount(user);
        assertEquals("Petr", accountDao.readAccountById(2).getName());
    }

    @Test
    public void showAccWithOffset() throws SQLException {
        Account user = new Account();
        user.setId(2);
        user.setName("1");
        accountDao.createAccount(user);
        List<Account> accountList = accountDao.showAccWithOffset(1,1, "1", "");
        assertEquals(accountList.get(0).getName(), user.getName());
    }

    @Test
    public void loadPicture() throws SQLException, IOException {
        accountDao.loadPicture(1, inputStream);
        assertFalse(accountDao.getImageFromDb(1).isEmpty());
    }

    @Test
    public void showAllAccounts() {
        assertEquals(accountDao.readAccountById(1).getName(), accountDao.showAllAccounts().get(0).getName());
    }
}