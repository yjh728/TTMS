package com.yjh.test.servlet.user;

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

@WebServlet("/user/update")
public class UserUpdate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String newPassword = req.getParameter("new_password");
        PrintWriter writer = resp.getWriter();
        UserService userService = new UserService();
        Result<User> result = new Result<>();
        try {
            List<User> users = userService.quary(username, null);
            if (users == null) {
                result.setStatus(false);
                result.setReasons("该用户不存在");
            } else if ((users = userService.quary(username, password)).size() > 0) {
                if (userService.update(username, newPassword) > 0) {
                    result.setStatus(true);
                    result.setReasons("修改密码成功");
                    users.get(0).setPassword(newPassword);
                    result.setData(users.get(0));
                } else {
                    result.setStatus(false);
                    result.setReasons("修改密码失败");
                }
            } else {
                result.setStatus(false);
                result.setReasons("原密码错误");
            }
            writer.write(result.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
