package com.example.indiapaymenthub.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Security implements WebMvcConfigurer, HandlerInterceptor {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this)
                .addPathPatterns("/admin/**", "/user/**", "/login") // Protect routes + check login page
                .excludePathPatterns("/logout", "/public/**"); // Allow public access
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();

        if (session != null && session.getAttribute("loggedInUser") != null) {
            String userType = (String) session.getAttribute("userType");

            // If user is already logged in and tries to access "/login", redirect them
            if (requestURI.equals("/login")) {
                if ("ADMIN".equals(userType)) {
                    response.sendRedirect("/admin/dashboard"); // Redirect admin
                } else {
                    response.sendRedirect("/user/dashboard"); // Redirect normal user
                }
                return false;
            }

            // Role-based access restriction
            if (requestURI.startsWith("/admin") && !"ADMIN".equals(userType)) {
                response.sendRedirect("/bad-request");
                return false;
            }

            if (requestURI.startsWith("/user") && !"USER".equals(userType)) {
                response.sendRedirect("/bad-request");
                return false;
            }
        } else {
            // If user is not logged in and tries to access admin/user pages, redirect to login
            if (requestURI.startsWith("/admin") || requestURI.startsWith("/user")) {
                response.sendRedirect("/login");
                return false;
            }
        }

        return true;
    }
}
