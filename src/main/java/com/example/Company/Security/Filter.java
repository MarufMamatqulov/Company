package com.example.Company.Security;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.*;

public interface Filter {

    default void init(FilterConfig filterConfig) throws ServletException {

    }

    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;
    default void destroy() {

    }
}
