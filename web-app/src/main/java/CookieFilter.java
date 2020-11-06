import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

public class CookieFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie cookies[] = request.getCookies();
        String email = null;
        String password = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("mail")) {
                email = cookie.getValue();
            }
            if (cookie.getName().equals("pas")) {
                password = cookie.getValue();
            }
        }
        try {
            AccountDao accountDao = new AccountDao();
            Account account = accountDao.checkForLogin(email, password);
            request.setAttribute("account", account);
            filterChain.doFilter(request, servletResponse);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {

    }
}
