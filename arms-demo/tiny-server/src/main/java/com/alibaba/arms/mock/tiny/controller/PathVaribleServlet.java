package com.alibaba.arms.mock.tiny.controller;

import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author aliyun
 * @date 2021/12/10
 */
@WebServlet(name = "pathVariableServlet", urlPatterns = "/pathvariable/*")
public class PathVaribleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        int idx = uri.indexOf("/pathvariable");
        String var = uri.substring(idx + "/pathvariable".length() + 1);
        resp.getWriter().write("var is " + var);
        resp.flushBuffer();
    }
}
