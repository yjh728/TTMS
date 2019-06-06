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

@WebServlet("/studio/add")
public class StudioAdd extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Result<Studio> result = new Result<>();
        StudioService studioService = new StudioService();
        String studioName = req.getParameter("studio_name");
        int studioRow = Integer.valueOf(req.getParameter("studio_rows"));
        int studioCol = Integer.valueOf(req.getParameter("studio_cols"));
        Studio studio = new Studio();
        studio.setStudioName(studioName);
        studio.setRow(studioRow);
        studio.setCol(studioCol);
        studio.setSeatsCount(studioCol * studioRow);
        try {
            studioService.add(studio);
            result.setStatus(true);
            result.setReasons("演出厅和座位添加成功");
            result.setData(studio);
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("演出厅和座位添加失败");
        } finally {
            writer.write(result.toString());
        }
    }
}
