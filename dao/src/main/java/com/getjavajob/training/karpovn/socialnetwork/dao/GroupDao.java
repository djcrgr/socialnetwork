package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Group;

import java.io.IOException;
import java.sql.*;

public class GroupDao {

    private static final String SELECT_ALL = "SELECT * FROM mgroup";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE id=?";
    private static final String CREATE_GROUP = "INSERT INTO mgroup (id, name) values (?, ?)";
    private static final String UPDATE_GROUP = "update mgroup set name = ? where id = ?";
    private static final String DELETE_BY_ID = "delete from mgroup where id = ?";

    private Connection connection;

    public GroupDao() throws SQLException, IOException, ClassNotFoundException {
        this.connection = ConnectionPool.getInstance().getConnection();
    }

    public void createGroup(Group group) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(CREATE_GROUP)) {
            preparedStatement.setInt(1, group.getId());
            preparedStatement.setString(2, group.getName());
            preparedStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public Group readGroupById(int id) {
        try (PreparedStatement prepareStatement = this.connection
                .prepareStatement(SELECT_BY_ID)) {
            prepareStatement.setInt(1, id);
            try (ResultSet resultSet = prepareStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createGroupFromResult(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateGroup(Group group) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_GROUP)) {
            preparedStatement.setString(1, group.getName());
            preparedStatement.setInt(2, group.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void deleteGroupById(int id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private Group createGroupFromResult(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setId(resultSet.getInt("id"));
        group.setName(resultSet.getString("name"));
        return group;
    }
}
