package com.getjavajob.djcrgr.socialnetwork.controllers;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@Controller
public class LoginController {

	private final AccountService accountService;

	public LoginController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping("/login")
	public ModelAndView login(@RequestParam("email") String email, @RequestParam("password") String password,
	                          @RequestParam(value = "remember", required = false) String remember,
	                          HttpSession session, HttpServletResponse resp) {
		Account account = accountService.checkExisting(email, password);
		ModelAndView modelAndView = new ModelAndView();
		if (account != null) {
			session.setAttribute("account_id", account.getId());
			rememberChecked(remember, resp, account);
			modelAndView.setViewName("redirect:home");
		} else {
			setMessageWhenWrong(email, modelAndView);
		}
		return modelAndView;
	}

	@GetMapping("/logout")
	public String signOut(HttpSession session, @CookieValue(value = "token", required = false) String token,
	                      HttpServletResponse resp) {
		session.invalidate();
		if (token != null) {
			Cookie cookie = new Cookie("token", "");
			cookie.setMaxAge(0);
			resp.addCookie(cookie);
		}
		return "redirect:/";
	}

	private void setMessageWhenWrong(String email, ModelAndView modelAndView) {
		modelAndView.addObject("message", "wrong e-mail or password");
		modelAndView.addObject("email", email);
		modelAndView.setViewName("index");
	}

	private void rememberChecked(String remember, HttpServletResponse resp, Account account) {
		boolean rememberOn = "on".equals(remember);
		if (rememberOn) {
			addCookie(resp, account);
		}
	}

	private void addCookie(HttpServletResponse resp, Account account) {
		String dataMailPass = "email=" + account.getEmail() + "&" + "password=" + account.getPassword() + "^";
		String key = "ThisIsASecretKey";
		String token = encrypt(key, dataMailPass);
		Cookie cookie = new Cookie("token", token);
		cookie.setHttpOnly(true);
		resp.addCookie(cookie);
	}

	private String encrypt(String secret, String data) {
		byte[] decodedKey = Base64.getDecoder().decode(secret);
		try {
			Cipher cipher = Cipher.getInstance("AES");
			SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, originalKey);
			byte[] cipherText = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(cipherText);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error occured while encrypting data", e);
		}
	}
}
