import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Phone;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateAccServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            AccountService accountService = new AccountService();
            Account account = new Account();
            String id = req.getParameter("idAccount");
            int idAcc = Integer.parseInt(id);
            account.setId(idAcc);
            String name = req.getParameter("name");
            if (!name.equals("")) {
                account.setName(name);
            }
            String surname = req.getParameter("surname");
            if (!surname.equals("")) {
                account.setSurname(surname);
            }
            if (!req.getParameter("age").equals("")) {
                account.setAge(Integer.parseInt(req.getParameter("age")));
            }
            String address = req.getParameter("address");
            if (!address.equals("")) {
                account.setAddress(address);
            }
            List<Phone> phones = new ArrayList<>();
            if (!req.getParameter("phoneNumHome").equals("")) {
                Phone homePhone = new Phone();
                homePhone.setNumber(Integer.parseInt(req.getParameter("phoneNumHome")));
                homePhone.setType("home");
                phones.add(homePhone);
            }
            if (!req.getParameter("phoneNumWork").equals("")) {
                Phone workPhone = new Phone();
                workPhone.setNumber(Integer.parseInt(req.getParameter("phoneNumWork")));
                workPhone.setType("work");
                phones.add(workPhone);
            }
            if (!phones.isEmpty()) {
                account.setPhoneNum(phones);
            }
            String email = req.getParameter("email");
            if (!email.equals("")) {
                account.setEmail(email);
            }
            String password = req.getParameter("password");
            if (!password.equals("")) {
                account.setPassword(password);
            }
            accountService.update(account);
            Object image = accountService.getImageFromDb(idAcc);
            req.setAttribute("image", image);
            req.setAttribute("currentAcc", accountService.readById(account.getId()));
            req.setAttribute("idAcc", idAcc);
            req.setAttribute("homePhone", Integer.parseInt(req.getParameter("phoneNumHome")));
            req.setAttribute("workPhone", Integer.parseInt(req.getParameter("phoneNumWork")));
            req.getRequestDispatcher("/WEB-INF/look/profileEdit.jsp").forward(req, resp);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
