package com.yjh.test.servlet.user;

import com.yjh.test.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/user/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        UserService userService = new UserService();
        String username = req.getParameter("username");
        try {
            int count = userService.delete(username);
            if (count > 0) {
                System.out.println("注销成功");
                writer.println("注销成功");
            } else {
                System.out.println("注销失败");
                writer.println("注销失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        writer.close();
    }
}
