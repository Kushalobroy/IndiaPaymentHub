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
                .addPathPatterns("/admin/**", "/user/**", "/login", "/payments/**") // Protect payments route
                .excludePathPatterns("/logout", "/public/**"); // Allow public access
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();

        if (session != null && session.getAttribute("loggedInUser") != null) {
            String userType = (String) session.getAttribute("userType");

            // Redirect logged-in users from "/login" to respective dashboards
            if (requestURI.equals("/login")) {
                if ("ADMIN".equals(userType)) {
                    response.sendRedirect("/admin/dashboard");
                } else {
                    response.sendRedirect("/user/dashboard");
                }
                return false;
            }

            // Role-based access control
            if (requestURI.startsWith("/admin") && !"ADMIN".equals(userType)) {
                response.sendRedirect("/bad-request");
                return false;
            }

            if (requestURI.startsWith("/user") && !"USER".equals(userType)) {
                response.sendRedirect("/bad-request");
                return false;
            }

            // ✅ Restrict /payments/** route to logged-in users only
            if (requestURI.startsWith("/payments") && session.getAttribute("loggedInUser") == null) {
                response.sendRedirect("/login");
                return false;
            }
        } else {
            // Redirect unauthenticated users trying to access protected routes
            if (requestURI.startsWith("/admin") || requestURI.startsWith("/user") || requestURI.startsWith("/payments")) {
                response.sendRedirect("/login");
                return false;
            }
        }

        return true;
    }
}
