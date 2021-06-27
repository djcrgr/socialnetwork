package com.getjavajob.djcrgr.socialnetwork.controllers;

import com.getjavajob.training.karpovn.socialnetwork.common.Group;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import com.getjavajob.training.karpovn.socialnetwork.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

@Controller
public class GroupController {

	private final GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@GetMapping("/showAllGroups")
	public ModelAndView showAllGroups() throws SQLException, ClassNotFoundException {
		ModelAndView modelAndView = new ModelAndView("groups");
		List<Group> groupList = groupService.showAll();
		modelAndView.addObject("groups", groupList);
		return modelAndView;
	}

	@GetMapping("/groupProfile")
	public ModelAndView groupProfile(@RequestParam Integer groupId) throws SQLException, ClassNotFoundException {
		if (groupId == null) {
			return new ModelAndView("login");
		}
		Group group = groupService.readById(groupId);
		ModelAndView modelAndView = new ModelAndView("group");
		if (group == null) {
			return new ModelAndView("login");
		} else  {
			modelAndView.addObject("group", group);
			modelAndView.addObject("picture", groupService.getImageFromDb(group.getId()));
			return modelAndView;
		}
	}
}
