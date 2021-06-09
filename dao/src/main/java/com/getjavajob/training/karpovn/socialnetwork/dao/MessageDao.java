package com.getjavajob.training.karpovn.socialnetwork.dao;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Message;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class MessageDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void createMessage(Message message) {
		entityManager.persist(message);
	}

	public void deleteById(int id) {
		entityManager.remove(entityManager.find(Message.class, id));
	}

	public Message showById(int id) {
		String qlString = "select m from Message m where m.id = ?1";
		TypedQuery<Message> query = entityManager.createQuery(qlString, Message.class);
		query.setParameter(1, id);
		return query.getSingleResult();
	}

	public void loadPicture(int messageID, String imageUrl) {
		Message message = showById(messageID);
			message.setImageUrl(imageUrl);
			entityManager.merge(message);
	}

	public List<Message> showMessagesByAccId(int id) {
		try {
			String qlString = "SELECT a FROM Message a WHERE a.accountTo.id = ?1";
			TypedQuery<Message> query = entityManager.createQuery(qlString, Message.class);
			query.setParameter(1, id);
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
}
