package com.getjavajob.djcrgr.socialnetwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;

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
			IOException, GeneralSecurityException {
		Account account = accountService.checkExisting(email, password);
		if (account != null) {
			ModelAndView modelAndView = new ModelAndView("home");
			modelAndView.addObject("account", account);
			modelAndView.addObject("image", accountService.getImageFromDb(account.getId()));
			String rememberCheck = request.getParameter("remember");
			String dataMailPass = "email=" + email + "&" + "password=" + password + "^";
			String key = "ThisIsASecretKey";
			String token = encrypt(key, dataMailPass);
			Cookie cookie = new Cookie("token", token);
			if (rememberCheck == null) {
				cookie.setMaxAge(-1);
			}
			cookie.setMaxAge(60 * 60);
			response.addCookie(cookie);
			return modelAndView;
		} else {
			return new ModelAndView("login");
		}
	}

	@PostMapping("/updateAcc")
	public ModelAndView updateAcc(@RequestBody String json) throws SQLException,
			IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Account account = objectMapper.readValue(json, Account.class);
		accountService.update(account);
		return profileEdit(account.getId());
	}

	@PostMapping("/picUpload")
	public ModelAndView pucUpload(@RequestParam("file") MultipartFile filePart,
	                              @RequestParam("newAccId") Integer id  ) throws IOException,
			SQLException {
		if (id == null) {
			id = 0;
		}
		InputStream inputStream = null;
		if (filePart != null) {
			inputStream = filePart.getInputStream();
		}
		accountService.loadPicture(id, inputStream);
		return profileEdit(id);
	}

	@GetMapping("/profileEdit")
	public ModelAndView profileEdit(@RequestParam(required = false) Integer id) throws IOException, SQLException {
		if (id == null) {
			return new ModelAndView("redirect: login");
		}
		Account account = accountService.readById(id);
		ModelAndView modelAndView = new ModelAndView("profileEdit");
		String image = accountService.getImageFromDb(id);
		if (image == null) {
			image = accountService.getImageFromDb(2);
		}
		modelAndView.addObject("image", image);
		modelAndView.addObject("currentAcc", account);
		modelAndView.addObject("idAcc", account.getId());
		if (!account.getPhoneNum().isEmpty()) {
			modelAndView.addObject("homePhone", account.getPhoneNum().get(0).getNumber());
			modelAndView.addObject("workPhone", account.getPhoneNum().get(1).getNumber());
		}
		return modelAndView;
	}

	@GetMapping("/login")
	public ModelAndView showLoginPage() {
		return new ModelAndView("login");
	}


	private String encrypt(String secret, String data) {


		byte[] decodedKey = Base64.getDecoder().decode(secret);

		try {
			Cipher cipher = Cipher.getInstance("AES");
			// rebuild key using SecretKeySpec
			SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, originalKey);
			byte[] cipherText = cipher.doFinal(data.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(cipherText);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error occured while encrypting data", e);
		}

	}
}
