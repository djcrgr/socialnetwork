package com.getjavajob.djcrgr.socialnetwork.controllers;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Phone;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import org.apache.commons.io.IOUtils;
import org.h2.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProfileController {

	private final AccountService accountService;

	@Autowired
	public ProfileController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/")
	public ModelAndView hello(Model model) {
		return new ModelAndView("index");
	}

	@GetMapping("/profile")
	public ModelAndView profile(@RequestParam(required = false) Integer id) throws IOException, SQLException {
		if (id == null) {
			return new ModelAndView("login");
		}
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("account", accountService.readById(id));
		modelAndView.addObject("image", accountService.getImageFromDb(id));
		return modelAndView;
	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpServletResponse response, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Cookie cookieMail = new Cookie("mail", "");
		Cookie cookiePass = new Cookie("pas", "");
		cookieMail.setMaxAge(0);
		cookiePass.setMaxAge(0);
		response.addCookie(cookieMail);
		response.addCookie(cookiePass);
		session.invalidate();
		return new ModelAndView("redirect: login");
	}

	@PostMapping("/login")
	public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpServletRequest request,
	 HttpServletResponse response) throws SQLException,
			IOException {
		Account account = accountService.checkExisting(email, password);
		if (account != null) {
			ModelAndView modelAndView = new ModelAndView("home");
			modelAndView.addObject("account", account);
			modelAndView.addObject("image", accountService.getImageFromDb(account.getId()));
			String rememberCheck = request.getParameter("remember");
			Cookie cookieMail = new Cookie("mail", email);
			Cookie cookiePass = new Cookie("pas", password);
			if (rememberCheck == null) {
				cookieMail.setMaxAge(-1);
				cookiePass.setMaxAge(-1);
			}
			cookieMail.setMaxAge(60*60);
			cookiePass.setMaxAge(60*60);
			response.addCookie(cookieMail);
			response.addCookie(cookiePass);
			return modelAndView;
		} else {
			return new ModelAndView("login");
		}
	}

	@PostMapping("/updateAcc")
	public ModelAndView updateAcc(@RequestBody String json) throws SQLException,
			IOException {
				System.out.println(json);
		return profileEdit(18);
	}

	@GetMapping("/profileEdit")
	public ModelAndView profileEdit(@RequestParam (required = false) Integer id) throws IOException, SQLException {
		if (id == null) {
			return new ModelAndView("redirect: login");
		}
		Account account = accountService.readById(id);
		ModelAndView modelAndView = new ModelAndView("profileEdit");
		String image = accountService.getImageFromDb(account.getId());
		if (image == null) {
			image = accountService.getImageFromDb(2);
		}
		modelAndView.addObject("image", image);
		modelAndView.addObject("currentAcc", account);
		modelAndView.addObject("idAcc", account.getId());
		if (account.getPhoneNum() != null) {
			modelAndView.addObject("homePhone", account.getPhoneNum().get(0).getNumber());
			modelAndView.addObject("workPhone", account.getPhoneNum().get(1).getNumber());
		}
		return modelAndView;
	}

	@GetMapping("/login")
	public ModelAndView showLoginPage() {
		return new ModelAndView("login");
	}
}
