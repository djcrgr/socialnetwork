package com.getjavajob.training.karpovn.socialnetwork.service;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Group;
import com.getjavajob.training.karpovn.socialnetwork.dao.ConnectionPool;
import com.getjavajob.training.karpovn.socialnetwork.dao.GroupDao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GroupService extends AbstractService<Group, Account>{

    private GroupDao groupDao;
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final Connection connection;

    public GroupService() throws SQLException, IOException, ClassNotFoundException {
        this.connection = connectionPool.getConnection();
        this.groupDao = new GroupDao(connection);
    }

    @Override
    public void create(Group group) {
        groupDao.createGroup(group);
    }

    @Override
    public Group readById(int id) throws SQLException, IOException, ClassNotFoundException {
        return groupDao.readGroupById(id);
    }

    @Override
    public String getImageFromDb(int id) throws IOException, SQLException {
        return groupDao.getImageFromDb(id);
    }

    @Override
    public void update(Group group) {
        groupDao.updateGroup(group);
    }

    @Override
    public void delete(Group group) throws SQLException {
        groupDao.deleteGroupById(group.getId());
    }

    @Override
    void addFriend(Group group, Group friend) {
        throw new UnsupportedOperationException();
    }

    @Override
    void removeFriend(Group group, Group friend) {
        throw new UnsupportedOperationException();
    }

    public List<Group> showWithOffset(int resultOnPage, int countCurrentPage, String searchStr) throws SQLException,
            IOException, ClassNotFoundException {
        return groupDao.showGroupWithOffset(resultOnPage, countCurrentPage * 5 - 5, searchStr);
    }

    @Override
    public void loadPicture(int id, InputStream inputStream) throws SQLException {
        groupDao.loadPicture(id, inputStream);
    }

    @Override
    List<Group> showFriends(Group group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Group> showAll() throws SQLException, IOException, ClassNotFoundException {
       return groupDao.showAllGroups();
    }

    @Override
    Group checkExisting(String email, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }
}