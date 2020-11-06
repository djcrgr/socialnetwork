import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberCheck = request.getParameter("remember");
        if (rememberCheck != null) {
            Cookie cookieMail = new Cookie("mail", email);
            Cookie cookiePas = new Cookie("pas", password);
            response.addCookie(cookieMail);
            response.addCookie(cookiePas);
        }
        setAccount(request, response, email, password);
    }

    private void setAccount(HttpServletRequest request, HttpServletResponse response, String email, String password) throws IOException, ServletException {
        try {
            AccountDao accountDao = new AccountDao();
            Account account = accountDao.checkForLogin(email, password);
            if (account != null) {
                request.setAttribute("account", account);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/look/home.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("message", "Invalid email/password");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

