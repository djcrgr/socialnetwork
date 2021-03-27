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

public class GroupProfileServlet extends HttpServlet {

    private GroupService groupService;

  /*  @Override
    public void init() {
        WebApplicationContext applicationContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.groupService = applicationContext.getBean(GroupService.class);
    }
*/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupId = req.getParameter("groupId");
        if (!groupId.isEmpty()) {
            int id = Integer.parseInt(groupId);
            Group group = null;
            try {
                group = groupService.readById(id);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            req.setAttribute("group", group);
            String picture = null;
            try {
                picture = groupService.getImageFromDb(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            req.setAttribute("picture", picture);
            req.getRequestDispatcher("/WEB-INF/look/group.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/look/home.jsp").forward(req, resp);
        }
    }
}
