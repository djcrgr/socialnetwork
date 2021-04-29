package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;


import javax.persistence.*;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Repository
public class AccountDao {

	@PersistenceContext
	private EntityManager entityManager;

	public String getImageFromDb(int accountId) {
		Query query = entityManager.createQuery("Select acc.photo FROM Account acc where acc.id = ?1 ");
		query.setParameter(1, accountId);
		Object result = query.getSingleResult();
		if ( result == null) {
			return null;
		} else {
			return DatatypeConverter.printBase64Binary((byte[]) result);
		}
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

	public void updateAccount(@ModelAttribute("account") Account account) {
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
