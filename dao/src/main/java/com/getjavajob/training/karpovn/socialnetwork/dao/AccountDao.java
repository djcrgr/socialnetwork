package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;


import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
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

	@PersistenceContext
	private EntityManager entityManager;

	public String getImageFromDb(int accountId) {
		Query query = entityManager.createQuery("Select acc.photo FROM Account acc where acc.id = ?1 ");
		query.setParameter(1, accountId);
		return query.getSingleResult().toString();
	}

	public Account checkForLogin(String email, String password) {
		String qlString = "Select acc FROM Account acc where acc.email = ?1 and acc" +
				".password = ?2";
		TypedQuery<Account> query = entityManager.createQuery(qlString, Account.class);
		query.setParameter(1, email);
		query.setParameter(2, password);
		return query.getSingleResult();
	}

	public void createAccount(Account account) {
		entityManager.persist(account);
	}

	public void updateAccount(Account account) {
		entityManager.merge(account);
	}

	public void deleteById(int id) {
		entityManager.remove(entityManager.find(Account.class, id));
	}

	public Account readAccountById(int id) {
		try {
			String qlString = "SELECT a FROM Account a WHERE a.id = ?1";
			TypedQuery<Account> query = entityManager.createQuery(qlString, Account.class);
			query.setParameter(1, id);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Account> showAccWithOffset(int limit, int offset, String subStringName, String subStringSurname) {
		List<Account> accountList = new ArrayList<>();
		/*String qlStringOne = "select * from account where name like CONCAT( '%' ,?1, '%') limit ?2 offset ?3";
		TypedQuery<Account> query = em.createQuery(qlStringOne, Account.class);
		query.setParameter()
		SqlRowSet rowSet = this.jdbcTemplate.queryForRowSet(QUERY_FOR__OFFSET, new Object[]{subStringName,
				subStringSurname,
				limit,
				offset}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER});
		while (rowSet.next()) {
			accountList.add(createAccountFromResultRowSet(rowSet));
		}*/
		return accountList;
	}

	public void loadPicture(int accountId, InputStream inputStream) throws SQLException, IOException {
		Account account = readAccountById(accountId);
		if (inputStream != null) {
			account.setPhoto(IOUtils.toByteArray(inputStream));
			entityManager.merge(account);
		}
	}

	public List<Account> showAllAccounts() throws SQLException {
		String qlString = "SELECT a FROM Account a";
		TypedQuery<Account> query = entityManager.createQuery(qlString, Account.class);
		return query.getResultList();
	}
}
