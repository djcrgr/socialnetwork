package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Phone;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class AccountDao {

    //crud for account

    private static final String SELECT_ALL = "select * from account";
    private static final String SELECT_ALL_BY_ID = "select distinct account.id from account ";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE account.id=?";
    private static final String CREATE_PHONE = "insert into phones (userId, phoneNum, phoneType) values (?, ?, ?)";
    private static final String CREATE_ACCOUNT = "INSERT INTO account (id, name, surname, age, address, " +
            "email, password) values (?, ? , ?, ?, ?, ?, ?)";
    private static final String UPDATE_ACCOUNT = "update account set name = ?, surname = ?, age = ?,  " +
            "address = ?, email = ?, password = ? where id = ?";
    private static final String UPDATE_PHONE = "update phones set phoneNum = ? where phoneType = ? and userId = ?";
    private static final String DELETE_BY_ID = "delete from account where id = ?";
    private static final String DELETE_PHONES_BY_ID = "delete from phones where userId = ?";
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

    public AccountDao(Connection connection) throws SQLException {
        this.connection = connection;
        connection.setAutoCommit(false);
    }

    public int getIdForNew() throws SQLException {
        int result = 0;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement("select count(*) as result from " +
                "account")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                return resultSet.getInt("result") + 1;
                }
            }
        }
        return result;
    }

    public String getImageFromDb(int accountId) throws SQLException, IOException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_PHOTO)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Blob blob = resultSet.getBlob("photo");
                    if (blob == null) {
                        return getImageFromDb(22);
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
//                    connectionPool.free(this.connection);
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
        if (email != null && password != null) {
            try (ResultSet resultSet = statement.executeQuery()) {
                Account account = null;
                if (resultSet.next()) {
                    account = createAccountFromResult(resultSet);
                }
                return account;
            }
        } else {
            return null;
        }
    }

    /*public void addFriend(Account account, Account friend) {
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
//            connectionPool.free(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }*/

    public List<Account> showFriend(Account account) {
        List<Account> friendList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SHOW_FRIENDS)) {
            preparedStatement.setInt(1, account.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    friendList.add(readAccountById(resultSet.getInt("friendid")));
                }
            }
//            connectionPool.free(connection);
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
            preparedStatement.setString(5, account.getAddress());
            preparedStatement.setString(6, account.getEmail());
            preparedStatement.setString(7, account.getPassword());
            preparedStatement.executeUpdate();
            /*if (account.getPhoneNum() != null) {
                for (Phone phone : account.getPhoneNum()) {
                    try (PreparedStatement preparedStatement1 = this.connection.prepareStatement(CREATE_PHONE)) {
                        preparedStatement1.setInt(1, account.getId());
                        preparedStatement1.setInt(2, phone.getNumber());
                        preparedStatement1.setString(3, phone.getType());
                        preparedStatement1.executeUpdate();

                    }
                }
            }*/
//            connectionPool.free(connection);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void updateAccount(Account account) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT)) {
            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, account.getSurname());
            preparedStatement.setInt(3, account.getAge());
            preparedStatement.setString(4, account.getAddress());
            preparedStatement.setString(5, account.getEmail());
            preparedStatement.setString(6, account.getPassword());
            preparedStatement.setInt(7, account.getId());
            preparedStatement.executeUpdate();
            /*if (account.getPhoneNum() != null) {
                for (Phone phone : account.getPhoneNum()) {
                    if (phone.getType().equals("home")) {
                        try (PreparedStatement preparedStatement1 = this.connection.prepareStatement("update phones " +
                                "set phoneNum = ? where phoneType = 'home' and userId = ?")) {
                            preparedStatement1.setInt(1, phone.getNumber());
                            preparedStatement1.setInt(2, account.getId());
                            preparedStatement1.executeUpdate();
//                            connectionPool.free(connection);
                        }
                    } else if (phone.getType().equals("work")){
                        try (PreparedStatement preparedStatement2 = this.connection.prepareStatement("update phones " +
                                "set phoneNum = ? where phoneType = 'work' and userId = ?")) {
                            preparedStatement2.setInt(1, phone.getNumber());
                            preparedStatement2.setInt(2, account.getId());
                            preparedStatement2.executeUpdate();
//                            connectionPool.free(connection);
                        }
                    }
                *//*try (PreparedStatement preparedStatement1 = this.connection.prepareStatement(UPDATE_PHONE)) {
                for (Phone phone : account.getPhoneNum()) {
                        preparedStatement1.setInt(1, phone.getNumber());
                        preparedStatement1.setString(2, phone.getType());
                        preparedStatement1.setInt(3, account.getId());
                        preparedStatement1.executeUpdate();
                    }
                }*//*
                }
            }*/
        }
    }

    public void deleteById(int id) throws SQLException {
        try (PreparedStatement prepareStatement = this.connection
                .prepareStatement(DELETE_BY_ID)) {
            prepareStatement.setInt(1, id);
            prepareStatement.executeUpdate();
//            connectionPool.free(connection);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        /*try (PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_PHONES_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
//            connectionPool.free(connection);
        }*/
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
            return null;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public List<Account> showAccWithOffset(int limit, int offset, String subStringName, String subStringSurname) throws SQLException {
        List<Account> accountList = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     this.connection.prepareStatement("select * from account " +
                             "where " +
                             "name like " +
                             "CONCAT( " +
                             "'%',?," +
                             "'%') " +
                             "union select * from account where surname like CONCAT( " +
                             "'%',?,'%') " +
                             "limit ? offset " +
                             "?")) {
            preparedStatement.setString(1, subStringName);
            preparedStatement.setString(2, subStringSurname);
            preparedStatement.setInt(3, limit);
            preparedStatement.setInt(4, offset);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    accountList.add(createAccountFromResult(resultSet));
                }
            }
        }
        return accountList;
    }

    public void loadPicture(int accountId, InputStream inputStream) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACC_PHOTO)) {
            preparedStatement.setBlob(1, inputStream);
            preparedStatement.setInt(2, accountId);
            preparedStatement.executeUpdate();
        }
    }

    public List<Account> showAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        try (PreparedStatement prepareStatement = this.connection
                .prepareStatement(SELECT_ALL_BY_ID)) {
            try (ResultSet resultSet = prepareStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    accountList.add(readAccountById(id));
                }
            }
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
        account.setAddress(resultSet.getString("address"));
        account.setEmail(resultSet.getString("email"));
        account.setPassword(resultSet.getString("password"));
        return account;
    }
}
