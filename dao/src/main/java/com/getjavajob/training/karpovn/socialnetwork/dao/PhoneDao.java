package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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

	private final JdbcTemplate jdbcTemplate;
	private final DataSource dataSource;
	@Autowired
	private EntityManagerFactory emf;

	@Autowired
	public PhoneDao(DataSource dataSource, JdbcTemplate jdbcTemplate) throws SQLException {
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
	}

	public void createAccPhones(Account account) {
		if (!account.getPhoneNum().isEmpty()) {
			for (Phone phone : account.getPhoneNum()) {
				this.jdbcTemplate.update(CREATE_PHONE, account.getId(), phone.getNumber(), phone.getType());
			}
		}
	}

	public List<Phone> readAccPhones(int id) {
		/*List<Phone> phoneList = new ArrayList<>();
		SqlRowSet rowSet = this.jdbcTemplate.queryForRowSet(SELECT_ALL_BY_ID, new Object[]{id},
				new int[] {Types.INTEGER});
		while (rowSet.next()) {
			phoneList.add(setPhone(rowSet));
		}
		return phoneList;*/
		List<Phone> list = new ArrayList<>();
		list.add(new Phone("1", "home"));
		list.add(new Phone("1", "work"));
		return list;
	}

	private Phone setPhone(SqlRowSet rowSet) {
		Phone phone = new Phone();
		phone.setNumber(rowSet.getString("phoneNum"));
		phone.setType(rowSet.getString("phoneType"));
		return phone;
	}

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
	}

	public void deleteAccPhones(Account account) {
		this.jdbcTemplate.update(DELETE_PHONES_BY_ID, account.getId());
	}
}