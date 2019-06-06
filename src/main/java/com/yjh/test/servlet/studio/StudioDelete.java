package com.yjh.test.servlet.studio;

import com.yjh.test.model.Result;
import com.yjh.test.model.Studio;
import com.yjh.test.service.StudioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/studio/delete")
public class StudioDelete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        Result<Studio> result = new Result<>();
        PrintWriter writer = resp.getWriter();
        StudioService studioService = new StudioService();
        int id = Integer.valueOf(req.getParameter("studio_id"));
        try {
            List<Studio> studios = studioService.quary(id);
            if (studios.size() == 0) {
                result.setStatus(false);
                result.setReasons("演出厅不存在");
            } else if (studioService.delete(id) > 0) {
                result.setStatus(true);
                result.setReasons("删除演出厅成功");
                result.setData(studios.get(0));
            } else {
                result.setStatus(false);
                result.setReasons("演出厅删除失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("演出厅删除失败");
        } finally {
            writer.write(result.toString());
        }
    }
}
