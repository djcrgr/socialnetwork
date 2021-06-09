package com.getjavajob.djcrgr.socialnetwork.controllers;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Message;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import com.getjavajob.training.karpovn.socialnetwork.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
public class MessagesController {

	private final MessageService messageService;
	private final AccountService accountService;

	public MessagesController(MessageService messageService, AccountService accountService) {
		this.messageService = messageService;
		this.accountService = accountService;
	}

	@GetMapping("/getMessages")
	public ModelAndView getMessages(@RequestParam Integer accountToId) throws IOException, SQLException {
		ModelAndView modelAndView = new ModelAndView("messages");
		List<Message> messages = messageService.showMessagesByAccId(accountToId);
		modelAndView.addObject("messages", messages);
		modelAndView.addObject("image", accountService.getImageFromDb(accountToId));
		modelAndView.addObject("account", accountService.readById(accountToId));
		return modelAndView;
	}

	@GetMapping("/deleteMessage")
	public String deleteMessage(@RequestParam Integer idMessage, @RequestParam Integer idAccount) throws IOException,
			SQLException {
		Message message = messageService.readById(idMessage);
		Account account = accountService.readById(idAccount);
		if (message.getAccountTo().getId().equals(idAccount)) {
			messageService.delete(message);
		}
		/*modelAndView.addObject("account", account);
		modelAndView.addObject("image", accountService.getImageFromDb(idAccount));*/
		return "forward:/profile?id=" + idAccount;
	}
}
