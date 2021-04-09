package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PhoneDao {

	private static final String CREATE_PHONE = "insert into phones (userId, phoneNum, phoneType) values (?, ?, ?)";
	private static final String SELECT_ALL_BY_ID = "select * from phones where userId = ?";
	private static final String DELETE_PHONES_BY_ID = "delete from phones where userId = ?";
	private static final String UPDATE_PHONES_FOR_HOME = "update phones set phoneNum = ? where phoneType = 'home' " +
			"and userId = ?";
	private static final String UPDATE_PHONES_FOR_WORK = "update phones set phoneNum = ? where phoneType = 'work' " +
			"and userId = ?";

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	JdbcTemplate jdbcTemplate;

	public void createAccPhones(List<Phone> phoneList) {
		if (!phoneList.isEmpty()) {
			for (Phone phone : phoneList) {
				entityManager.persist(phone);
			}
		}
	}

	public List<Phone> readAccPhones(int id) {
		List<Phone> phoneList = new ArrayList<>();
		SqlRowSet rowSet = this.jdbcTemplate.queryForRowSet(SELECT_ALL_BY_ID, new Object[]{id},
				new int[] {Types.INTEGER});
		while (rowSet.next()) {
			phoneList.add(setPhone(rowSet));
		}
		return phoneList;
	}

	private Phone setPhone(SqlRowSet rowSet) {
		Phone phone = new Phone();
		phone.setNumber(rowSet.getString("phoneNum"));
		phone.setType(rowSet.getString("phoneType"));
		return phone;
	}
/*
	public void updateAccPhones(Account account) {
		if (!account.getPhoneNum().isEmpty()) {
			for (Phone phone : account.getPhoneNum()) {
				if (phone.getType().equals("home")) {
					this.jdbcTemplate.update(UPDATE_PHONES_FOR_HOME, phone.getNumber(), account.getId());
				} else if (phone.getType().equals("work")) {
					this.jdbcTemplate.update(UPDATE_PHONES_FOR_WORK, phone.getNumber(), account.getId());
				}
			}
		}
	}*/

	/*public void deleteAccPhones(Account account) {
		this.jdbcTemplate.update(DELETE_PHONES_BY_ID, account.getId());
	}*/
}