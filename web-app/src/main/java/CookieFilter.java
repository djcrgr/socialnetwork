import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.dao.AccountDao;
import com.getjavajob.training.karpovn.socialnetwork.dao.PhoneDao;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class CookieFilter implements Filter {

    private AccountService accountService;
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    private FilterConfig getFilterConfig() {
        return filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        WebApplicationContext applicationContext =
                WebApplicationContextUtils.getWebApplicationContext(getFilterConfig().getServletContext());
        this.accountService = applicationContext.getBean(AccountService.class);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie[] cookies = request.getCookies();
        String email = null;
        String password = null;
        if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("mail")) {
                email = cookie.getValue();
            }
            if (cookie.getName().equals("pas")) {
                password = cookie.getValue();
            }
        }
        }
        if (email != null && password != null) {
        try {
            Account account = accountService.checkExisting(email, password);
            if (account != null) {
            request.setAttribute("message", "u r logged in");
            request.getSession().setAttribute("globalId", account.getId());
            } else {
                request.setAttribute("message", "please login");
            }
            filterChain.doFilter(request, servletResponse);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        } else {
            request.setAttribute("message", "please login");
            filterChain.doFilter(request, servletResponse);
        }
    }
}
