package com.getjavajob.djcrgr.socialnetwork.interceptors;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieInterceptor implements HandlerInterceptor {

	private final AccountService accountService;

	public CookieInterceptor(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
		Cookie[] cookies = httpServletRequest.getCookies();
		String email = null;
		String password = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("mail")) {
				email = cookie.getValue();
			}
			if (cookie.getName().equals("pas")) {
				password = cookie.getValue();
			}
		}
		if (email == null) {
			modelAndView.addObject("message", "please login");
		} else {
			Account account = accountService.checkExisting(email, password);
			if (account != null) {
				modelAndView.addObject("message", "u r logged in");
				modelAndView.addObject("globalId", account.getId());
			} else {
				modelAndView.addObject("message", "please login");
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
		System.out.println("SiteInterceptor afterCompletion");
	}
}
