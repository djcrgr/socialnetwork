package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class AccountDao {

    //crud for account

    private static final String SELECT_ALL = "SELECT * FROM account";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE id=?";
    private static final String CREATE_ACCOUNT = "INSERT INTO account (id, name, surname, age, phoneNum, address, " +
            "email, password) values (?, ? , ?, ?, ?, ?, ?, ?) ";
    private static final String UPDATE_ACCOUNT = "update account set name = ?, surname = ?, age = ?, phoneNum = ?, " +
            "address = ?, email = ?, password = ? where id = ?";
    private static final String DELETE_BY_ID = "delete from account where id = ?";
    private static final String ADD_FRIEND = "insert into friends (id, friendid)values (? , ?)";
    private static final String REMOVE_FRIEND = "delete from friends where friendid = ? and id = ?";
    private static final String SHOW_FRIENDS = "select * from friends where id = ?";
    private static final String UPDATE_ACC_PHOTO = "update account set photo = ? where id = ?";
    private static final String GET_PHOTO = "select photo from account where id = ?";

    private Connection connection;
    private ConnectionPool connectionPool;

    public AccountDao() throws SQLException, IOException, ClassNotFoundException {
        this.connectionPool = ConnectionPool.getInstance();
        this.connection = connectionPool.getConnection();
    }

    public String getImageFromDb (int accountId) throws SQLException, IOException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_PHOTO)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Blob blob = resultSet.getBlob("photo");
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    return base64Image;
                }
            }
        }
        return null;
    }

    public Account checkForLogin(String email, String password) throws SQLException {
        String sql = "SELECT * FROM account WHERE email = ? and password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, password);
        if (email != null && password  != null) {
            try (ResultSet resultSet = statement.executeQuery()) {
                Account account = null;
                if (resultSet.next()) {
                    account = createAccountFromResult(resultSet);
                }
                connectionPool.free(connection);
                return account;
            }
        } else {
            return null;
        }
    }

    public void addFriend(Account account, Account friend) {
        prepStat(friend, account, ADD_FRIEND);
    }

    public void removeFriend(Account account, Account friend) {
        prepStat(account, friend, REMOVE_FRIEND);
    }

    private void prepStat(Account account, Account friend, String removeFriend) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(removeFriend)) {
            preparedStatement.setInt(1, friend.getId());
            preparedStatement.setInt(2, account.getId());
            preparedStatement.execute();
            connectionPool.free(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Account> showFriend(Account account) {
        List<Account> friendList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SHOW_FRIENDS)) {
            preparedStatement.setInt(1, account.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    friendList.add(readAccountById(resultSet.getInt("friendid")));
                }
            }
            connectionPool.free(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return friendList;
    }

    public void createAccount(Account account) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(CREATE_ACCOUNT)) {
            preparedStatement.setInt(1, account.getId());
            preparedStatement.setString(2, account.getName());
            preparedStatement.setString(3, account.getSurname());
            preparedStatement.setInt(4, account.getAge());
            preparedStatement.setInt(5, account.getPhoneNum());
            preparedStatement.setString(6, account.getAddress());
            preparedStatement.setString(7, account.getEmail());
            preparedStatement.setString(8, account.getPassword());
            preparedStatement.executeUpdate();
            connectionPool.free(connection);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void updateAccount(Account account) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT)) {
            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, account.getSurname());
            preparedStatement.setInt(3, account.getAge());
            preparedStatement.setInt(4, account.getPhoneNum());
            preparedStatement.setString(5, account.getAddress());
            preparedStatement.setString(6, account.getEmail());
            preparedStatement.setString(7, account.getPassword());
            preparedStatement.setInt(8, account.getId());
            preparedStatement.executeUpdate();
            connectionPool.free(connection);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try (PreparedStatement prepareStatement = this.connection
                .prepareStatement(DELETE_BY_ID)) {
            prepareStatement.setInt(1, id);
            prepareStatement.executeUpdate();
            connectionPool.free(connection);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public Account readAccountById(int id) {
        try (PreparedStatement prepareStatement = this.connection
                .prepareStatement(SELECT_BY_ID)) {
            prepareStatement.setInt(1, id);
            try (ResultSet resultSet = prepareStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createAccountFromResult(resultSet);
                }
            }
            connectionPool.free(connection);
            return null;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public List<Account> showAccWithOffset(int limit, int offset, String subStringName, String subStringSurname) throws SQLException {
        List<Account> accountList = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     this.connection.prepareStatement("select * from account where name like CONCAT( '%',?,'%') union select * from account where surname like CONCAT( '%',?,'%') limit ? offset ?")) {
            preparedStatement.setString(1, subStringName);
            preparedStatement.setString(2, subStringSurname);
            preparedStatement.setInt(3, limit);
            preparedStatement.setInt(4, offset );
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    accountList.add(createAccountFromResult(resultSet));
                }
            }
            connectionPool.free(this.connection);
        }
        return accountList;
    }

    public void loadPicture(int accountId, InputStream inputStream) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACC_PHOTO)) {
            preparedStatement.setBlob(1, inputStream);
            preparedStatement.setInt(2, accountId);
            preparedStatement.executeUpdate();
            connectionPool.free(this.connection);
        }
    }

    public List<Account> showAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        try (PreparedStatement prepareStatement = this.connection
                .prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = prepareStatement.executeQuery()) {
                while (resultSet.next()) {
                    accountList.add(createAccountFromResult(resultSet));
                }
            }
            connectionPool.free(connection);
            return accountList;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
    }


    private Account createAccountFromResult(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setName(resultSet.getString("name"));
        account.setSurname(resultSet.getString("surname"));
        account.setAge(resultSet.getInt("age"));
        account.setPhoneNum(resultSet.getInt("phoneNum"));
        account.setAddress(resultSet.getString("address"));
        account.setEmail(resultSet.getString("email"));
        account.setPassword(resultSet.getString("password"));
        return account;
    }
}
