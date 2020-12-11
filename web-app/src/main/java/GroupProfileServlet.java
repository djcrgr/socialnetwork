import com.getjavajob.training.karpovn.socialnetwork.common.Group;
import com.getjavajob.training.karpovn.socialnetwork.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class GroupProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupId = req.getParameter("groupId");
        if (!groupId.isEmpty()) {
            int id = Integer.parseInt(groupId);
            try {
                GroupService groupService = new GroupService();
                Group group = groupService.readById(id);
                req.setAttribute("group", group);
                String picture = groupService.getImageFromDb(id);
                req.setAttribute("picture", picture);
                req.getRequestDispatcher("/WEB-INF/look/group.jsp").forward(req, resp);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            req.getRequestDispatcher("/WEB-INF/look/home.jsp").forward(req, resp);
        }
    }
}
