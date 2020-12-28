import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class ProfileServlet extends HttpServlet {

    private AccountService accountService;

    @Override
    public void init() {
        WebApplicationContext applicationContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.accountService = applicationContext.getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null && id.length() > 0) {
            try {
                int accId = Integer.parseInt(id);
                Account account = accountService.readById(accId);
                req.setAttribute("account", account);
                try {
                    String image = accountService.getImageFromDb(account.getId());
                    req.setAttribute("image", image);
                } catch (NullPointerException e) {
                    String res = "";
                    res = accountService.getImageFromDb(17);
                    req.setAttribute("image", res);
                }
                req.setAttribute("id", accId);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/look/home.jsp");
                dispatcher.forward(req, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/look/login.jsp");
            requestDispatcher.forward(req, resp);
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