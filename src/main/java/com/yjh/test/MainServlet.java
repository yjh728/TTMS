package com.yjh.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");
        String reason = (String) req.getAttribute("reason");
        reason = reason == null ? "" : reason;
        String sessionId = null;
        for (Cookie cookie:req.getCookies()) {
            if (cookie.getName().equals("JSESSIONID")) {
                sessionId = cookie.getValue();
            }
        }
        if (req.getSession().getId().equals(sessionId)) {
            resp.sendRedirect("user/welcome");
        } else {
            resp.getWriter().write("<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "</head>\n" +
                    "<body>\n" +
                    reason +
                    "<br/><form action=\"user/login\" method=\"post\">\n" +
                    "    账号：<input type=\"text\" name=\"username\"><br/>\n" +
                    "    密码：<input type=\"password\" name=\"password\"><br/>\n" +
                    "    <input type=\"submit\" value=\"登录\">\n" +
                    "</form>\n" +
                    "</body>\n" +
                    "</html>");
        }
        resp.getWriter().close();
    }
}
