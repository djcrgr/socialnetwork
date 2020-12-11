package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Group;

import javax.print.attribute.standard.DateTimeAtCreation;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class GroupDao {

    private static final String SELECT_ALL = "SELECT * FROM mgroup";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE id=?";
    private static final String CREATE_GROUP = "INSERT INTO mgroup (id, name, description, ownerId, creationDate) values (?, ?, " +
            "? , ?, ?)";
    private static final String UPDATE_GROUP = "update mgroup set name = ?, description = ?, ownerId = ? where id = ?";
    private static final String DELETE_BY_ID = "delete from mgroup where id = ?";
    private static final String UPDATE_GROUP_PHOTO = "update mgroup set picture = ? where id = ?";
    private static final String GET_PHOTO = "select picture from mgroup where id = ?";
    private static final String GET_ACC_ID_FROM_GROUP_WITH_ID = "select userId from groupusers where groupId = ?";
    private static final String GET_ACC_ID_FROM_GROUP = "select userId from groupusers";
    private static final String SHOW_ALL_GROUPS_ID = "select id from mgroup";

    private Connection connection;
    private ConnectionPool connectionPool;

    public GroupDao() throws SQLException, IOException, ClassNotFoundException {
        this.connectionPool = ConnectionPool.getInstance();
        this.connection = connectionPool.getConnection();
    }

    public GroupDao(Connection connection) throws SQLException {
        this.connection = connection;
        this.connection.setAutoCommit(false);
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

    public String getImageFromDb(int groupId) throws SQLException, IOException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_PHOTO)) {
            preparedStatement.setInt(1, groupId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Blob blob = resultSet.getBlob("picture");
                    if (blob == null) {
                        return getImageFromDb(1);
                    }
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    connectionPool.free(this.connection);
                    return base64Image;
                }
            }
        }
        return null;
    }

    public List<Account> showAllUsers() throws SQLException, IOException, ClassNotFoundException {
        List<Account> accountList = new ArrayList<>();
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ACC_ID_FROM_GROUP)) {
            AccountDao accountDao = new AccountDao();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    accountList.add(accountDao.readAccountById(resultSet.getInt("userId")));
                }
            }
            connectionPool.free(this.connection);
        }
        return accountList;
    }

    public List<Group> showAllGroups() throws SQLException, IOException, ClassNotFoundException {
        List<Group> groupList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    groupList.add(createGroupFromResult(resultSet));
                }
            }
            connectionPool.free(this.connection);
        }
        return groupList;
    }

    public List<Group> showGroupWithOffset(int limit, int offset, String subStringName) throws SQLException, IOException,
            ClassNotFoundException {
        List<Group> groupList = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     this.connection.prepareStatement("select * from mgroup " +
                             "where " +
                             "name like " +
                             "CONCAT( " +
                             "'%',?," +
                             "'%') "  +
                             "limit ? offset " +
                             "?")) {
            preparedStatement.setString(1, subStringName);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    groupList.add(createGroupFromResult(resultSet));
                }
            }
            connectionPool.free(this.connection);
        }
        return groupList;
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
