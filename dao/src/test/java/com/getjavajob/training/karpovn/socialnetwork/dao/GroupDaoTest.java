package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Group;
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

public class GroupDaoTest {

    private Connection connection;
    private AccountDao accountDao;
    private GroupDao groupDao;
    private InputStream inputStream;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("Db.properties"));
        inputStream = this.getClass().getClassLoader().getResourceAsStream("15.png");
        String createGroup = properties.getProperty("database.createGroup");
        String fillGroup = properties.getProperty("database.addGroup");
        String createAccTable = properties.getProperty("database.createAcc");
        String fillAcc = properties.getProperty("database.fill");
        this.connection = ConnectionPool.getInstance().getConnection();
        Statement statement = this.connection.createStatement();
        statement.execute(createGroup);
        statement.execute(fillGroup);
        statement.execute(createAccTable);
        statement.execute(fillAcc);
        groupDao = new GroupDao(connection);
        accountDao = new AccountDao(connection);
        ConnectionPool.getInstance().free(connection);}

    @After
    public void tearDown() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("Db.properties"));
        String cleanAccTable = properties.getProperty("database.cleanAcc");
        String cleanGroupTable = properties.getProperty("database.cleanGroup");
        this.connection = ConnectionPool.getInstance().getConnection();
        Statement statement = this.connection.createStatement();
        statement.execute(cleanAccTable);
        statement.execute(cleanGroupTable);
        ConnectionPool.getInstance().free(connection);
    }

    @Test
    public void createGroup() throws SQLException, IOException, ClassNotFoundException {
        Group group = new Group();
        group.setId(2);
        group.setName("group");
        group.setOwner(accountDao.readAccountById(1));
        groupDao.createGroup(group);
        assertEquals(group.getName(), groupDao.readGroupById(2).getName());
    }

    @Test
    public void readGroupById() throws SQLException, IOException, ClassNotFoundException {
        assertEquals("billy group", groupDao.readGroupById(1).getName());
    }

    @Test
    public void updateGroup() throws SQLException, IOException, ClassNotFoundException {
        Group group = new Group();
        group.setId(2);
        group.setName("group");
        group.setOwner(accountDao.readAccountById(1));
        groupDao.createGroup(group);
        assertEquals(group.getName(), groupDao.readGroupById(2).getName());
        group.setName("privet");
        groupDao.updateGroup(group);
        assertEquals(group.getName(), groupDao.readGroupById(2).getName());
    }

    @Test
    public void deleteGroupById() throws SQLException, IOException, ClassNotFoundException {
        Group group = new Group();
        group.setId(2);
        group.setName("group");
        group.setOwner(accountDao.readAccountById(1));
        groupDao.createGroup(group);
        assertEquals(group.getName(), groupDao.readGroupById(2).getName());
        groupDao.deleteGroupById(2);
        assertNull(groupDao.readGroupById(2));
    }

    @Test
    public void getImageFromDb() throws SQLException, IOException {
        groupDao.loadPicture(1, inputStream);
        assertFalse(groupDao.getImageFromDb(1).isEmpty());
    }

    @Test
    public void showAllUsers() {
    }

    @Test
    public void showAllGroups() throws SQLException, IOException, ClassNotFoundException {
        Group group = groupDao.readGroupById(1);
        List<Group> groups = groupDao.showAllGroups();
        assertEquals(group.getName(), groups.get(0).getName());
    }

    @Test
    public void showGroupWithOffset() throws SQLException, IOException, ClassNotFoundException {
        List<Group> groups = groupDao.showGroupWithOffset(1, 0, "bill");
        assertEquals(groups.get(0).getName(), groupDao.readGroupById(1).getName());
    }

    @Test
    public void loadPicture() throws SQLException, IOException {
        groupDao.loadPicture(1, inputStream);
        assertFalse(groupDao.getImageFromDb(1).isEmpty());
    }
}