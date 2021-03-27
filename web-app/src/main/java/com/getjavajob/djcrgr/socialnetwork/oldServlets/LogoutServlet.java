package com.getjavajob.djcrgr.socialnetwork.oldServlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Cookie cookieMail = new Cookie("mail", "");
        Cookie cookiePass = new Cookie("pas", "");
        cookieMail.setMaxAge(0);
        cookiePass.setMaxAge(0);
        resp.addCookie(cookieMail);
        resp.addCookie(cookiePass);
        session.invalidate();
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/look/login.jsp");
        requestDispatcher.forward(req, resp);
    }
}

