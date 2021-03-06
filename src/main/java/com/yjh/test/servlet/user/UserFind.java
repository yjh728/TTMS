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


@WebServlet("/user/find")
public class UserFind extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        String username = req.getParameter("username");
        String answer = req.getParameter("answer");
        PrintWriter writer = resp.getWriter();
        UserService userService = new UserService();
        Result<User> result = new Result<>();
        try {
            List<User> users = userService.quary(username, null);
            if (users.size() == 0) {
                result.setStatus(false);
                result.setReasons("该用户不存在");
            } else  {
                if (users.get(0).getAnswer().equals(answer)) {
                    result.setStatus(true);
                    result.setReasons("找回密码成功");
                    result.setData(users.get(0));
                } else {
                    result.setStatus(false);
                    result.setReasons("密保答案错误");
                }
            }
            writer.write(result.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
