package com.getjavajob.djcrgr.socialnetwork;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    private AccountService accountService;

/*
    @Override
    public void init() {
        WebApplicationContext applicationContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.accountService = applicationContext.getBean(AccountService.class);
    }
*/

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberCheck = request.getParameter("remember");
        Cookie cookieMail = new Cookie("mail", email);
        Cookie cookiePas = new Cookie("pas", password);
        if (rememberCheck == null) {
            cookieMail.setMaxAge(-1);
            cookiePas.setMaxAge(-1);
        }
        cookieMail.setMaxAge(60*60);
        cookiePas.setMaxAge(60*60);
        response.addCookie(cookieMail);
        response.addCookie(cookiePas);
        setAccount(request, response, email, password);
    }

    private void setAccount(HttpServletRequest request, HttpServletResponse response, String email, String password) throws IOException, ServletException {
        try {
            Account account = accountService.checkExisting(email, password);
            if (account != null) {
                response.sendRedirect("profile?id=" + account.getId());
            } else {
                request.setAttribute("message", "Invalid email/password");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/look/login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

