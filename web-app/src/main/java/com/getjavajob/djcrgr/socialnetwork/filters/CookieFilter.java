package com.getjavajob.djcrgr.socialnetwork.filters;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import lombok.SneakyThrows;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;

@Component
@Order(1)
public class CookieFilter implements Filter {


    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    private FilterConfig getFilterConfig() {
        return filterConfig;
    }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        WebApplicationContext applicationContext =
                WebApplicationContextUtils.getWebApplicationContext(getFilterConfig().getServletContext());
        AccountService accountService = applicationContext.getBean(AccountService.class);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }
        if (token != null) {
            Account account = null;
            String key = "ThisIsASecretKey";
            String decryptedCookie = decrypt(key, token);
            int startIndexForMail = decryptedCookie.indexOf("email=") + 6;
            int endIndexForMail = decryptedCookie.indexOf("&", startIndexForMail);
            String email = decryptedCookie.substring(startIndexForMail, endIndexForMail);
            int startIndexForPass = decryptedCookie.indexOf("password=") + 9;
            int endIndexForPass = decryptedCookie.indexOf("^", startIndexForPass);
            String password = decryptedCookie.substring(startIndexForPass, endIndexForPass);
                account = accountService.checkExisting(email, password);
            if (account != null) {
                request.setAttribute("message", "u r logged in");
                request.getSession().setAttribute("globalId", account.getId());
            } else {
                request.setAttribute("message", "please login");
            }
        } else {
            request.setAttribute("message", "please login");
        }
        filterChain.doFilter(request, servletResponse);
    }

    private String decrypt(String secret, String encryptedString) {


        byte[] decodedKey = Base64.getDecoder().decode(secret);

        try {
            Cipher cipher = Cipher.getInstance("AES");
            // rebuild key using SecretKeySpec
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
            return new String(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error occured while decrypting data", e);
        }
    }
}
