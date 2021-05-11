package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Phone;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PhoneDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void createAccPhones(List<Phone> phoneList) {
		if (!phoneList.isEmpty()) {
			for (Phone phone : phoneList) {
				entityManager.persist(phone);
			}
		}
	}

	@Transactional
	public List<Phone> readAccPhones(Integer id) {
		String qlString = "SELECT a FROM Phone a where a.account.id = ?1";
		TypedQuery<Phone> query = entityManager.createQuery(qlString, Phone.class);
		query.setParameter(1, id);
		return query.getResultList();
	}

	public void updateAccPhones(Account account) {
		if (!account.getPhoneNum().isEmpty()) {
			for (Phone phone : account.getPhoneNum()) {
				entityManager.merge(phone);
			}
		}
	}

	public void deleteAccPhones(Account account) {
		List<Phone> phoneList = account.getPhoneNum();
		if (!phoneList.isEmpty()) {
			for (Phone phone : phoneList) {
				entityManager.remove(phone);
			}
		}
	}
}