import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Phone;
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;
import com.getjavajob.training.karpovn.socialnetwork.dao.PhoneDao;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ReadAccServlet extends HttpServlet {

    private AccountDao accountDao;
    private PhoneDao phoneDao;

    @Override
    public void init() {
        WebApplicationContext applicationContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.accountDao = applicationContext.getBean(AccountDao.class);
        this.phoneDao = applicationContext.getBean(PhoneDao.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            AccountService accountService = new AccountService(accountDao, phoneDao);
            String idString = req.getParameter("idAcc");
            Integer res = (Integer) req.getSession().getAttribute("globalId");
            if (res == null) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/look/login.jsp");
                dispatcher.forward(req, resp);
            } else {
                if (idString.isEmpty()) {
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/look/login.jsp");
                    dispatcher.forward(req, resp);
                } else {
                        int id = Integer.parseInt(idString);
                        if (id == res) {
                        Account account = accountService.readById(id);
                        List<Phone> phoneList = account.getPhoneNum();
                        int homePhone = 0;
                        int workPhone = 0;
                            for (Phone phone : phoneList) {
                                if (phone.getType() != null) {
                                if (phone.getType().equals("work")) {
                                    workPhone = phone.getNumber();
                                } else {
                                    homePhone = phone.getNumber();
                                }
                                }
                            }
                        req.setAttribute("workPhone", workPhone);
                        req.setAttribute("homePhone", homePhone);
                        req.setAttribute("currentAcc", account);
                        req.setAttribute("idAcc", id);
                        String image = accountService.getImageFromDb(id);
                        req.setAttribute("image", image);
                        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/look/profileEdit.jsp");
                        dispatcher.forward(req, resp);
                    } else {
                        String msg = "no permissions";
                        req.setAttribute("Error", msg);
                        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/look/login.jsp");
                        dispatcher.forward(req, resp);
                    }
            }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
