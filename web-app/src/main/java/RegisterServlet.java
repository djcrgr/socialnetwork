import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String age = req.getParameter("age");
        String phoneNum = req.getParameter("phoneNum");
        String address = req.getParameter("address");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String destPage = null;
        try {
            AccountService accountService = new AccountService();
            int id = accountService.showAll().size() + 1;
            Account account = new Account(id ,name, surname, Integer.parseInt(age) , address, Integer.parseInt(phoneNum),
                    password, email);
            accountService.create(account);
            if (account != null) {
                HttpSession session = req.getSession();
                session.setAttribute("account", account);
                destPage = "/WEB-INF/look/home.jsp";
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(destPage);
            requestDispatcher.forward(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
