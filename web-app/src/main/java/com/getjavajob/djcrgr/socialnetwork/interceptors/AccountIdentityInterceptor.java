package com.getjavajob.djcrgr.socialnetwork.interceptors;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.getjavajob.djcrgr.socialnetwork.interceptors.AuthorizeInterceptor.getSessionAccountId;
import static java.lang.Integer.parseInt;

@Component
public class AccountIdentityInterceptor extends HandlerInterceptorAdapter {

	private final AccountService accountService;

	public AccountIdentityInterceptor(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, SQLException {
		String accountId = request.getParameter("account_id");
		if (accountId != null) {
			identifyAccountByAccountId(request, accountId);
		} else {
			identifyAsCurrentUser(request);
		}
		return true;
	}

	private void identifyAccountByAccountId(HttpServletRequest httpRequest, String accountIdParameter) throws IOException, SQLException {
		int accountId = parseInt(accountIdParameter);
		Account account = accountService.readById(accountId);
		if (account != null) {
			httpRequest.setAttribute("account", account);
			httpRequest.setAttribute("image", accountService.getImageFromDb(account.getId()));
		}
	}

	private void identifyAsCurrentUser(HttpServletRequest httpRequest) throws IOException, SQLException {
		int sessionAccountId = getSessionAccountId(httpRequest);
		Account account = accountService.readById(sessionAccountId);
		httpRequest.setAttribute("account", account);
		httpRequest.setAttribute("image", accountService.getImageFromDb(account.getId()));
	}
}
