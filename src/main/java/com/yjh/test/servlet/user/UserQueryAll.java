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

@WebServlet("/user/queryall")
public class UserQueryAll extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        Result<List<User>> result = new Result<>();
        PrintWriter writer = resp.getWriter();
        UserService service = new UserService();
        try {
            List<User> userList = service.quary(null, null);
            if (userList.size() == 0) {
                result.setStatus(true);
                result.setReasons("没有用户");
            } else {
                result.setStatus(true);
                result.setReasons("查询成功");
                result.setData(userList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("查询失败");
        } finally {
            writer.write(result.toString());
        }
    }
}
