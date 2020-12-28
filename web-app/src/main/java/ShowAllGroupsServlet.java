import com.getjavajob.training.karpovn.socialnetwork.common.Group;
import com.getjavajob.training.karpovn.socialnetwork.service.GroupService;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowAllGroupsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String image = req.getParameter("image");
        try {
            GroupService groupService = new GroupService();
            List<Group> groups = groupService.showAll();
            if (!groups.isEmpty()) {
                req.setAttribute("groups", groups);
                req.setAttribute("image", image);
                req.getRequestDispatcher("/WEB-INF/look/groups.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/WEB-INF/look/home.jsp").forward(req, resp);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
