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

@WebServlet("/studio/update")
public class StudioUpdate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        int studioID = Integer.valueOf(req.getParameter("studio_id"));
        String newName = req.getParameter("new_name");
        StudioService studioService = new StudioService();
        Result<Studio> result = new Result<>();
        PrintWriter writer = resp.getWriter();
        try {
            List<Studio> studios = studioService.quary(studioID);
            if (studios.size() == 0) {
                result.setStatus(false);
                result.setReasons("演出厅不存在");
            } else if (studioService.update(studioID, newName) > 0) {
                studios.get(0).setStudioName(newName);
                result.setStatus(true);
                result.setReasons("演出厅更新成功");
                result.setData(studios.get(0));
            } else {
                result.setStatus(false);
                result.setReasons("更新演出厅失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("更新演出厅失败");
        } finally {
            writer.write(result.toString());
        }
    }
}
