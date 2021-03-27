package com.getjavajob.djcrgr.socialnetwork.oldServlets;

import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;


@MultipartConfig
public class PicUploadServlet extends HttpServlet {

    private AccountService accountService;

  /*  @Override
    public void init() {
        WebApplicationContext applicationContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.accountService = applicationContext.getBean(AccountService.class);
    }
*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        String id = req.getParameter("newAccId");
        if (id.isEmpty()) {//todo read string api
            id = "0";
        }
        InputStream inputStream = null;
        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }
        try {
            accountService.loadPicture(Integer.parseInt(id), inputStream);
            req.setAttribute("account", accountService.readById(Integer.parseInt(id)));
            req.setAttribute("image", accountService.getImageFromDb(Integer.parseInt(id)));
            req.getRequestDispatcher("/WEB-INF/look/home.jsp").forward(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
