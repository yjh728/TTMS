package com.yjh.test.servlet.user;

import com.yjh.test.model.ENUM.UserStatus;
import com.yjh.test.model.Result;
import com.yjh.test.model.User;
import com.yjh.test.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/user/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 设置请求编码格式
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        UserService userService = new UserService();
        String identity = req.getParameter("identity");
        User user = new User();
        user.setUserName(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        user.setAnswer(req.getParameter("answer"));
        switch (identity) {
            case "经理":
                user.setIdentity(UserStatus.MANAGER);
                break;
            case "售票员":
                user.setIdentity(UserStatus.TICKET_CLERK);
                break;
            case "用户":
                user.setIdentity(UserStatus.CUSTOMER);
                break;
        }
        Result<User> result = new Result<>();
        try {
            List<User> users = userService.quary(user.getUserName(), null);
            if (users.size() != 0) {
                result.setStatus(false);
                result.setReasons("该用户已存在，请直接登录");
            } else {
                int count = userService.add(user);
                if (count > 0) {
                    result.setStatus(true);
                    result.setReasons("注册成功");
                    result.setData(user);
                } else {
                    result.setStatus(false);
                    result.setReasons("注册失败");
                }
            }
            writer.write(result.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        writer.close();
    }
}
