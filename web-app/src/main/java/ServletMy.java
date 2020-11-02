/*
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;
import com.getjavajob.training.karpovn.socialnetwork.dao.ConnectionPool;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class ServletMy extends HttpServlet {

    private String name;
    private ConnectionPool connectionPool;
    private AccountDao accountDao;
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        try {
            connectionPool = com.getjavajob.training.karpovn.socialnetwork.dao.ConnectionPool.getInstance("com.mysql.jdbc.Driver","jdbc:mysql://eu-cdbr-west-03.cleardb.net/heroku_34f0532abb90acb?reconnect=true:3306/heroku_34f0532abb90acb", "bd1823065c1f06", "af490815", 10, 20, true);
            accountDao = new com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao(this.connectionPool.getConnection());
            accountService = new com.getjavajob.training.karpovn.socialnetwork.service.AccountService(accountDao);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("<table border=1 cellpadding=5>");
        List<com.getjavajob.training.karpovn.socialnetwork.common.Account> accountList = accountService.showAll();
        if (accountList != null) {
            for (com.getjavajob.training.karpovn.socialnetwork.common.Account account : accountList) {
                sb.append("<tr>");
                sb.append("<td>").append(account.getName()).append("</td>");
                sb.append("<td>").append(account.getSurname()).append("</td>");
                sb.append("<td>").append(account.getAge()).append("</td>");
                sb.append("<td>").append(account.getPhoneNum()).append("</td>");
                sb.append("<td>").append(account.getAddres()).append("</td>");
                sb.append("<tr>");
            }
        }
        sb.append("</table>");
        sb.append("</body></html>");
        printWriter.write(sb.toString());
    }

}
*/
