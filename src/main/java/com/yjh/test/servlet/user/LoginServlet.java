package com.yjh.test.servlet.user;

import com.yjh.test.dao.DaoFactory;
import com.yjh.test.model.Result;
import com.yjh.test.model.User;
import com.yjh.test.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Writer writer = resp.getWriter();
        UserService userService = new UserService();
        Result<User> result = new Result<>();
        try {
            List<User> users = userService.quary(username, null);
            if (users.size() == 0) {
                result.setStatus(false);
                result.setReasons("用户不存在");
                result.setData(null);
                writer.write(result.toString());
            } else {
                users = userService.quary(username, password);
                if (users.size() == 0) {
                    result.setStatus(false);
                    result.setReasons("账号或密码错误");
                    writer.write(result.toString());
                } else {
                    result.setStatus(true);
                    result.setReasons("登录成功");
                    result.setData(users.get(0));
                    writer.write(result.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
