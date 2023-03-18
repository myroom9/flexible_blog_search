package com.whahn.filter;


import com.whahn.common.RequestContextUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Order(0)
public class RequestInitFilter extends OncePerRequestFilter {
    private boolean isInitialized = false;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String requestId = UUID.randomUUID().toString();
        RequestContextUtil.setRequestId(requestId);
        ThreadContext.put("requestId", requestId);

        chain.doFilter(request, response);
    }
}
