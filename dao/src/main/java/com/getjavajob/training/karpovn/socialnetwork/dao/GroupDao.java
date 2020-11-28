package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Group;

import javax.print.attribute.standard.DateTimeAtCreation;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class GroupDao {

    private static final String SELECT_ALL = "SELECT * FROM mgroup";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE id=?";
    private static final String CREATE_GROUP = "INSERT INTO mgroup (id, name, description, ownerId, creationDate) values (?, ?, " +
            "? , ?, ?)";
    private static final String UPDATE_GROUP = "update mgroup set name = ?, description = ?, ownerId = ? where id = ?";
    private static final String DELETE_BY_ID = "delete from mgroup where id = ?";
    private static final String UPDATE_GROUP_PHOTO = "update mgroup set picture = ? where id = ?";

    private Connection connection;
    private ConnectionPool connectionPool;

    public GroupDao() throws SQLException, IOException, ClassNotFoundException {
        this.connectionPool = ConnectionPool.getInstance();
        this.connection = connectionPool.getConnection();
    }

    public void createGroup(Group group) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(CREATE_GROUP)) {
            preparedStatement.setInt(1, group.getId());
            preparedStatement.setString(2, group.getName());
            preparedStatement.setString(3, group.getDescription());
            preparedStatement.setInt(4, group.getOwner().getId());
            preparedStatement.setDate(5, new Date(System.currentTimeMillis()) );
            preparedStatement.execute();
            connectionPool.free(connection);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public Group readGroupById(int id) throws IOException, ClassNotFoundException, SQLException {
        try (PreparedStatement prepareStatement = this.connection
                .prepareStatement(SELECT_BY_ID)) {
            prepareStatement.setInt(1, id);
            try (ResultSet resultSet = prepareStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createGroupFromResult(resultSet);
                }
            }
            connectionPool.free(connection);
            return null;
        }
    }

    public void updateGroup(Group group) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_GROUP)) {
            preparedStatement.setString(1, group.getName());
            preparedStatement.setString(2, group.getDescription());
            preparedStatement.setInt(3, group.getOwner().getId());
            preparedStatement.setInt(4, group.getId());
            preparedStatement.executeUpdate();
            connectionPool.free(connection);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void deleteGroupById(int id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connectionPool.free(connection);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private Group createGroupFromResult(ResultSet resultSet) throws SQLException, IOException, ClassNotFoundException {
        Group group = new Group();
        AccountDao accountDao = new AccountDao();
        group.setId(resultSet.getInt("id"));
        group.setName(resultSet.getString("name"));
        group.setDescription(resultSet.getString("description"));
        group.setOwner(accountDao.readAccountById(resultSet.getInt("ownerId")));
        group.setCreationDate(resultSet.getDate("creationDate"));
        return group;
    }

    public void loadPicture(int groupId, InputStream inputStream) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_GROUP_PHOTO)) {
            preparedStatement.setBlob(1, inputStream);
            preparedStatement.setInt(2, groupId);
            preparedStatement.executeUpdate();
            connectionPool.free(this.connection);
        }
    }


}
