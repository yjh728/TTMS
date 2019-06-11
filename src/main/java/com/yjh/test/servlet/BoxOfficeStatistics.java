package com.yjh.test.servlet;

import com.yjh.test.model.Result;
import com.yjh.test.model.Statistics;
import com.yjh.test.service.BoxOfficeStatisticsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/BoxOfficeStatistics")
public class BoxOfficeStatistics extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Result<List<Statistics>> result = new Result<>();
        BoxOfficeStatisticsService statisticsService = new BoxOfficeStatisticsService();
        try {
            result.setStatus(true);
            result.setReasons("查询成功");
            result.setData(statisticsService.analysis());
        } catch (SQLException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setReasons("查询失败");
        } finally {
            writer.write(result.toString());
            writer.close();
        }
    }
}
