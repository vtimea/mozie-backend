package com.mozie.security;

import com.mozie.model.database.User;
import com.mozie.repository.UserRepository;
import com.mozie.service.user.AuthToken;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        lazyInit(httpServletRequest.getServletContext());
        String receivedToken = parseJwt(httpServletRequest);
        if (receivedToken == null) {
            httpServletResponse.reset();
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String userId = AuthToken.getUserId(receivedToken);
        if (userId != null) {
            User user = userRepository.findUserByUserId(userId);
            if (user != null) {
                if (user.getToken().equals(receivedToken) && !AuthToken.isExpired(receivedToken)) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }
            }
        }
        httpServletResponse.reset();
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private String parseJwt(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private void lazyInit(ServletContext servletContext) {
        if (userRepository == null) {
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            if (webApplicationContext != null) {
                userRepository = webApplicationContext.getBean(UserRepository.class);
            }
        }
    }
}
