package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao {

    private static final String CREATE_PHONE = "insert into phones (userId, phoneNum, phoneType) values (?, ?, ?)";
    private static final String SELECT_ALL = "select * from phones";
    private static final String SELECT_ALL_BY_ID = "select * from phones where userId = ?";
    private static final String DELETE_PHONES_BY_ID = "delete from phones where userId = ?";

    private final Connection connection;

    public PhoneDao(Connection connection) throws SQLException {
        this.connection = connection;
        connection.setAutoCommit(false);
    }

    public void createAccPhones(Account account) {
        if (account.getPhoneNum() != null) {
            for (Phone phone : account.getPhoneNum()) {
                try (PreparedStatement preparedStatement1 = this.connection.prepareStatement(CREATE_PHONE)) {
                    preparedStatement1.setInt(1, account.getId());
                    preparedStatement1.setInt(2, phone.getNumber());
                    preparedStatement1.setString(3, phone.getType());
                    preparedStatement1.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public List<Phone> readAccPhones(int id) {
        List<Phone> phoneList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ALL_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Phone phone = new Phone();
                    phone.setNumber(resultSet.getInt("phoneNum"));
                    phone.setType(resultSet.getString("phoneType"));
                    phoneList.add(phone);
                }
                return phoneList;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void updateAccPhones(Account account) throws SQLException {
        if (account.getPhoneNum() != null) {
            for (Phone phone : account.getPhoneNum()) {
                if (phone.getType().equals("home")) {
                    try (PreparedStatement preparedStatement1 = this.connection.prepareStatement("update phones " +
                            "set phoneNum = ? where phoneType = 'home' and userId = ?")) {
                        preparedStatement1.setInt(1, phone.getNumber());
                        preparedStatement1.setInt(2, account.getId());
                        preparedStatement1.executeUpdate();
                    }
                } else if (phone.getType().equals("work")){
                    try (PreparedStatement preparedStatement2 = this.connection.prepareStatement("update phones " +
                            "set phoneNum = ? where phoneType = 'work' and userId = ?")) {
                        preparedStatement2.setInt(1, phone.getNumber());
                        preparedStatement2.setInt(2, account.getId());
                        preparedStatement2.executeUpdate();
                    }
                }
            }
        }
    }

    public void deleteAccPhones(Account account) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_PHONES_BY_ID)) {
            preparedStatement.setInt(1, account.getId());
            preparedStatement.executeUpdate();
        }
    }
}
