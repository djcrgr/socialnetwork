/*
package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Phone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class PhoneDaoTest {

    private Connection connection;
    private AccountDao accountDao;
    private PhoneDao phoneDao;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("Db.properties"));
        String createAccTable = properties.getProperty("database.createAcc");
        String fillAcc = properties.getProperty("database.fill");
        String createPhonesTable = properties.getProperty("database.createPhones");
        this.connection = ConnectionPool.getInstance().getConnection();
        Statement statement = this.connection.createStatement();
        statement.execute(createAccTable);
        statement.execute(fillAcc);
        statement.execute(createPhonesTable);
        accountDao = new AccountDao(connection);
        phoneDao =  new PhoneDao(connection);
        ConnectionPool.getInstance().free(connection);
    }

    @After
    public void tearDown() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("Db.properties"));
        String cleanAccTable = properties.getProperty("database.cleanAcc");
        String cleanPhonesTable = properties.getProperty("database.cleanPhones");
        this.connection = ConnectionPool.getInstance().getConnection();
        Statement statement = this.connection.createStatement();
        statement.execute(cleanAccTable);
        statement.execute(cleanPhonesTable);
        ConnectionPool.getInstance().free(connection);
    }

    @Test
    public void createAccPhones() {
        Account account = accountDao.readAccountById(1);
        Phone workPhone = new Phone(111, "work");
        Phone homePhone = new Phone(222, "home");
        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(homePhone);
        phoneList.add(workPhone);
        account.setPhoneNum(phoneList);
        phoneDao.createAccPhones(account);
        assertEquals(account.getPhoneNum().toString(), phoneDao.readAccPhones(1).toString());
    }

    @Test
    public void readAccPhones() {
        Account account = accountDao.readAccountById(1);
        Phone workPhone = new Phone(111, "work");
        Phone homePhone = new Phone(222, "home");
        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(homePhone);
        phoneList.add(workPhone);
        account.setPhoneNum(phoneList);
        phoneDao.createAccPhones(account);
        assertEquals(account.getPhoneNum().toString(), phoneDao.readAccPhones(1).toString());
    }

    @Test
    public void updateAccPhones() throws SQLException {
        Account account = accountDao.readAccountById(1);
        Phone workPhone = new Phone(111, "work");
        Phone homePhone = new Phone(222, "home");
        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(homePhone);
        phoneList.add(workPhone);
        account.setPhoneNum(phoneList);
        phoneDao.createAccPhones(account);
        workPhone = new Phone(333, "work");
        phoneList.remove(1);
        phoneList.add(workPhone);
        account.setPhoneNum(phoneList);
        phoneDao.updateAccPhones(account);
        assertEquals(account.getPhoneNum().toString(), phoneDao.readAccPhones(1).toString());
    }

    @Test
    public void deleteAccPhones() throws SQLException {
        Account account = accountDao.readAccountById(1);
        Phone workPhone = new Phone(111, "work");
        Phone homePhone = new Phone(222, "home");
        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(homePhone);
        phoneList.add(workPhone);
        account.setPhoneNum(phoneList);
        phoneDao.createAccPhones(account);
        assertFalse(phoneDao.readAccPhones(1).isEmpty());
        phoneDao.deleteAccPhones(account);
        assertTrue(phoneDao.readAccPhones(1).isEmpty());
    }
}*/
