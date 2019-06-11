package com.yjh.test.servlet.studio;

import com.yjh.test.model.Result;
import com.yjh.test.model.Seat;
import com.yjh.test.model.Studio;
import com.yjh.test.service.SeatService;
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

@WebServlet("/studio/queryall")
public class StudioQueryAll extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        Result<List<Studio>> result = new Result<>();
        PrintWriter writer = resp.getWriter();
        StudioService studioServic = new StudioService();
        try {
            List<Studio> studios = studioServic.quary(-1);
            if (studios.size() == 0) {
                result.setStatus(false);
                result.setReasons("无演出厅");
            } else {
                result.setStatus(true);
                result.setReasons("查询演出厅成功");
                result.setData(studios);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("查询演出厅失败");
        }finally {
            writer.write(result.toString());
        }
    }
}
