package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Group;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import static org.junit.Assert.*;

public class GroupDaoTest {


    private Connection connection;
/*
    public void before() throws SQLException, IOException, ClassNotFoundException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("Db.properties"));
        String create = properties.getProperty("database.createGroup");
        this.connection = ConnectionPool.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.execute(create);
    }


    public void tearDown() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("Db.properties"));
        String clean = properties.getProperty("database.cleanGroup");
        Statement statement = connection.createStatement();
        statement.execute(clean);
        statement.close();
    }

    @Test
    public void createGroup() throws SQLException, IOException, ClassNotFoundException {
        before();
        Group group = new Group();
        group.setId(1);
        group.setName("name");
        GroupDao groupDao = new GroupDao();
        groupDao.createGroup(group);
        Group expectedGroup;
        expectedGroup = groupDao.readGroupById(1);
        assertTrue(group.equals(expectedGroup));
        tearDown();
    }

    @Test
    public void readGroupById() throws IOException, SQLException, ClassNotFoundException {
        before();
        GroupDao groupDao = new GroupDao();
        groupDao.createGroup(new Group(3, "positive"));
        Group expectedGroup = groupDao.readGroupById(3);
        expectedGroup.getName();
        assertEquals("positive", expectedGroup.getName());
        tearDown();
    }

    @Test
    public void updateGroup() throws SQLException, IOException, ClassNotFoundException {
        before();
        GroupDao groupDao = new GroupDao();
        Group group = new Group(2, "name");
        groupDao.createGroup(group);
        Group newGroup = new Group();
        newGroup.setId(2);
        newGroup.setName("newGroup");
        groupDao.updateGroup(newGroup);
        assertEquals("newGroup", groupDao.readGroupById(2).getName());
        tearDown();
    }

    @Test
    public void deleteGroupById() throws SQLException, IOException, ClassNotFoundException {
        before();
        GroupDao groupDao = new GroupDao();
        groupDao.deleteGroupById(2);
        assertNull(groupDao.readGroupById(2));
        tearDown();
    }*/
}