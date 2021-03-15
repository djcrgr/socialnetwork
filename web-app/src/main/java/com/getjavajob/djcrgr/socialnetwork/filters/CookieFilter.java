package com.getjavajob.djcrgr.socialnetwork.filters;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@Component
@Order(1)
public class CookieFilter implements Filter {


    private FilterConfig filterConfig;
    private AccountService accountService;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    private FilterConfig getFilterConfig() {
        return filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        WebApplicationContext applicationContext =
                WebApplicationContextUtils.getWebApplicationContext(getFilterConfig().getServletContext());
        this.accountService = applicationContext.getBean(AccountService.class);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie[] cookies = request.getCookies();
        String email = null;
        String password = null;
        if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("mail")) {
                email = cookie.getValue();
            }
            if (cookie.getName().equals("pas")) {
                password = cookie.getValue();
            }
        }
        }
        if (email != null && password != null) {
            Account account = null;
            try {
                account = accountService.checkExisting(email, password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (account != null) {
            request.setAttribute("message", "u r logged in");
            request.getSession().setAttribute("globalId", account.getId());
            } else {
                request.setAttribute("message", "please login");
            }
            filterChain.doFilter(request, servletResponse);

        } else {
            request.setAttribute("message", "please login");
            filterChain.doFilter(request, servletResponse);
        }
    }
}
