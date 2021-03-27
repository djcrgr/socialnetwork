package com.getjavajob.djcrgr.socialnetwork.oldServlets;

import com.getjavajob.training.karpovn.socialnetwork.common.Group;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import com.getjavajob.training.karpovn.socialnetwork.service.GroupService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowAllGroupsServlet extends HttpServlet {

    private GroupService groupService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String image = req.getParameter("image");
        List<Group> groups = null;
        try {
            groups = groupService.showAll();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if (!groups.isEmpty()) {
            req.setAttribute("groups", groups);
            req.setAttribute("image", image);
            req.getRequestDispatcher("/WEB-INF/look/groups.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/look/home.jsp").forward(req, resp);
        }
    }
}
