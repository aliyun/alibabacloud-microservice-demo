package com.alibaba.arms.mock.server.filters;

import com.alibaba.arms.mock.server.aop.IgnoreLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author aliyun
 * @date 2021/06/15
 */
@Component
@Slf4j
@IgnoreLog
public class TraceFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (!httpServletRequest.getRequestURI().equalsIgnoreCase("/")) {
            log.info("request {} from {}", httpServletRequest.getRequestURL(), httpServletRequest.getRemoteAddr());
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
