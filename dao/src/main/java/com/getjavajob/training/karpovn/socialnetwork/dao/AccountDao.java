package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import jdk.nashorn.internal.objects.annotations.Constructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;


import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Repository
@ContextConfiguration(locations = {"classpath:applicationContextDao.xml"})
public class AccountDao {

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
	private static final String QUERY_FOR__OFFSET = "select * from account where name like CONCAT( '%' ,?, '%') union " +
			"select * from account where surname like CONCAT( '%',?,'%') limit ? offset ?";
	private static final String QUERY_EMAIL_PASS = "SELECT id FROM account WHERE email = ? and password = ?";
	private static final String QUERY_FOR_COUNT = "select count(*) as result from account";


	private final DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public AccountDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Autowired
	public AccountDao(DataSource dataSource, JdbcTemplate jdbcTemplate) {
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
	}

	public DataSource getDatasource() {
		return dataSource;
	}

	public int getIdForNew() {
		return this.jdbcTemplate.queryForObject(QUERY_FOR_COUNT, Integer.class) + 1;
	}

	public String getImageFromDb(int accountId) {
		Blob result;
		result = this.jdbcTemplate.queryForObject(GET_PHOTO, Blob.class, accountId);
		if (result == null) {
			return getImageFromDb(22);
		} else {
			return getString(result);
		}
	}

	private String getString(Blob blob) {
		InputStream inputStream = null;
		try {
			inputStream = blob.getBinaryStream();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		while (true) {
			try {
				if (!((bytesRead = inputStream.read(buffer)) != -1)) break;
			} catch (IOException e) {
				e.printStackTrace();
			}
			outputStream.write(buffer, 0, bytesRead);
		}
		byte[] imageBytes = outputStream.toByteArray();
		return Base64.getEncoder().encodeToString(imageBytes);
	}

	public Account checkForLogin(String email, String password) {
		return this.jdbcTemplate.queryForObject(QUERY_EMAIL_PASS, new Object[]{email, password},
				new RowMapper<Account>() {
					@Override
					public Account mapRow(ResultSet resultSet, int i) throws SQLException {
						return AccountDao.this.readAccountById(resultSet.getInt("id"));
					}
				});
	}

	public void createAccount(Account account) {
		this.jdbcTemplate.update(CREATE_ACCOUNT, account.getId(), account.getName(), account.getSurname(),
				account.getAge(), account.getAddress(), account.getEmail(), account.getPassword());
	}

	public void updateAccount(Account account) {
		this.jdbcTemplate.update(UPDATE_ACCOUNT, account.getName(), account.getSurname(), account.getAge(),
				account.getAddress(), account.getEmail(), account.getPassword(), account.getId());
	}

	public void deleteById(int id) {
		this.jdbcTemplate.update(DELETE_BY_ID, id);
	}

	public Account readAccountById(int id) {
		return this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id},
				(resultSet, i) -> createAccountFromResult(resultSet));
	}

	public List<Account> showAccWithOffset(int limit, int offset, String subStringName, String subStringSurname) {
		List<Account> accountList = new ArrayList<>();
		SqlRowSet rowSet = this.jdbcTemplate.queryForRowSet(QUERY_FOR__OFFSET, new Object[]{subStringName,
				subStringSurname,
				limit,
				offset}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER});
		while (rowSet.next()) {
			accountList.add(createAccountFromResultRowSet(rowSet));
		}
		return accountList;
	}

	public void loadPicture(int accountId, InputStream inputStream) throws SQLException {
		this.jdbcTemplate.update(UPDATE_ACC_PHOTO, inputStream, accountId);
	}

	public List<Account> showAllAccounts() {
		return this.jdbcTemplate.query(SELECT_ALL_BY_ID, (resultSet, i) -> readAccountById(resultSet.getInt("id")));
	}


	private Account createAccountFromResultRowSet(SqlRowSet rowSet) {
		Account account = new Account();
		account.setId(rowSet.getInt("id"));
		account.setName(rowSet.getString("name"));
		account.setSurname(rowSet.getString("surname"));
		account.setAge(rowSet.getInt("age"));
		account.setAddress(rowSet.getString("address"));
		account.setEmail(rowSet.getString("email"));
		account.setPassword(rowSet.getString("password"));
		return account;
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
	/*public List<Account> showFriend(Account account) {
		List<Account> friendList = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SHOW_FRIENDS)) {
			preparedStatement.setInt(1, account.getId());
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					friendList.add(readAccountById(resultSet.getInt("friendid")));
				}
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return friendList;
	}*/
}
