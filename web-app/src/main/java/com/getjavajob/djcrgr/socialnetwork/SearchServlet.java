package com.getjavajob.djcrgr.socialnetwork;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.common.Group;
import com.getjavajob.training.karpovn.socialnetwork.common.Phone;
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;
import com.getjavajob.training.karpovn.socialnetwork.dao.PhoneDao;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import com.getjavajob.training.karpovn.socialnetwork.service.GroupService;
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
import java.util.ArrayList;
import java.util.List;

public class SearchServlet extends HttpServlet {

    private AccountService accountService;
    private GroupService groupService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchString = req.getParameter("name");
        int currentPage;
        try {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
        } catch (Exception e) {
            currentPage = 1;
        }
        int currentPageGr;
        try {
            currentPageGr = Integer.parseInt(req.getParameter("currentPageGr"));
        } catch (Exception e) {
            currentPageGr = 1;
        }
        int recordsPerPage = 5;
        try {
            List<Group> groups = new ArrayList<>();
            List<Account> resultList = new ArrayList<>();
            List<Account> accountList = accountService.showWithOffset(5, currentPage, searchString, searchString);
            List<Group> groupList = groupService.showWithOffset(5, currentPageGr, searchString);
            if (!groupList.isEmpty()) {
                for (Group group : groupList) {
                    if (group.getName().toLowerCase().contains(searchString.toLowerCase())) {
                        groups.add(group);
                    }
                }
                int numberOfPagesGr = groups.size() / recordsPerPage;
                if (numberOfPagesGr % recordsPerPage > 0) {
                    numberOfPagesGr++;
                }
                req.setAttribute("resultListGroups", groups);
                req.setAttribute("numberOfPagesGr", numberOfPagesGr);
                req.setAttribute("currentPageGr", currentPageGr);
                req.setAttribute("recordsPerPage", recordsPerPage);
            }
            if (!accountList.isEmpty()) {
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
                req.setAttribute("numberOfPages", numberOfPages);
                req.setAttribute("currentPage", currentPage);
                req.setAttribute("recordsPerPage", recordsPerPage);
            }
            req.setAttribute("name", searchString);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/look/result.jsp");
            dispatcher.forward(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
