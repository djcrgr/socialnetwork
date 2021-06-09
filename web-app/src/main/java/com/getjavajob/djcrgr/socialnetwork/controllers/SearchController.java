package com.getjavajob.djcrgr.socialnetwork.controllers;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Group;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import com.getjavajob.training.karpovn.socialnetwork.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

	private final AccountService accountService;
	private final GroupService groupService;

	public SearchController(AccountService accountService, GroupService groupService) {
		this.accountService = accountService;
		this.groupService = groupService;
	}

	@GetMapping("/search")
	public ModelAndView search(@RequestParam String name, @RequestParam(defaultValue = "1") Integer page) throws SQLException {
		ModelAndView modelAndView = new ModelAndView("result");
		List<Account> accountList = accountService.showWithOffset(5, page, name);
		if (!accountList.isEmpty()) {
			modelAndView.addObject("resultList", accountList);
			int numberOfPages = accountList.size() / 10;
			if (numberOfPages % 10 > 0) {
				numberOfPages++;
			}
			modelAndView.addObject("numberOfPages", numberOfPages);
			modelAndView.addObject("currentPage", page);
			modelAndView.addObject("recordsPerPage", 10);
		}
		List<Group> groupList = groupService.showWithOffset(5, page, name);
		if (!groupList.isEmpty()) {
			int numberOfPagesGr = groupList.size() / 10;
			if (numberOfPagesGr % 10 > 0) {
				numberOfPagesGr++;
			}
			modelAndView.addObject("resultListGroups", groupList);
			modelAndView.addObject("numberOfPagesGr", numberOfPagesGr);
			modelAndView.addObject("currentPageGr", page);
			modelAndView.addObject("recordsPerPage", 10);
		}
		modelAndView.addObject("name", name);
		/*int recordsPerPage = 5;
			List<Group> groups = new ArrayList<>();
			List<Account> resultList = new ArrayList<>();
			List<Account> accountList = accountService.showWithOffset(5, page, name, name);
			List<Group> groupList = groupService.showWithOffset(5, page, name);
			if (!groupList.isEmpty()) {
				for (Group group : groupList) {
					if (group.getName().toLowerCase().contains(name.toLowerCase())) {
						groups.add(group);
					}
				}
				int numberOfPagesGr = groups.size() / recordsPerPage;
				if (numberOfPagesGr % recordsPerPage > 0) {
					numberOfPagesGr++;
				}
				modelAndView.addObject("resultListGroups", groups);
				modelAndView.addObject("numberOfPagesGr", numberOfPagesGr);
				modelAndView.addObject("currentPageGr", page);
				modelAndView.addObject("recordsPerPage", recordsPerPage);
			}
			if (!accountList.isEmpty()) {
				for (Account account : accountList) {
					if (account.getName().contains(name) || account.getSurname().contains(name)) {
						resultList.add(account);
					}
				}
				modelAndView.addObject("resultList", resultList);
				int numberOfPages = resultList.size() / recordsPerPage;
				if (numberOfPages % recordsPerPage > 0) {
					numberOfPages++;
				}
				modelAndView.addObject("numberOfPages", numberOfPages);
				modelAndView.addObject("currentPage", page);
				modelAndView.addObject("recordsPerPage", recordsPerPage);
			}
			modelAndView.addObject("name", name);*/
		return  modelAndView;
	}
}
