import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Phone;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RegisterServlet extends HttpServlet {

    private AccountService accountService;

    @Override
    public void init() {
        WebApplicationContext applicationContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.accountService = applicationContext.getBean(AccountService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("accname");
        String surname = req.getParameter("accsurname");
        String age = req.getParameter("age");
        if (age.isEmpty()) {
            age = "0";
        }
        String phoneNumHome = req.getParameter("phoneNumHome");
        if (phoneNumHome.isEmpty()) {
            phoneNumHome = "0";
        }
        String phoneNumWork = req.getParameter("phoneNumWork");
        if (phoneNumWork.isEmpty()) {
            phoneNumWork = "0";
        }
        String address = req.getParameter("address");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String destPage = null;
        int id = accountService.showAll().size() + 6;
        List<Phone> phoneList = new ArrayList<>();
        Phone homePhone = new Phone();
        homePhone.setType("home");
        homePhone.setNumber(Integer.parseInt(phoneNumHome));
        Phone workPhone = new Phone();
        workPhone.setType("work");
        workPhone.setNumber(Integer.parseInt(phoneNumWork));
        phoneList.add(homePhone);
        phoneList.add(workPhone);
        Account account = new Account(id, name, surname, Integer.parseInt(age), address, phoneList,
                password, email);
        try {
            accountService.create(account);
            req.setAttribute("account", account);
            req.setAttribute("newAccId", account.getId());
            req.setAttribute("mes", "Account created");
        } catch (Exception e) {
            req.setAttribute("mes", "Exist");
        }
        destPage = "/WEB-INF/look/register.jsp";
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(destPage);
        requestDispatcher.forward(req, resp);
    }
}
