package com.example.postgresql.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.postgresql.controller.RequiredRoles;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

public class RoleCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        RequiredRoles annotation = handlerMethod.getMethod().getAnnotation(RequiredRoles.class);

        if (annotation == null) {
            return true;
        }

        HttpSession session = request.getSession(false);
        //System.out.println(session);
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect("/login");
            return false;
        }

        String userRole = (String) session.getAttribute("role");
        String[] requiredRoles = annotation.value();

        if (Arrays.stream(requiredRoles).noneMatch(role -> role.equals(userRole))) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>403 - Access Denied</title>
                </head>
                <body>
                    <h1>403 Forbidden</h1>
                    <p>You don't have permission to access this resource.</p>
                    <a href="/login">Go to login page</a>
                </body>
                </html>
            """);
            return false;
        }

        return true;
    }
}