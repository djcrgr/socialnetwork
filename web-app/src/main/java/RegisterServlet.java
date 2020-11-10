import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@MultipartConfig
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
        Part filePart = req.getPart("file");
        InputStream  inputStream = null;
        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }
        try {
            AccountService accountService = new AccountService();
            AccountDao accountDao = new AccountDao();
            int id = accountService.showAll().size() + 1;
            Account account = new Account(id ,name, surname, Integer.parseInt(age) , address, Integer.parseInt(phoneNum),
                    password, email);
            accountService.create(account);
            accountDao.loadPicture(id, inputStream);
            if (account != null) {
                req.setAttribute("account", account);
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
