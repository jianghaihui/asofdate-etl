package com.asofdate.platform.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hzwy23 on 2017/5/18.
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

    private final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) request;

        try {

            logger.debug("URI is:{}",r.getRequestURI());

            Authentication authentication = JwtService.getAuthentication(r);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (Exception e){

            HttpServletResponse w = (HttpServletResponse)response;

            logger.debug("clear Cookie that the name is Authorization");

            w.addCookie(new Cookie("Authorization",""));

            w.sendRedirect("/");

            e.printStackTrace();

        }
    }
}
