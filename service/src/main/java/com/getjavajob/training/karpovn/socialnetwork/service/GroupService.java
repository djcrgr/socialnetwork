package com.getjavajob.training.karpovn.socialnetwork.service;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Group;
import com.getjavajob.training.karpovn.socialnetwork.dao.ConnectionPool;
import com.getjavajob.training.karpovn.socialnetwork.dao.GroupDao;
import org.postgresql.core.SqlCommand;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupService extends AbstractService<Group, Account>{

    private final GroupDao groupDao;

    private final Connection connection;
    private final ConnectionPool connectionPool;

    public GroupService() throws SQLException, IOException, ClassNotFoundException {
        connectionPool = ConnectionPool.getInstance();
        this.connection = connectionPool.getThreadConnection();
        this.groupDao = new GroupDao(connection);
    }

    private void freeConnection() {
        connectionPool.free(connection);
    }

    @Override
    public void create(Group group) throws SQLException {
        try {
        groupDao.createGroup(group);
        connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        } finally {
            freeConnection();
        }
    }

    @Override
    public Group readById(int id) throws SQLException, IOException, ClassNotFoundException {
        Group group = null;
        try {
            group= groupDao.readGroupById(id);
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        } finally {
            freeConnection();
        }
        return group;
    }

    @Override
    public String getImageFromDb(int id) throws IOException, SQLException {
        String image = null;
        try {
            image = groupDao.getImageFromDb(id);
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        } finally {
            freeConnection();
        }
        return image;
    }

    @Override
    public void update(Group group) throws SQLException {
        try {
        groupDao.updateGroup(group);
        connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        } finally {
            freeConnection();
        }
    }

    @Override
    public void delete(Group group) throws SQLException {
        try {
        groupDao.deleteGroupById(group.getId());
        } catch (Exception throwables) {
            throwables.printStackTrace();
            connection.rollback();
        } finally {
            freeConnection();
        }
    }

   /* @Override
    void addFriend(Group group, Group friend) {
        throw new UnsupportedOperationException();
    }

    @Override
    void removeFriend(Group group, Group friend) {
        throw new UnsupportedOperationException();
    }*/

    public List<Group> showWithOffset(int resultOnPage, int countCurrentPage, String searchStr) throws SQLException{
        List<Group> groups = new ArrayList<>();
        try {
            groups = groupDao.showGroupWithOffset(resultOnPage, countCurrentPage * 5 - 5, searchStr);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            freeConnection();
        }
        return groups;
    }

    @Override
    public void loadPicture(int id, InputStream inputStream) throws SQLException {
        try {
        groupDao.loadPicture(id, inputStream);
        connection.commit();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            freeConnection();
        }
    }

    /*@Override
    List<Group> showFriends(Group group) {
        throw new UnsupportedOperationException();
    }*/

    @Override
    public List<Group> showAll() throws SQLException, IOException, ClassNotFoundException {
       List<Group> groups = new ArrayList<>();
       try {
           groups = groupDao.showAllGroups();
           connection.commit();
       } catch (Exception e) {
           connection.rollback();
       } finally {
           freeConnection();
       }
        return groups;
    }

    @Override
    Group checkExisting(String email, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }
}