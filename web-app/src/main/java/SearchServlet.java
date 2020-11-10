import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchString = req.getParameter("name");
        int currentPage;
        try {
        currentPage = Integer.parseInt(req.getParameter("currentPage"));
        } catch (Exception e) {
            currentPage = 1;

        }
        int recordsPerPage = 5;
        try {
            AccountDao accountDao = new AccountDao();
            List<Account> resultList = new ArrayList<>();
            List<Account> accountList = accountDao.showAccWithOffset(5, currentPage * 5 - 5, searchString,
                    searchString);
            if (accountList != null) {
                for (Account account : accountList) {
                    if (account.getName().contains(searchString) || account.getSurname().contains(searchString)) {
                        resultList.add(account);
                    }
                }
                req.setAttribute("resultList", resultList);
                int numberOfPages = resultList.size() / recordsPerPage;
                if (numberOfPages % recordsPerPage > 0) {
                    numberOfPages++;
                }
                req.setAttribute("name", searchString);
                req.setAttribute("numberOfPages", numberOfPages);
                req.setAttribute("currentPage", currentPage);
                req.setAttribute("recordsPerPage", recordsPerPage);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/look/index.jsp");
                dispatcher.forward(req, resp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
