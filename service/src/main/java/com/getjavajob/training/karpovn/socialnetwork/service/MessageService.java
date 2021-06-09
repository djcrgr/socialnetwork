package com.getjavajob.training.karpovn.socialnetwork.service;

import com.getjavajob.training.karpovn.socialnetwork.common.Message;
import com.getjavajob.training.karpovn.socialnetwork.dao.MessageDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {

	private final MessageDao messageDao;

	public MessageService(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	@Transactional
	public void create(Message message) {
		messageDao.createMessage(message);
	}

	@Transactional
	public Message readById(int id) {
		return messageDao.showById(id);
	}

	@Transactional
	public List<Message> showMessagesByAccId(int id) {
		return messageDao.showMessagesByAccId(id);
	}

	@Transactional
	public void delete(Message message) {
		messageDao.deleteById(message.getId());
	}
}
