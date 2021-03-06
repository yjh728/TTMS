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

@WebServlet("/user/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Result<User> result = new Result<>();
        UserService userService = new UserService();
        int id = Integer.valueOf(req.getParameter("user_id"));
        try {
            List<User> list = userService.quary(id);
            if (list.size() == 0) {
                result.setStatus(false);
                result.setReasons("用户不存在");
            } else if (userService.delete(id) > 0) {
                result.setStatus(true);
                result.setReasons("删除用户成功");
                result.setData(list.get(0));
            } else {
                result.setStatus(false);
                result.setReasons("删除用户失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("删除用户失败");
        } finally {
            writer.write(result.toString());
            writer.close();
        }
    }
}
