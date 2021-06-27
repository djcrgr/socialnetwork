package com.getjavajob.djcrgr.socialnetwork.interceptors;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Base64;

@Component
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

	private final AccountService accountService;
	private String[] loggedInUrls;

	public AuthorizeInterceptor(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostConstruct
	private void initLoggedInUrls() {
		loggedInUrls = new String[]{"/home"};
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/*removeCacheFromBrowser(response);*/
		boolean logged = isLoggedIn(request);
		boolean loginRequred = isUrlMatch(loggedInUrls, request);
		if (!logged && loginRequred) {
			request.getRequestDispatcher("/index").forward(request, response);
		} else if (logged){
			setSessionAccountToRequest(request);
			if (!loginRequred) {
				request.getRequestDispatcher("/home").forward(request, response);
			}
		}
		return true;
	}

	private boolean isUrlMatch(String[] urls, HttpServletRequest request) {
		String requestUrl = request.getRequestURI();
		for (String url : urls) {
			if (requestUrl.contains(url)) {
				return true;
			}
		}
		return false;
	}

	private void setSessionAccountToRequest(HttpServletRequest httpRequest) {
		int sessionAccountId = getSessionAccountId(httpRequest);
		Account account = accountService.readById(sessionAccountId);
		httpRequest.setAttribute("global_acc", account);
	}

	private boolean isLoggedIn(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("account_id") == null) {
			Account account = getAccountFromCookiesValue(request);
			if (account == null) {
				return false;
			} else {
				HttpSession session1 = request.getSession();
				session1.setAttribute("account_id", account.getId());
				return true;
			}
		} else {
			return true;
		}
	}

	public static int getSessionAccountId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return (int) session.getAttribute("account_id");
		} else {
			return -1;
		}
	}

	private Account getAccountFromCookiesValue(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String token = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					token = cookie.getValue();
				}
			}
		}
		if (token != null) {
			String key = "ThisIsASecretKey";
			String decryptedCookie = decrypt(key, token);
			int startIndexForMail = decryptedCookie.indexOf("email=") + 6;
			int endIndexForMail = decryptedCookie.indexOf("&", startIndexForMail);
			String email = decryptedCookie.substring(startIndexForMail, endIndexForMail);
			int startIndexForPass = decryptedCookie.indexOf("password=") + 9;
			int endIndexForPass = decryptedCookie.indexOf("^", startIndexForPass);
			String password = decryptedCookie.substring(startIndexForPass, endIndexForPass);
			return accountService.checkExisting(email, password);
		}
		else return null;
	}

	private String decrypt(String secret, String encryptedString) {
		byte[] decodedKey = Base64.getDecoder().decode(secret);
		try {
			Cipher cipher = Cipher.getInstance("AES");
			SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
			cipher.init(Cipher.DECRYPT_MODE, originalKey);
			byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
			return new String(cipherText);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error occurred while decrypting data", e);
		}
	}
}
