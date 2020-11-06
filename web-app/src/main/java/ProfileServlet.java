import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getAttribute("account") != null) {
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/look/home.jsp");
                    dispatcher.forward(req, resp);
                } else {
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/look/login.jsp");
                    dispatcher.forward(req, resp);
                }

        }
    }

    /*Cookie cookies[] = req.getCookies();
        String email = null;
        String password = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("email")) {
                email = cookie.getValue();
            }
            if (cookie.getName().equals("password")) {
                password = cookie.getValue();
            }
        }
        if (email != null && password != null) {
            try {
                AccountDao accountDao = new AccountDao();
                Account account = accountDao.checkForLogin(email, password);
                if (req.getAttribute("account") != null) {
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/look/home.jsp");
                    dispatcher.forward(req, resp);
                } else {
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/look/login.jsp");
                    dispatcher.forward(req, resp);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/look/login.jsp");
            dispatcher.forward(req, resp);
        }*/

