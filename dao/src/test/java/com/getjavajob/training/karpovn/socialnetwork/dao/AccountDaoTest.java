package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;
import com.getjavajob.training.karpovn.socialnetwork.dao.ConnectionPool;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountDaoTest {

    private Connection connection;

    @Test
    public void aCreateAccount() throws IOException, SQLException, ClassNotFoundException {
        setUp();
        AccountDao accountDao = new AccountDao();
        Account newAccount = new Account(2, "ivan", "ivanov", 10, 25111);
        accountDao.createAccount(newAccount);
        Account account = accountDao.readAccountById(2);
        assertEquals("ivan", account.getName());
        tearDown();
    }

    @Test
    public void bReadAccountById() throws IOException, SQLException, ClassNotFoundException {
        setUp();
        AccountDao accountDao = new AccountDao();
        Account expectedAcc = accountDao.readAccountById(1);
        assertEquals(25111, expectedAcc.getPhoneNum());
        tearDown();
    }

    @Test
    public void cUpdateAccount() throws IOException, SQLException, ClassNotFoundException {
        setUp();
        AccountDao accountDao = new AccountDao();
        Account newAcc = new Account(1, "petr", "ivanov", 15, 545);
        accountDao.updateAccount(newAcc);
        Account expectedAcc = accountDao.readAccountById(1);
        assertEquals("petr", expectedAcc.getName());
        tearDown();
    }

    @Test
    public void deleteById() throws IOException, SQLException, ClassNotFoundException {
        setUp();
        AccountDao accountDao = new AccountDao();
        accountDao.deleteById(1);
        assertNull(accountDao.readAccountById(1));
        tearDown();
    }

    @Test
    public void eAddFriend() throws IOException, SQLException, ClassNotFoundException {
        setUp();
        Account friend = new Account(2, "petr", "petrov", 32, 32111);
        AccountDao accountDao = new AccountDao();
        Account user = accountDao.readAccountById(1);
        accountDao.createAccount(friend);
        accountDao.addFriend(user, friend);
        int friendId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from friends")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                friendId = resultSet.getInt("friendid");
            }
        }
        assertEquals(friend.getId(), friendId);
        tearDown();
    }

    @Test
    public void hShowFriend() throws IOException, SQLException, ClassNotFoundException {
        setUp();
        AccountDao accountDao = new AccountDao();
        Account user = accountDao.readAccountById(1);
        Account friend = new Account(2, "petr", "petrov", 32, 32111);
        accountDao.createAccount(friend);
        accountDao.addFriend(user, friend);
        List<Account> accountList = new ArrayList<>();
        accountList.add(friend);
        List<Account> expectedList = accountDao.showFriend(user);
        assertEquals(accountList.get(0).getName(), expectedList.get(0).getName());
        tearDown();
    }

    @Test(expected = org.h2.jdbc.JdbcSQLNonTransientException.class)
    public void gRemoveFriend() throws IOException, SQLException, ClassNotFoundException {
        setUp();
        AccountDao accountDao = new AccountDao();
        Account user = accountDao.readAccountById(1);
        Account friend = new Account(2, "petr", "petrov", 32, 32111);
        accountDao.createAccount(friend);
        accountDao.removeFriend(user, friend);
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from friends")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            tearDown();
            resultSet.getInt("id");
        }
    }

    @Test
    public void hShowAllAccounts() throws SQLException, IOException, ClassNotFoundException {
        setUp();
        AccountDao accountDao = new AccountDao();
        List<Account> accountList = accountDao.showAllAccounts();
        assertEquals("ivan", accountList.get(0).getName());
        tearDown();
    }

    @Test
    public void iShowAccWithOffset() throws SQLException, IOException, ClassNotFoundException {
        setUp();
        AccountDao accountDao = new AccountDao();
        Account account = new Account(1, "ivan", "ivanov", 10, 25111);
        List<Account> accountList = accountDao.showAccWithOffset(1,0, "iva", "iva");
        assertEquals(account.getName(), accountList.get(0).getName());
        tearDown();
    }

    public void setUp() throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("Db.properties"));
        String create = properties.getProperty("database.createAcc");
        String fill = properties.getProperty("database.fill");
        String createFriends = properties.getProperty("database.createFriends");
        this.connection = ConnectionPool.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.execute(create);
        statement.execute(fill);
        statement.execute(createFriends);
    }

    public void tearDown() throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("Db.properties"));
        String cleanAcc = properties.getProperty("database.cleanAcc");
        String cleanFriends = properties.getProperty("database.cleanFriends");
        Statement statement = this.connection.createStatement();
        statement.execute(cleanAcc);
        statement.execute(cleanFriends);
        statement.close();
    }
}